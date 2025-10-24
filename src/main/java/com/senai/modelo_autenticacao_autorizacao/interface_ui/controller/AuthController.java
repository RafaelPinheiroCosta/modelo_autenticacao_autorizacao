package com.senai.modelo_autenticacao_autorizacao.interface_ui.controller;

import com.senai.modelo_autenticacao_autorizacao.application.dto.AuthDTO;
import com.senai.modelo_autenticacao_autorizacao.application.service.AuthService;
import com.senai.modelo_autenticacao_autorizacao.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService auth;


    private final UsuarioRepository usuarios;

    @PostMapping("/login")
    public ResponseEntity<AuthDTO.AuthResponse> login(@RequestBody AuthDTO.LoginRequest req) {
        var tokens = auth.login(req);
        return ResponseEntity.ok(new AuthDTO.AuthResponse(
                tokens.get("accessToken"),
                tokens.get("refreshToken")
        ));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthDTO.AuthResponse> refresh(@RequestBody AuthDTO.RefreshRequest req) {
        var newToken = auth.refresh(req.refreshToken());
        return ResponseEntity.ok(new AuthDTO.AuthResponse(
                newToken.get("accessToken"),
                req.refreshToken()
        ));
    }

    @GetMapping("/me")
    public AuthDTO.UserResponse me(Authentication auth) {
        var usuario = usuarios.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return new AuthDTO.UserResponse(usuario.getNome(), usuario.getEmail(), usuario.getRole().name());
    }
}
