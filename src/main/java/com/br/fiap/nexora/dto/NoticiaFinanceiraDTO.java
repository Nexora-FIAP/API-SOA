package com.br.fiap.nexora.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record NoticiaFinanceiraDTO(

        @Schema(description = "ID da notícia financeira gerado automaticamente", example = "1")
        Long id,

        @Schema(description = "Título da notícia", example = "Mercado financeiro fecha em alta")
        @NotBlank
        String titulo,

        @Schema(description = "Conteúdo completo da notícia", example = "O mercado financeiro apresentou alta de 2% nesta quinta-feira...")
        @NotBlank
        String conteudo,

        @Schema(description = "Fonte da notícia", example = "Valor Econômico")
        @NotBlank
        String fonte,

        @Schema(description = "Data de publicação da notícia", example = "2025-10-16")
        @NotNull
        LocalDate dataPublicacao

) { }
