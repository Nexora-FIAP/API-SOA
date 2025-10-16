package com.br.fiap.nexora.dto;

import com.br.fiap.nexora.enums.TipoConta;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ContaBancariaDTO(

        @Schema(description = "ID da conta bancária gerado automaticamente", example = "1")
        Long id,

        @Schema(description = "Nome do banco", example = "Banco do Brasil")
        @NotBlank
        String banco,

        @Schema(description = "Número da conta bancária", example = "123456")
        @Positive
        int numeroConta,

        @Schema(description = "Número da agência bancária", example = "0001")
        @NotBlank
        String agencia,

        @Schema(description = "Tipo da conta bancária", example = "CORRENTE")
        @NotNull
        TipoConta tipoConta,

        @Schema(description = "CPF do cliente vinculado à conta", example = "12345678901")
        @NotBlank
        String cpfCliente,

        @Schema(description = "Saldo atual da conta", example = "1500.75")
        @Positive
        float saldoAtual

) { }

