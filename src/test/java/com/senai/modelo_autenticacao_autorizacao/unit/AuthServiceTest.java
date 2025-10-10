package com.senai.modelo_autenticacao_autorizacao.unit;
import com.senai.modelo_autenticacao_autorizacao.application.dto.AuthDTO;
import com.senai.modelo_autenticacao_autorizacao.application.service.AuthService;
import com.senai.modelo_autenticacao_autorizacao.domain.entity.Professor;
import com.senai.modelo_autenticacao_autorizacao.domain.entity.Usuario;
import com.senai.modelo_autenticacao_autorizacao.domain.enums.Role;
import com.senai.modelo_autenticacao_autorizacao.domain.repository.UsuarioRepository;
import com.senai.modelo_autenticacao_autorizacao.infrastructure.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UsuarioRepository usuarios;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private JwtService jwt;

    @InjectMocks
    private AuthService service;

    @Test
    void deveGerarTokenQuandoCredenciaisValidas() {
        AuthDTO.LoginRequest req = new AuthDTO.LoginRequest("rafael@senai.com", "123");
        Usuario user = Professor.builder()
                .email("rafael@senai.com")
                .senha("encoded")
                .role(Role.ADMIN)
                .build();

        when(usuarios.findByEmail("rafael@senai.com")).thenReturn(Optional.of(user));
        when(encoder.matches("123", "encoded")).thenReturn(true);
        when(jwt.generateToken("rafael@senai.com", "ADMIN")).thenReturn("token123");

        String token = service.login(req);

        assertEquals("token123", token);
    }

    @Test
    void deveLancarExcecaoQuandoSenhaIncorreta() {
        AuthDTO.LoginRequest req = new AuthDTO.LoginRequest("rafael@senai.com", "123");
        Usuario user = Professor.builder()
                .email("rafael@senai.com")
                .senha("encoded")
                .role(Role.ADMIN)
                .build();

        when(usuarios.findByEmail("rafael@senai.com")).thenReturn(Optional.of(user));
        when(encoder.matches("123", "encoded")).thenReturn(false);

        assertThrows(BadCredentialsException.class, () -> service.login(req));
    }
}