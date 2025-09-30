package com.senai.modelo_autenticacao_autorizacao.domain.repository;

import com.senai.modelo_autenticacao_autorizacao.domain.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfessorRepository extends JpaRepository<Professor, String> {
    Optional<Professor> findByEmail(String email);
}
