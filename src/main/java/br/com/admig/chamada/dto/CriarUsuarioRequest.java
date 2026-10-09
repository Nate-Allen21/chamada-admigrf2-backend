package br.com.admig.chamada.dto;

import br.com.admig.chamada.model.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CriarUsuarioRequest {
    @NotBlank
    private String nome;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String senha;
    private Role role;
    private String telefone;
}
