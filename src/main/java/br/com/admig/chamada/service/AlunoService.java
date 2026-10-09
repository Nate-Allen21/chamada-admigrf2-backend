package br.com.admig.chamada.service;

import br.com.admig.chamada.model.Aluno;
import br.com.admig.chamada.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public List<Aluno> listarPorClasse(Long classeId) {
        return alunoRepository.findByClasseId(classeId);
    }

    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    public Aluno buscarPorId(Long id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
    }

    public Aluno salvar(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    public Aluno atualizar(Long id, Aluno dados) {
        Aluno aluno = buscarPorId(id);
        aluno.setNome(dados.getNome());
        aluno.setDataNascimento(dados.getDataNascimento());
        aluno.setResponsavel(dados.getResponsavel());
        aluno.setTelefoneContato(dados.getTelefoneContato());
        aluno.setClasse(dados.getClasse());
        aluno.setAtivo(dados.getAtivo());
        return alunoRepository.save(aluno);
    }

    public void excluir(Long id) {
        alunoRepository.deleteById(id);
    }
}
