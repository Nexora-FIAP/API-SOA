package com.br.fiap.nexora.model;

import com.br.fiap.nexora.enums.PerfilInvestidor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Cliente")
@Getter
@Setter
public class Cliente {
    @Id
    int cpf;

    String nome;
    String email;
    LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    PerfilInvestidor perfilInvestidor;

    LocalDateTime criadoEm;
    LocalDateTime atualizadoEm;

    String telefone;

    @OneToMany(mappedBy = "cliente")
    List<ContaBancaria> contas = new ArrayList<>();

    @OneToMany(mappedBy = "cliente")
    List<Investimento> investimentos = new ArrayList<>();

    @OneToMany(mappedBy = "cliente")
    private List<QuizResultado> quizzes = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cep_endereco")
    private Endereco endereco;
}
