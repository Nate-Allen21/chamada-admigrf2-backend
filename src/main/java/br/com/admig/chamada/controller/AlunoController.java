package br.com.admig.chamada.controller;

import br.com.admig.chamada.model.Aluno;
import br.com.admig.chamada.service.AlunoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alunos")
@RequiredArgsConstructor
public class AlunoController {

    private final AlunoService alunoService;

    @GetMapping
    public List<Aluno> listar(@RequestParam(required = false) Long classeId) {
        return classeId != null ? alunoService.listarPorClasse(classeId) : alunoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Aluno buscar(@PathVariable Long id) {
        return alunoService.buscarPorId(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','SECRETARIO')")
    public Aluno criar(@RequestBody Aluno aluno) {
        return alunoService.salvar(aluno);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','SECRETARIO')")
    public Aluno atualizar(@PathVariable Long id, @RequestBody Aluno aluno) {
        return alunoService.atualizar(id, aluno);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','SECRETARIO')")
    public void excluir(@PathVariable Long id) {
        alunoService.excluir(id);
    }
}
