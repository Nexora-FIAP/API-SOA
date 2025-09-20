package com.br.fiap.nexora.dto;

import com.br.fiap.nexora.enums.TipoTransacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record TransacaoBancariaDTO(
        Long id, // opcional ao criar uma nova transação

        @NotBlank
        String descricao,

        @NotNull
        LocalDate dataTransacao,

        @Positive
        float valor,

        @NotNull
        TipoTransacao transacao,

        @NotBlank
        String categoria,

        @NotNull
        Long contaBancariaId // referência à conta bancária
) { }
