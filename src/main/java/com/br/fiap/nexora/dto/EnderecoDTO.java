package com.br.fiap.nexora.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EnderecoDTO(
        @NotNull
        int cep,

        @NotBlank
        String rua,

        @NotNull
        int numero,

        String complemento,

        @NotBlank
        String bairro,

        @NotBlank
        String cidade,

        @NotBlank
        String uf
) {

}
