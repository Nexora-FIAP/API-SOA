package com.br.fiap.nexora.model;

import com.br.fiap.nexora.enums.TipoTransacao;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity(name = "TransacaoBancaria")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class TransacaoBancaria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String descricao;

    LocalDate dataTransacao;

    float valor;

    TipoTransacao transacao;

    String categoria;

    @ManyToOne
    @JoinColumn(name = "conta_bancaria")
    ContaBancaria contaBancaria;
}
