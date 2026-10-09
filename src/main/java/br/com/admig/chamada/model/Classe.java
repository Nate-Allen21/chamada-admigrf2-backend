package br.com.admig.chamada.model;

import br.com.admig.chamada.model.enums.CategoriaClasse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "classes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Classe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private CategoriaClasse categoria;

    /** Nome customizável do professor/responsável pela classe, opcional */
    private String professorResponsavel;

    @Builder.Default
    private Boolean ativo = true;

    @OneToMany(mappedBy = "classe")
    @JsonIgnore
    @Builder.Default
    private List<Aluno> alunos = new ArrayList<>();
}
