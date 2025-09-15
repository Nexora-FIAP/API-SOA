package com.br.fiap.nexora.model;

import com.br.fiap.nexora.enums.PerfilInvestidor;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "Cliente")
public class Cliente {
    @Id
    private String cpf;

    private String nome;
    private String email;
    private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    private PerfilInvestidor perfilInvestidor;

    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;

    private String telefone;
}
