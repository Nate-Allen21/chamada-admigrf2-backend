package br.com.admig.chamada.service;

import br.com.admig.chamada.model.Licao;
import br.com.admig.chamada.repository.LicaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LicaoService {

    private final LicaoRepository licaoRepository;

    public List<Licao> listarPorTrimestre(Long trimestreId) {
        return licaoRepository.findByTrimestreIdOrderByNumeroAsc(trimestreId);
    }

    public Licao buscarPorId(Long id) {
        return licaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lição não encontrada"));
    }

    public Licao atualizarTitulo(Long id, String titulo) {
        Licao licao = buscarPorId(id);
        licao.setTitulo(titulo);
        return licaoRepository.save(licao);
    }
}
