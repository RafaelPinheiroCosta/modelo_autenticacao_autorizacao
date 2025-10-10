package com.senai.modelo_autenticacao_autorizacao.application.dto;

import com.senai.modelo_autenticacao_autorizacao.domain.entity.Professor;
import com.senai.modelo_autenticacao_autorizacao.domain.enums.Role;
import lombok.Builder;

@Builder
public record ProfessorDTO(
        String id,
        String nome,
        String cpf,
        String email,
        String senha,
        Boolean ativo,
        Role role
) {
    public static ProfessorDTO fromEntity(Professor professor) {
        return ProfessorDTO.builder()
                .id(professor.getId())
                .nome(professor.getNome())
                .cpf(professor.getCpf())
                .email(professor.getEmail())
                .ativo(professor.isAtivo())
                .role(professor.getRole())
                .build();
    }

    public Professor toEntity() {
        return Professor.builder()
                .id(this.id)
                .nome(this.nome)
                .cpf(this.cpf)
                .email(this.email)
                .senha(this.senha)
                .ativo(this.ativo != null ? this.ativo : true)
                .role(this.role != null ? this.role : Role.PROFESSOR)
                .build();
    }

}
