package br.com.admig.chamada.controller;

import br.com.admig.chamada.dto.CalendarioResponse;
import br.com.admig.chamada.model.Trimestre;
import br.com.admig.chamada.service.TrimestreService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','SECRETARIO')")
    public Trimestre criar(@RequestBody Trimestre trimestre) {
        return trimestreService.criarComLicoes(trimestre);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public void excluir(@PathVariable Long id) {
        trimestreService.excluir(id);
    }
}
