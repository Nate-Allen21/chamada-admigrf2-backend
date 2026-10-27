package br.com.admig.chamada.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Registro de presença/falta de um aluno em uma lição específica.
 */
@Entity
@Table(name = "presencas", uniqueConstraints = @UniqueConstraint(columnNames = {"aluno_id", "licao_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Presenca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "aluno_id", nullable = false)
    @JsonIgnoreProperties({"classe"})
    private Aluno aluno;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "licao_id", nullable = false)
    @JsonIgnoreProperties({"trimestre"})
    private Licao licao;

    @Column(nullable = false)
    private Boolean presente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registrado_por_id")
    @JsonIgnoreProperties({"senha", "authorities"})
    private Usuario registradoPor;

    @Builder.Default
    private LocalDateTime registradoEm = LocalDateTime.now();

    private String observacao;
}
