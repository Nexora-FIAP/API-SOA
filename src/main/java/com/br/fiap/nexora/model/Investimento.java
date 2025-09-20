package com.br.fiap.nexora.model;

import com.br.fiap.nexora.enums.TipoInvestimento;
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
    Cliente cliente;
}
