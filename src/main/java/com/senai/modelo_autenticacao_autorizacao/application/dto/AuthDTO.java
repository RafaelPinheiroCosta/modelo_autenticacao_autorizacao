package com.senai.modelo_autenticacao_autorizacao.application.dto;

public class AuthDTO {

    public record LoginRequest(String email, String senha) {}

    public record TokenResponse(String token) {}

    public record AuthResponse(String accessToken, String refreshToken) {}

    public record RefreshRequest(String refreshToken) {}

    public record UserResponse(String nome, String email, String role) {}
}
