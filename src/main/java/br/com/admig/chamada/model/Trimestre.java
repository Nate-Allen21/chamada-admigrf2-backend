package br.com.admig.chamada.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa um trimestre da Escola Dominical (padrão de revista: 13 lições).
 */
@Entity
@Table(name = "trimestres")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trimestre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer ano;

    /** Número do trimestre no ano: 1, 2, 3 ou 4 */
    @Column(nullable = false)
    private Integer numero;

    private String tituloRevista;

    @Column(nullable = false)
    private LocalDate dataInicio; // domingo da lição 1

    @Column(nullable = false)
    private LocalDate dataFim; // domingo da lição 13

    @Builder.Default
    private Boolean ativo = true;

    @OneToMany(mappedBy = "trimestre", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Builder.Default
    private List<Licao> licoes = new ArrayList<>();
}
