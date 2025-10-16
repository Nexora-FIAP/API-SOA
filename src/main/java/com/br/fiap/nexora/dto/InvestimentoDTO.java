package com.br.fiap.nexora.dto;

import com.br.fiap.nexora.enums.TipoInvestimento;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record InvestimentoDTO(

        @Schema(description = "ID do investimento gerado automaticamente", example = "1")
        Long id,

        @Schema(description = "Tipo de investimento", example = "RENDA_FIXA")
        @NotNull
        TipoInvestimento tipo,

        @Schema(description = "Valor aplicado no investimento", example = "1000.00")
        @Positive
        float valorAplicado,

        @Schema(description = "Data de aplicação do investimento", example = "2025-10-16")
        @NotNull
        LocalDate dataAplicacao,

        @Schema(description = "Rendimento atual do investimento", example = "1050.75")
        @Positive
        float rendimentoAtual,

        @Schema(description = "CPF do cliente vinculado ao investimento", example = "12345678901")
        @NotNull
        String cpfCliente

) { }

