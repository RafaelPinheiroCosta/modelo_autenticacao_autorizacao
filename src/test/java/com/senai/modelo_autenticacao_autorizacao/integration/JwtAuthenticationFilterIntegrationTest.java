package com.senai.modelo_autenticacao_autorizacao.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senai.modelo_autenticacao_autorizacao.application.dto.AuthDTO;
import com.senai.modelo_autenticacao_autorizacao.infrastructure.security.JwtService;
import com.senai.modelo_autenticacao_autorizacao.infrastructure.security.UsuarioDetailsService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class JwtAuthenticationFilterIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private ObjectMapper mapper;

    @Mock
    private UsuarioDetailsService usuarioDetailsService;

    @Test
    void deveRetornar405QuandoMetodoHttpNaoSuportado() throws Exception {
        mockMvc.perform(get("/auth/login"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void devePermitirAcessoAoLoginSemTokenMesmoComSenhaInvalida() throws Exception {
        AuthDTO.LoginRequest req = new AuthDTO.LoginRequest("rafael@senai.com", "senhaErrada");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deveNegarAcessoAEndpointProtegidoSemToken() throws Exception {
        mockMvc.perform(get("/professores"))
                .andExpect(status().isForbidden());
    }

    @Test
    void devePermitirAcessoComTokenValido() throws Exception {
        String email = "rafael@senai.com";
        String token = jwtService.generateToken(email, "ADMIN");

        UserDetails mockUser = User.withUsername(email)
                .password("encoded")
                .roles("ADMIN")
                .build();

        when(usuarioDetailsService.loadUserByUsername(email)).thenReturn(mockUser);

        mockMvc.perform(get("/professores")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
}
