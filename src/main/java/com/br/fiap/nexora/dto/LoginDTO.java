package com.br.fiap.nexora.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginDTO(

        @Schema(description = "CPF do cliente (11 dígitos)", example = "12345678901")
        String cpf,

        @Schema(description = "Senha de acesso do cliente", example = "senhaSegura123")
        String senha
) { }
