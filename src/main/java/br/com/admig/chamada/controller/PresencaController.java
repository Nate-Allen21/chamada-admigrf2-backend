package br.com.admig.chamada.controller;

import br.com.admig.chamada.dto.RegistrarPresencaRequest;
import br.com.admig.chamada.model.Presenca;
import br.com.admig.chamada.model.Usuario;
import br.com.admig.chamada.service.PresencaService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/presencas")
@RequiredArgsConstructor
public class PresencaController {

    private final PresencaService presencaService;

    @GetMapping
    public List<Presenca> listar(@RequestParam Long licaoId, @RequestParam Long classeId) {
        return presencaService.listarPorLicaoEClasse(licaoId, classeId);
    }

    @GetMapping("/aluno/{alunoId}")
    public List<Presenca> listarPorAluno(@PathVariable Long alunoId) {
        return presencaService.listarPorAluno(alunoId);
    }

    /**
     * Endpoint principal da chamada: registra presença/falta de todos os
     * alunos de uma classe para uma lição específica em uma única requisição.
     * Tanto o Administrador quanto o Secretário podem chamar (o Secretário é
     * o ator com mais uso deste endpoint no dia a dia).
     */
    @PostMapping("/chamada")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','SECRETARIO')")
    public List<Presenca> registrarChamada(@RequestBody RegistrarPresencaRequest request,
                                            @AuthenticationPrincipal Usuario usuario) {
        return presencaService.registrarChamada(request, usuario);
    }
}
