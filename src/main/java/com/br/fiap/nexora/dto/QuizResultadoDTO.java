package com.br.fiap.nexora.dto;

import com.br.fiap.nexora.enums.TipoQuiz;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record QuizResultadoDTO(
        Long id,

        @NotNull
        TipoQuiz tipo,

        @NotBlank
        String resultado,

        @NotNull
        LocalDate dataFeita,

        @NotBlank
        String cpfCliente // associando ao cliente pelo CPF
) { }
