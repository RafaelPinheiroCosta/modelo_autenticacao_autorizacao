package com.senai.modelo_autenticacao_autorizacao.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senai.modelo_autenticacao_autorizacao.application.dto.AuthDTO;
import com.senai.modelo_autenticacao_autorizacao.domain.entity.Professor;
import com.senai.modelo_autenticacao_autorizacao.domain.enums.Role;
import com.senai.modelo_autenticacao_autorizacao.domain.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private UsuarioRepository usuarios;
    @Autowired
    private PasswordEncoder encoder;

    @BeforeEach
    void setup() {
        usuarios.deleteAll();
        usuarios.save(Professor.builder()
                .nome("Rafael")
                .cpf("000.000.000-00")
                .email("rafael@senai.com")
                .senha(encoder.encode("123456"))
                .role(Role.ADMIN)
                .build());
    }

    @Test
    void deveFazerLoginERetornarToken() throws Exception {
        AuthDTO.LoginRequest req = new AuthDTO.LoginRequest("rafael@senai.com", "123456");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    void deveNegarLoginComSenhaInvalida() throws Exception {
        AuthDTO.LoginRequest req = new AuthDTO.LoginRequest("rafael@senai.com", "errada");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isUnauthorized());
    }
}
