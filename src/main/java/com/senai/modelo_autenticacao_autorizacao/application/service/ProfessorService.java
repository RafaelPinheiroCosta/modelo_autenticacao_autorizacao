package com.senai.modelo_autenticacao_autorizacao.application.service;

import com.senai.modelo_autenticacao_autorizacao.application.dto.ProfessorDTO;
import com.senai.modelo_autenticacao_autorizacao.domain.repository.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ProfessorService {

    private final ProfessorRepository professorRepository;

    public List<ProfessorDTO> listarTodosProfessores() {
        return professorRepository.findAll().stream()
                .map(ProfessorDTO::fromEntity)
                .toList();
    }
}
