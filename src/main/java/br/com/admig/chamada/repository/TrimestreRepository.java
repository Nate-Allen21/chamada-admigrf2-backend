package br.com.admig.chamada.repository;

import br.com.admig.chamada.model.Trimestre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface TrimestreRepository extends JpaRepository<Trimestre, Long> {
    Optional<Trimestre> findByDataInicioLessThanEqualAndDataFimGreaterThanEqual(LocalDate d1, LocalDate d2);
    Optional<Trimestre> findByAnoAndNumero(Integer ano, Integer numero);
}
