package br.com.admig.chamada.repository;

import br.com.admig.chamada.model.Classe;
import br.com.admig.chamada.model.enums.CategoriaClasse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClasseRepository extends JpaRepository<Classe, Long> {
    Optional<Classe> findByCategoria(CategoriaClasse categoria);
}
