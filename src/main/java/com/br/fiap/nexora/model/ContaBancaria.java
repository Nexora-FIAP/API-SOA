package com.br.fiap.nexora.model;

import com.br.fiap.nexora.enums.TipoConta;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "ContaBancaria")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
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
    @JoinColumn(name = "cpf_cliente")
    Cliente cliente;

    float saldoAtual;

    private LocalDateTime atualizadoEm;

    @OneToMany(mappedBy = "contaBancaria")
    private List<TransacaoBancaria> transacoes = new ArrayList<>();
}
