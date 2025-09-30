package com.senai.modelo_autenticacao_autorizacao.interface_ui.controller;

import com.senai.modelo_autenticacao_autorizacao.application.dto.ProfessorDTO;
import com.senai.modelo_autenticacao_autorizacao.application.service.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/professores")
@RequiredArgsConstructor
public class ProfessorController {

    private final ProfessorService service;

    @GetMapping
    public ResponseEntity<List<ProfessorDTO>> listarTodosProfessores() {
        List<ProfessorDTO> professores = service.listarTodosProfessores();
        return ResponseEntity.ok(professores);
    }
}
