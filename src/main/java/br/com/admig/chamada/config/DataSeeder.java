package br.com.admig.chamada.config;

import br.com.admig.chamada.model.Classe;
import br.com.admig.chamada.model.Usuario;
import br.com.admig.chamada.model.enums.CategoriaClasse;
import br.com.admig.chamada.model.enums.Role;
import br.com.admig.chamada.repository.ClasseRepository;
import br.com.admig.chamada.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final ClasseRepository classeRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.default-email}")
    private String adminEmail;

    @Value("${app.admin.default-password}")
    private String adminPassword;

    @Value("${app.secretary.default-email:}")
    private String secretaryEmail;

    @Value("${app.secretary.default-password:}")
    private String secretaryPassword;

    @Override
    public void run(String... args) {
        seedClasses();
        seedAdmin();
        seedSecretary();
    }

    private void seedClasses() {
        for (CategoriaClasse categoria : CategoriaClasse.values()) {
            if (classeRepository.findByCategoria(categoria).isEmpty()) {
                classeRepository.save(Classe.builder()
                        .categoria(categoria)
                        .ativo(true)
                        .build());
            }
        }
    }

    private void seedAdmin() {
        if (!usuarioRepository.existsByEmail(adminEmail)) {
            usuarioRepository.save(Usuario.builder()
                    .nome("Administrador ADMIG RF II")
                    .email(adminEmail)
                    .senha(passwordEncoder.encode(adminPassword))
                    .role(Role.ADMINISTRADOR)
                    .ativo(true)
                    .build());
        }
    }

    private void seedSecretary() {
        if (secretaryEmail.isBlank() || secretaryPassword.isBlank()
                || usuarioRepository.existsByEmail(secretaryEmail)) {
            return;
        }

        usuarioRepository.save(Usuario.builder()
                .nome("Cleo")
                .email(secretaryEmail)
                .senha(passwordEncoder.encode(secretaryPassword))
                .role(Role.SECRETARIO)
                .ativo(true)
                .build());
    }
}
