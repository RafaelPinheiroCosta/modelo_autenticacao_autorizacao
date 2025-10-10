package com.senai.modelo_autenticacao_autorizacao.unit;

import com.senai.modelo_autenticacao_autorizacao.domain.entity.Professor;
import com.senai.modelo_autenticacao_autorizacao.domain.entity.Usuario;
import com.senai.modelo_autenticacao_autorizacao.domain.enums.Role;
import com.senai.modelo_autenticacao_autorizacao.domain.repository.UsuarioRepository;
import com.senai.modelo_autenticacao_autorizacao.infrastructure.security.UsuarioDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioDetailsServiceTest {

    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private UsuarioDetailsService service;

    @Test
    void deveRetornarUserDetailsQuandoUsuarioEncontrado() {
        Usuario user = Professor.builder()
                .email("rafael@senai.com")
                .senha("123")
                .role(Role.ADMIN)
                .build();

        when(repository.findByEmail("rafael@senai.com")).thenReturn(Optional.of(user));

        UserDetails details = service.loadUserByUsername("rafael@senai.com");

        assertEquals("rafael@senai.com", details.getUsername());
        assertTrue(details.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        when(repository.findByEmail("x@senai.com")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> service.loadUserByUsername("x@senai.com"));
    }
}
