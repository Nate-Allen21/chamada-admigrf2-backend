package br.com.admig.chamada.dto;

import br.com.admig.chamada.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private Long id;
    private String nome;
    private String email;
    private Role role;
    private String fotoUrl;
}
