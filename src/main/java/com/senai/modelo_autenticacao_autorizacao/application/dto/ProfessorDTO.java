package com.senai.modelo_autenticacao_autorizacao.application.dto;

import com.senai.modelo_autenticacao_autorizacao.domain.entity.Professor;

public record ProfessorDTO(
        String nome
) {
    public static ProfessorDTO fromEntity(Professor professor) {
        return new ProfessorDTO(professor.getNome())
    }
}
