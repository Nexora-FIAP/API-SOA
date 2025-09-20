package com.br.fiap.nexora.dto;

import com.br.fiap.nexora.enums.TipoConta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ContaBancariaDTO(
        Long id,

        @NotBlank
        String banco,

        @Positive
        int numeroConta,

        @NotBlank
        String agencia,

        @NotNull
        TipoConta tipoConta,

        @NotBlank
        String cpfCliente, // referência ao cliente

        @Positive
        float saldoAtual
) { }
