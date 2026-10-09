package br.com.admig.chamada.service;

import br.com.admig.chamada.dto.AtualizarPerfilRequest;
import br.com.admig.chamada.dto.CriarUsuarioRequest;
import br.com.admig.chamada.model.Usuario;
import br.com.admig.chamada.model.enums.Role;
import br.com.admig.chamada.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public Usuario criar(CriarUsuarioRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Já existe um usuário com este e-mail");
        }
        Usuario usuario = Usuario.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .senha(passwordEncoder.encode(request.getSenha()))
                .role(request.getRole() != null ? request.getRole() : Role.SECRETARIO)
                .telefone(request.getTelefone())
                .ativo(true)
                .build();
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizarPerfil(Long id, AtualizarPerfilRequest request) {
        Usuario usuario = buscarPorId(id);

        if (request.getNome() != null) usuario.setNome(request.getNome());
        if (request.getTelefone() != null) usuario.setTelefone(request.getTelefone());
        if (request.getFotoUrl() != null) usuario.setFotoUrl(request.getFotoUrl());

        if (request.getNovaSenha() != null && !request.getNovaSenha().isBlank()) {
            if (request.getSenhaAtual() == null ||
                    !passwordEncoder.matches(request.getSenhaAtual(), usuario.getSenha())) {
                throw new RuntimeException("Senha atual incorreta");
            }
            usuario.setSenha(passwordEncoder.encode(request.getNovaSenha()));
        }

        return usuarioRepository.save(usuario);
    }

    public void desativar(Long id) {
        Usuario usuario = buscarPorId(id);
        usuario.setAtivo(false);
        usuarioRepository.save(usuario);
    }

    public void excluir(Long id) {
        usuarioRepository.deleteById(id);
    }
}
