package com.br.fiap.nexora.model;

import com.br.fiap.nexora.dto.TransacaoBancariaDTO;
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

    @Enumerated(EnumType.STRING)
    TipoTransacao transacao;

    String categoria;

    @ManyToOne
    @JoinColumn(name = "conta_bancaria")
    ContaBancaria contaBancaria;

    // Construtor que recebe DTO
    public TransacaoBancaria(TransacaoBancariaDTO dto, ContaBancaria conta) {
        this.descricao = dto.descricao();
        this.dataTransacao = dto.dataTransacao();
        this.valor = dto.valor();
        this.transacao = dto.transacao();
        this.categoria = dto.categoria();
        this.contaBancaria = conta;
    }

    // Método para atualizar a partir de DTO
    public void atualizar(TransacaoBancariaDTO dto, ContaBancaria conta) {
        this.descricao = dto.descricao();
        this.dataTransacao = dto.dataTransacao();
        this.valor = dto.valor();
        this.transacao = dto.transacao();
        this.categoria = dto.categoria();
        this.contaBancaria = conta;
    }
}
