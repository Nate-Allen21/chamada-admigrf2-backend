package br.com.admig.chamada.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;
import java.util.Locale;
import java.util.Set;
import java.util.Arrays;

/**
 * Upload simples de foto de perfil. Em produção recomenda-se trocar por um
 * bucket S3/Cloud Storage; para o escopo deste sistema o arquivo é salvo em
 * disco (persistido via volume Docker) e servido em /uploads/**.
 */
@RestController
@RequestMapping("/api/upload")
public class UploadController {

    private final Path uploadDir;
    private static final Set<String> TIPOS_PERMITIDOS = Set.of("image/jpeg", "image/png", "image/webp");

    public UploadController(@Value("${app.upload-dir:uploads}") String uploadDir) {
        this.uploadDir = Path.of(uploadDir);
    }

    @PostMapping("/foto-perfil")
    public ResponseEntity<Map<String, String>> uploadFotoPerfil(@RequestParam("arquivo") MultipartFile arquivo) throws IOException {
        if (arquivo.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("erro", "Arquivo vazio"));
        }
        String tipo = arquivo.getContentType();
        if (tipo == null || !TIPOS_PERMITIDOS.contains(tipo.toLowerCase(Locale.ROOT))) {
            return ResponseEntity.badRequest().body(Map.of("erro", "Envie uma imagem JPG, PNG ou WebP"));
        }
        if (!assinaturaCompativel(arquivo, tipo.toLowerCase(Locale.ROOT))) {
            return ResponseEntity.badRequest().body(Map.of("erro", "O conteúdo do arquivo não corresponde a uma imagem válida"));
        }
        Files.createDirectories(uploadDir);

        String extensao = switch (tipo.toLowerCase(Locale.ROOT)) {
            case "image/jpeg" -> ".jpg";
            case "image/png" -> ".png";
            case "image/webp" -> ".webp";
            default -> throw new IllegalArgumentException("Tipo de imagem não suportado");
        };
        String nomeArquivo = UUID.randomUUID() + extensao;
        Path destino = uploadDir.resolve(nomeArquivo);
        arquivo.transferTo(destino);

        String url = "/uploads/" + nomeArquivo;
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("url", url));
    }

    private boolean assinaturaCompativel(MultipartFile arquivo, String tipo) throws IOException {
        byte[] cabecalho = arquivo.getInputStream().readNBytes(12);
        return switch (tipo) {
            case "image/jpeg" -> cabecalho.length >= 3
                    && (cabecalho[0] & 0xFF) == 0xFF
                    && (cabecalho[1] & 0xFF) == 0xD8
                    && (cabecalho[2] & 0xFF) == 0xFF;
            case "image/png" -> Arrays.equals(cabecalho, new byte[] {
                    (byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A
            });
            case "image/webp" -> cabecalho.length >= 12
                    && new String(cabecalho, 0, 4, java.nio.charset.StandardCharsets.US_ASCII).equals("RIFF")
                    && new String(cabecalho, 8, 4, java.nio.charset.StandardCharsets.US_ASCII).equals("WEBP");
            default -> false;
        };
    }
}
