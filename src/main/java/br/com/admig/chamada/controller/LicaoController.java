package br.com.admig.chamada.controller;

import br.com.admig.chamada.model.Licao;
import br.com.admig.chamada.service.LicaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/licoes")
@RequiredArgsConstructor
public class LicaoController {

    private final LicaoService licaoService;

    @GetMapping
    public List<Licao> listarPorTrimestre(@RequestParam Long trimestreId) {
        return licaoService.listarPorTrimestre(trimestreId);
    }

    @GetMapping("/{id}")
    public Licao buscar(@PathVariable Long id) {
        return licaoService.buscarPorId(id);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','SECRETARIO')")
    public Licao atualizarTitulo(@PathVariable Long id, @RequestBody String titulo) {
        return licaoService.atualizarTitulo(id, titulo);
    }
}
