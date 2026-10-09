package br.com.admig.chamada.controller;

import br.com.admig.chamada.dto.AtualizarPerfilRequest;
import br.com.admig.chamada.dto.CriarUsuarioRequest;
import br.com.admig.chamada.model.Usuario;
import br.com.admig.chamada.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    /** Apenas o Administrador cria/edita/exclui perfis de Secretário. */
    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public List<Usuario> listar() {
        return usuarioService.listarTodos();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public Usuario criar(@RequestBody CriarUsuarioRequest request) {
        return usuarioService.criar(request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public void excluir(@PathVariable Long id) {
        usuarioService.excluir(id);
    }

    @PatchMapping("/{id}/desativar")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public void desativar(@PathVariable Long id) {
        usuarioService.desativar(id);
    }

    // --- Perfil próprio: qualquer usuário autenticado edita o seu próprio ---

    @GetMapping("/perfil")
    public Usuario meuPerfil(@AuthenticationPrincipal Usuario usuario) {
        return usuarioService.buscarPorId(usuario.getId());
    }

    @PutMapping("/perfil")
    public Usuario atualizarMeuPerfil(@AuthenticationPrincipal Usuario usuario,
                                       @RequestBody AtualizarPerfilRequest request) {
        return usuarioService.atualizarPerfil(usuario.getId(), request);
    }
}
