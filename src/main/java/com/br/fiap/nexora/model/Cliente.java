package com.br.fiap.nexora.model;

import com.br.fiap.nexora.dto.ClienteDTO;
import com.br.fiap.nexora.enums.PerfilInvestidor;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Cliente")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "cpf")
public class Cliente {
    @Id
    String cpf; // mudou de int para String

    String nome;
    String email;
    LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    PerfilInvestidor perfilInvestidor;

    @Column(updatable = false) // só define na criação
    private LocalDateTime criadoEm = LocalDateTime.now();

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

    // Construtor que recebe DTO
    public Cliente(ClienteDTO clienteDTO) {
        this.cpf = clienteDTO.cpf();
        this.nome = clienteDTO.nome();
        this.email = clienteDTO.email();
        this.dataNascimento = clienteDTO.dataNascimento();
        this.perfilInvestidor = clienteDTO.perfilInvestidor();
        this.telefone = clienteDTO.telefone();
        this.endereco = new Endereco(clienteDTO.endereco());
    }

    public void atualizar(ClienteDTO clienteDTO) {
        this.nome = clienteDTO.nome();
        this.email = clienteDTO.email();
        this.dataNascimento = clienteDTO.dataNascimento();
        this.perfilInvestidor = clienteDTO.perfilInvestidor();
        this.telefone = clienteDTO.telefone();
        this.endereco = new Endereco(clienteDTO.endereco()); // sobrescreve o endereço
    }


    // Método chamado antes de atualizar a entidade
    @PreUpdate
    public void preUpdate() {
        this.atualizadoEm = LocalDateTime.now();
    }

}
