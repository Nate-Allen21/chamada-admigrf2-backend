package br.com.admig.chamada.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.admig.chamada.dto.CalendarioResponse;
import br.com.admig.chamada.model.Trimestre;
import br.com.admig.chamada.service.TrimestreService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/trimestres")
@RequiredArgsConstructor
public class TrimestreController {

    private final TrimestreService trimestreService;

    @GetMapping
    public List<Trimestre> listar() {
        return trimestreService.listarTodos();
    }

    @GetMapping("/{id}")
    public Trimestre buscar(@PathVariable Long id) {
        return trimestreService.buscarPorId(id);
    }

    /** Calendário: mostra o trimestre ativo, as 13 lições e a lição atual. */
    @GetMapping("/calendario")
    public CalendarioResponse calendario() {
        return trimestreService.calendarioAtual();
    }

    @PostMapping
    public Trimestre criar(@RequestBody Trimestre trimestre) {
        return trimestreService.criarComLicoes(trimestre);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        trimestreService.excluir(id);
    }
}
