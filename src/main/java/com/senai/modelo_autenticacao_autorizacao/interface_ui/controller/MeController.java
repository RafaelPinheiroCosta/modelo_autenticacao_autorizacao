package com.senai.modelo_autenticacao_autorizacao.interface_ui.controller;

import com.senai.modelo_autenticacao_autorizacao.application.dto.AuthDTO;
import com.senai.modelo_autenticacao_autorizacao.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MeController {

    private final UsuarioRepository usuarios;

    @GetMapping("/me")
    public AuthDTO.UserResponse me(Authentication auth) {
        var usuario = usuarios.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return new AuthDTO.UserResponse(usuario.getNome(), usuario.getEmail(), usuario.getRole().name());
    }
}