package com.br.fiap.nexora.dto;

import com.br.fiap.nexora.enums.TipoInvestimento;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record InvestimentoDTO(
        Long id, 

        @NotNull
        TipoInvestimento tipo,

        @Positive
        float valorAplicado,

        @NotNull
        LocalDate dataAplicacao,

        @Positive
        float rendimentoAtual,

        @NotNull
        String cpfCliente // referência ao cliente pelo CPF
) { }
