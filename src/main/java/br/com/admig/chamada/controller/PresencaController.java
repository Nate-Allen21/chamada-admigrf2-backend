package br.com.admig.chamada.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.admig.chamada.dto.RegistrarPresencaRequest;
import br.com.admig.chamada.model.Presenca;
import br.com.admig.chamada.service.PresencaService;
import lombok.RequiredArgsConstructor;

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
    public List<Presenca> registrarChamada(@RequestBody RegistrarPresencaRequest request) {
        return presencaService.registrarChamada(request);
    }
}
