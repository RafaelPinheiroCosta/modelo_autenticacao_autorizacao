package com.senai.modelo_autenticacao_autorizacao.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name="professores")
public class Professor extends Usuario {

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="professor_turmas", joinColumns=@JoinColumn(name="professor_id"))
    @Column(name="turma")
    private List<String > listaDeTurmas;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="professor_ucs", joinColumns=@JoinColumn(name="professor_id"))
    @Column(name="uc")
    private List<String> listaDeUC;
}