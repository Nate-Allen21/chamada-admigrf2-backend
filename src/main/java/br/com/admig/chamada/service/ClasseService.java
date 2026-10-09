package br.com.admig.chamada.service;

import br.com.admig.chamada.model.Classe;
import br.com.admig.chamada.repository.ClasseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClasseService {

    private final ClasseRepository classeRepository;

    public List<Classe> listarTodas() {
        return classeRepository.findAll();
    }

    public Classe buscarPorId(Long id) {
        return classeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Classe não encontrada"));
    }

    public Classe salvar(Classe classe) {
        return classeRepository.save(classe);
    }

    public Classe atualizar(Long id, Classe dados) {
        Classe classe = buscarPorId(id);
        classe.setProfessorResponsavel(dados.getProfessorResponsavel());
        classe.setAtivo(dados.getAtivo());
        return classeRepository.save(classe);
    }

    public void excluir(Long id) {
        classeRepository.deleteById(id);
    }
}
