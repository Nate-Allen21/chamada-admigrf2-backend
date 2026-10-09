package br.com.admig.chamada.dto;

import lombok.Data;

@Data
public class AtualizarPerfilRequest {
    private String nome;
    private String telefone;
    private String fotoUrl;
    private String senhaAtual;
    private String novaSenha;
}
