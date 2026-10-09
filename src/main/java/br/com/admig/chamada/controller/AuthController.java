package br.com.admig.chamada.controller;

import br.com.admig.chamada.config.JwtService;
import br.com.admig.chamada.dto.LoginRequest;
import br.com.admig.chamada.dto.LoginResponse;
import br.com.admig.chamada.model.Usuario;
import br.com.admig.chamada.repository.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha())
        );

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        String token = jwtService.generateToken(usuario);

        return new LoginResponse(token, usuario.getId(), usuario.getNome(), usuario.getEmail(),
                usuario.getRole(), usuario.getFotoUrl());
    }
}
