package br.com.admig.chamada.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Uma das 13 lições de um trimestre, dada todo domingo de manhã na Escola Dominical.
 */
@Entity
@Table(name = "licoes", uniqueConstraints = @UniqueConstraint(columnNames = {"trimestre_id", "numero"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Licao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Número da lição dentro do trimestre: 1 a 13 */
    @Column(nullable = false)
    private Integer numero;

    private String titulo;

    /** Domingo em que a lição é ministrada */
    @Column(nullable = false)
    private LocalDate data;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trimestre_id", nullable = false)
    @JsonIgnoreProperties({"licoes"})
    private Trimestre trimestre;
}
