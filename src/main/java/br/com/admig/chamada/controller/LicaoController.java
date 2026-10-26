package br.com.admig.chamada.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.admig.chamada.model.Licao;
import br.com.admig.chamada.service.LicaoService;
import lombok.RequiredArgsConstructor;

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
    public Licao atualizarTitulo(@PathVariable Long id, @RequestBody String titulo) {
        return licaoService.atualizarTitulo(id, titulo);
    }
}
