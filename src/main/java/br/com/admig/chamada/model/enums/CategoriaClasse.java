package br.com.admig.chamada.model.enums;

/**
 * Categorias de classes da Escola Dominical - ADMIG RF II
 */
public enum CategoriaClasse {
    VARAO("Varões", "Remidos de Deus"),
    CRIANCA("Crianças", "Cordeirinhos de Cristo"),
    IRMA("Irmãs", "Mensageiras de Cristo"),
    ADOLESCENTE("Adolescentes", "Geração Eleita"),
    JOVEM("Jovens", "Atalaias de Deus");

    private final String nomeGrupo;
    private final String nomeClasse;

    CategoriaClasse(String nomeGrupo, String nomeClasse) {
        this.nomeGrupo = nomeGrupo;
        this.nomeClasse = nomeClasse;
    }

    public String getNomeGrupo() {
        return nomeGrupo;
    }

    public String getNomeClasse() {
        return nomeClasse;
    }
}
