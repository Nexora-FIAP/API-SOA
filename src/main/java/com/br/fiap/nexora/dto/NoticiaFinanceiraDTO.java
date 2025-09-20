package com.br.fiap.nexora.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record NoticiaFinanceiraDTO(
        Long id,

        @NotBlank
        String titulo,

        @NotBlank
        String conteudo,

        @NotBlank
        String fonte,

        @NotNull
        LocalDate dataPublicacao
) { }