package com.br.fiap.nexora.model;

import com.br.fiap.nexora.dto.ClienteDTO;
import com.br.fiap.nexora.enums.PerfilInvestidor;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Cliente")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "cpf")
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
    List<QuizResultado> quizzes = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cep_endereco")
    Endereco endereco;

    public Cliente(@Valid ClienteDTO clienteDTO) {
        this.nome = clienteDTO.nome();
        this.email = clienteDTO.email();
        this.dataNascimento = clienteDTO.dataNascimento();
        this.perfilInvestidor = clienteDTO.perfilInvestidor();
        this.telefone = clienteDTO.telefone();
    }
}
