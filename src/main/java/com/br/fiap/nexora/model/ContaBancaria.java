package com.br.fiap.nexora.model;

import com.br.fiap.nexora.dto.ContaBancariaDTO;
import com.br.fiap.nexora.enums.TipoConta;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "ContaBancaria")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ContaBancaria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String banco;

    @Column(unique = true, nullable = false)
    int numeroConta;

    String agencia;

    @Enumerated(EnumType.STRING)
    TipoConta tipoConta;

    @ManyToOne
    @JoinColumn(name = "cpf_cliente")
    @JsonBackReference
    Cliente cliente;

    float saldoAtual;

    private LocalDateTime atualizadoEm;

    @OneToMany(mappedBy = "contaBancaria")
    private List<TransacaoBancaria> transacoes = new ArrayList<>();

    // Construtor que recebe DTO
    public ContaBancaria(ContaBancariaDTO dto, Cliente cliente) {
        this.banco = dto.banco();
        this.numeroConta = dto.numeroConta();
        this.agencia = dto.agencia();
        this.tipoConta = dto.tipoConta();
        this.saldoAtual = dto.saldoAtual();
        this.cliente = cliente;
        this.atualizadoEm = LocalDateTime.now();
    }

    // Método para atualizar a conta a partir de DTO
    public void atualizar(ContaBancariaDTO dto, Cliente cliente) {
        this.banco = dto.banco();
        this.numeroConta = dto.numeroConta();
        this.agencia = dto.agencia();
        this.tipoConta = dto.tipoConta();
        this.saldoAtual = dto.saldoAtual();
        this.cliente = cliente;
        this.atualizadoEm = LocalDateTime.now();
    }
}
