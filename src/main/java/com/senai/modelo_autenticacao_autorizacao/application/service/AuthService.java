package com.senai.modelo_autenticacao_autorizacao.application.service;

import com.senai.modelo_autenticacao_autorizacao.application.dto.AuthDTO;
import com.senai.modelo_autenticacao_autorizacao.domain.entity.Usuario;
import com.senai.modelo_autenticacao_autorizacao.domain.repository.UsuarioRepository;
import com.senai.modelo_autenticacao_autorizacao.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarios;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    public String login(AuthDTO.LoginRequest req) {
        Usuario usuario = usuarios.findByEmail(req.email())
                .orElseThrow(() ->  new RuntimeException("Usuário não encontrado"));

        if (!encoder.matches(req.senha(), usuario.getSenhaHash())) {
            throw new BadCredentialsException("Credenciais inválidas");
        }

        return jwt.generateToken(usuario.getEmail(), usuario.getRole().name());
    }
}


