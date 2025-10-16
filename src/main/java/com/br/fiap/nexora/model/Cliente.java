package com.br.fiap.nexora.model;

import com.br.fiap.nexora.dto.ClienteDTO;
import com.br.fiap.nexora.enums.PerfilInvestidor;
import com.br.fiap.nexora.repository.EnderecoRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Cliente")
@Getter
@Setter
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
    @JsonManagedReference
    List<ContaBancaria> contas = new ArrayList<>();

    @OneToMany(mappedBy = "cliente")
    @JsonManagedReference
    List<Investimento> investimentos = new ArrayList<>();

    @OneToMany(mappedBy = "cliente")
    @JsonManagedReference
    List<QuizResultado> quizzes = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cep_endereco")
    Endereco endereco;

    @JsonIgnore // não mostra a senha no JSON
    @NotBlank
    String senha;

    // Construtor que recebe DTO
    public Cliente(ClienteDTO clienteDTO) {
        this.cpf = clienteDTO.cpf();
        this.nome = clienteDTO.nome();
        this.email = clienteDTO.email();
        this.dataNascimento = clienteDTO.dataNascimento();
        this.perfilInvestidor = clienteDTO.perfilInvestidor();
        this.telefone = clienteDTO.telefone();
        this.endereco = new Endereco(clienteDTO.endereco());
        this.senha = clienteDTO.senha();
    }

    public void atualizar(ClienteDTO dto, EnderecoRepository enderecoRepository) {
        this.nome = dto.nome();
        this.email = dto.email();
        this.telefone = dto.telefone();
        this.perfilInvestidor = dto.perfilInvestidor();
        this.dataNascimento = dto.dataNascimento();

        if (dto.endereco() != null) {
            if (dto.endereco().id() != null) {
                // usar endereço existente
                this.endereco = enderecoRepository.findById(dto.endereco().id())
                        .orElseThrow(() -> new RuntimeException("Endereco não encontrado"));
            } else {
                // criar novo endereço
                this.endereco = new Endereco(dto.endereco());
            }
        }
    }


    // Método chamado antes de atualizar a entidade
    @PreUpdate
    public void preUpdate() {
        this.atualizadoEm = LocalDateTime.now();
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
