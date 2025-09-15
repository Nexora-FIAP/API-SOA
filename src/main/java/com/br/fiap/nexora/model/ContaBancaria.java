package com.br.fiap.nexora.model;

import com.br.fiap.nexora.enums.TipoConta;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "ContaBancaria")
@Getter
@Setter
public class ContaBancaria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String banco;

    int numeroConta;

    String agencia;

    @Enumerated(EnumType.STRING)
    TipoConta tipoConta;

    @ManyToOne
    @JoinColumn(name = "cpf")
    Cliente cliente;

    float saldoAtual;

    private LocalDateTime atualizadoEm;
}
