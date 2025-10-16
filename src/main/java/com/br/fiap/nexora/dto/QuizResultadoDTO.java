package com.br.fiap.nexora.dto;

import com.br.fiap.nexora.enums.TipoQuiz;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record QuizResultadoDTO(

        @Schema(description = "ID do resultado do quiz gerado automaticamente", example = "1")
        Long id,

        @Schema(description = "Tipo de quiz realizado", example = "CONHECIMENTO_FINANCEIRO")
        @NotNull
        TipoQuiz tipo,

        @Schema(description = "Resultado obtido no quiz", example = "Avançado")
        @NotBlank
        String resultado,

        @Schema(description = "Data em que o quiz foi realizado", example = "2025-10-16")
        @NotNull
        LocalDate dataFeita,

        @Schema(description = "CPF do cliente que realizou o quiz", example = "12345678901")
        @NotBlank
        String cpfCliente

) { }

