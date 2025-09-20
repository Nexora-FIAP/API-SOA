package com.br.fiap.nexora.model;

import com.br.fiap.nexora.enums.TipoInvestimento;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity(name = "Investimento")
@Getter
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
    Cliente cliente;
}
