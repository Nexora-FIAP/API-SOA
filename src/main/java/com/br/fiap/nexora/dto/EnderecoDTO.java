package com.br.fiap.nexora.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EnderecoDTO(
        Long id,

        @NotNull
        String cep,

        @NotBlank
        String rua,

        @NotNull
        Integer numero,

        String complemento,

        @NotBlank
        String bairro,

        @NotBlank
        String cidade,

        @NotBlank
        String uf
) { }
