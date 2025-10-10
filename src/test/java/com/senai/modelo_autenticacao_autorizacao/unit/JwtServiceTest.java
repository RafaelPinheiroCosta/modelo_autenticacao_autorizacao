package com.senai.modelo_autenticacao_autorizacao.unit;

import com.senai.modelo_autenticacao_autorizacao.infrastructure.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;
    private final String secret = "mysupersecretkeymysupersecretkey123!";
    private final long expiration = 3600;

    @BeforeEach
    void setup() {
        jwtService = new JwtService(secret, expiration);
    }

    @Test
    void deveGerarTokenComEmailEValidarCorretamente() {
        String email = "rafael@senai.com";
        String role = "ADMIN";
        String token = jwtService.generateToken(email, role);

        assertNotNull(token);
        assertEquals(email, jwtService.extractEmail(token));
        assertEquals(role, jwtService.extractRole(token));

        UserDetails user = User.withUsername(email).password("x").roles(role).build();
        assertTrue(jwtService.isTokenValid(token, user));
    }

    @Test
    void deveDetectarTokenInvalidoComOutroUsuario() {
        String token = jwtService.generateToken("rafael@senai.com", "ADMIN");
        UserDetails user = User.withUsername("outro@senai.com").password("x").roles("ADMIN").build();

        assertFalse(jwtService.isTokenValid(token, user));
    }
}