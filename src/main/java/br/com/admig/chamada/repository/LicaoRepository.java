package br.com.admig.chamada.repository;

import br.com.admig.chamada.model.Licao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LicaoRepository extends JpaRepository<Licao, Long> {
    List<Licao> findByTrimestreIdOrderByNumeroAsc(Long trimestreId);
    Optional<Licao> findFirstByDataLessThanEqualOrderByDataDesc(LocalDate data);
}
