package com.senai.modelo_autenticacao_autorizacao.application.service;

import com.senai.modelo_autenticacao_autorizacao.application.dto.ProfessorDTO;
import com.senai.modelo_autenticacao_autorizacao.domain.entity.Professor;
import com.senai.modelo_autenticacao_autorizacao.domain.enums.Role;
import com.senai.modelo_autenticacao_autorizacao.domain.repository.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ProfessorService {

    private final ProfessorRepository professorRepository;

    private final PasswordEncoder encoder;

    @PreAuthorize("hasAnyRole('ADMIN','PROFESSOR')")
    public List<ProfessorDTO> listarTodosProfessores() {
        return professorRepository.findAll().stream()
                .map(ProfessorDTO::fromEntity)
                .toList();
    }


    @PreAuthorize("hasAnyRole('ADMIN')")
    public ProfessorDTO cadastrarProfessor(ProfessorDTO dto) {
        Professor entity = dto.toEntity();
        entity.setSenha(encoder.encode(dto.senha()));
        entity.setRole(Role.PROFESSOR);
        professorRepository.save(entity);
        return ProfessorDTO.fromEntity(entity);
    }
}
