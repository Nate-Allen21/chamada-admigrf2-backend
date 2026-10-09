package br.com.admig.chamada.repository;

import br.com.admig.chamada.model.Presenca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PresencaRepository extends JpaRepository<Presenca, Long> {
    List<Presenca> findByLicaoId(Long licaoId);
    List<Presenca> findByAlunoId(Long alunoId);
    Optional<Presenca> findByAlunoIdAndLicaoId(Long alunoId, Long licaoId);
    List<Presenca> findByLicaoIdAndAluno_Classe_Id(Long licaoId, Long classeId);
}
