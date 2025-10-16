package com.br.fiap.nexora.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EnderecoDTO(

        @Schema(description = "ID do endereço gerado automaticamente", example = "1")
        Long id,

        @Schema(description = "CEP do endereço", example = "06454000")
        @NotNull
        String cep,

        @Schema(description = "Nome da rua", example = "Rua das Palmeiras")
        @NotBlank
        String rua,

        @Schema(description = "Número da residência", example = "123")
        @NotNull
        Integer numero,

        @Schema(description = "Complemento do endereço", example = "Apartamento 45B")
        String complemento,

        @Schema(description = "Bairro do endereço", example = "Centro")
        @NotBlank
        String bairro,

        @Schema(description = "Cidade do endereço", example = "Barueri")
        @NotBlank
        String cidade,

        @Schema(description = "UF do endereço", example = "SP")
        @NotBlank
        String uf

) { }

