package com.br.fiap.nexora.model;

import com.br.fiap.nexora.dto.InvestimentoDTO;
import com.br.fiap.nexora.enums.TipoInvestimento;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity(name = "Investimento")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Investimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    TipoInvestimento tipo;

    float valorAplicado;

    LocalDate dataAplicacao;

    float rendimentoAtual;

    @ManyToOne
    @JoinColumn(name = "cpf")
    @JsonBackReference
    Cliente cliente;

    // Construtor que recebe DTO
    public Investimento(InvestimentoDTO dto, Cliente cliente) {
        this.tipo = dto.tipo();
        this.valorAplicado = dto.valorAplicado();
        this.dataAplicacao = dto.dataAplicacao();
        this.rendimentoAtual = dto.rendimentoAtual();
        this.cliente = cliente;
    }

    // Método para atualizar a partir de DTO
    public void atualizar(InvestimentoDTO dto, Cliente cliente) {
        this.tipo = dto.tipo();
        this.valorAplicado = dto.valorAplicado();
        this.dataAplicacao = dto.dataAplicacao();
        this.rendimentoAtual = dto.rendimentoAtual();
        this.cliente = cliente;
    }
}
