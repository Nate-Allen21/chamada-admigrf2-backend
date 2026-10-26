package br.com.admig.chamada.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.admig.chamada.model.Classe;
import br.com.admig.chamada.service.ClasseService;
import lombok.RequiredArgsConstructor;

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
    public Classe criar(@RequestBody Classe classe) {
        return classeService.salvar(classe);
    }

    @PutMapping("/{id}")
    public Classe atualizar(@PathVariable Long id, @RequestBody Classe classe) {
        return classeService.atualizar(id, classe);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        classeService.excluir(id);
    }
}
