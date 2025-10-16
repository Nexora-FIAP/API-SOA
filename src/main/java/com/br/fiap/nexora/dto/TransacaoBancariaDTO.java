package com.br.fiap.nexora.dto;

import com.br.fiap.nexora.enums.TipoTransacao;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record TransacaoBancariaDTO(

        @Schema(description = "ID da transação bancária gerado automaticamente", example = "1")
        Long id,

        @Schema(description = "Descrição da transação", example = "Pagamento de conta de luz")
        @NotBlank
        String descricao,

        @Schema(description = "Data em que a transação foi realizada", example = "2025-10-16")
        @NotNull
        LocalDate dataTransacao,

        @Schema(description = "Valor da transação", example = "250.75")
        @Positive
        float valor,

        @Schema(description = "Tipo da transação", example = "SAIDA")
        @NotNull
        TipoTransacao transacao,

        @Schema(description = "Categoria da transação", example = "Despesas Fixas")
        @NotBlank
        String categoria,

        @Schema(description = "ID da conta bancária associada à transação", example = "3")
        @NotNull
        Long contaBancariaId

) { }

