package br.com.admig.chamada.controller;

import br.com.admig.chamada.model.Classe;
import br.com.admig.chamada.service.ClasseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
@RequiredArgsConstructor
public class ClasseController {

    private final ClasseService classeService;

    @GetMapping
    public List<Classe> listar() {
        return classeService.listarTodas();
    }

    @GetMapping("/{id}")
    public Classe buscar(@PathVariable Long id) {
        return classeService.buscarPorId(id);
    }

    // Ambos os perfis podem gerenciar classes, conforme solicitado.
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','SECRETARIO')")
    public Classe criar(@RequestBody Classe classe) {
        return classeService.salvar(classe);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','SECRETARIO')")
    public Classe atualizar(@PathVariable Long id, @RequestBody Classe classe) {
        return classeService.atualizar(id, classe);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public void excluir(@PathVariable Long id) {
        classeService.excluir(id);
    }
}
