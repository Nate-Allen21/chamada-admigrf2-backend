package br.com.admig.chamada.dto;

import lombok.Data;

import java.util.List;

@Data
public class RegistrarPresencaRequest {
    private Long licaoId;
    private List<ItemPresenca> itens;

    @Data
    public static class ItemPresenca {
        private Long alunoId;
        private Boolean presente;
        private String observacao;
    }
}
