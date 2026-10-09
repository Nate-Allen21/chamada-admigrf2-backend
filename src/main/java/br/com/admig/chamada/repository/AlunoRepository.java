package br.com.admig.chamada.repository;

import br.com.admig.chamada.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    List<Aluno> findByClasseIdAndAtivoTrue(Long classeId);
    List<Aluno> findByClasseId(Long classeId);
}
