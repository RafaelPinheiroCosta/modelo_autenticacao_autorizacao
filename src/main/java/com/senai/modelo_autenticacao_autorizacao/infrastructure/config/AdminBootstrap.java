package com.senai.modelo_autenticacao_autorizacao.infrastructure.config;

import com.senai.modelo_autenticacao_autorizacao.domain.entity.Professor;
import com.senai.modelo_autenticacao_autorizacao.domain.enums.Role;
import com.senai.modelo_autenticacao_autorizacao.domain.repository.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminBootstrap implements CommandLineRunner {

    private final ProfessorRepository professorRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${sistema.admin.email}")
    private String adminEmail;

    @Value("${sistema.admin.senha}")
    private String adminSenha;

    @Override
    public void run(String... args) {
        professorRepository.findByEmail(adminEmail).ifPresentOrElse(
                prof -> {
                    if (!prof.isAtivo()) {
                        prof.setAtivo(true);
                        professorRepository.save(prof);
                    }
                },
                () -> {
                    Professor admin = Professor.builder()
                            .nome("Administrador Provisório")
                            .email(adminEmail)
                            .cpf("000.000.000-00")
                            .senha(passwordEncoder.encode(adminSenha))
                            .role(Role.ADMIN)
                            .build();
                    professorRepository.save(admin);
                    System.out.println("⚡ Usuário admin provisório criado: " + adminEmail);
                }
        );
    }
}
