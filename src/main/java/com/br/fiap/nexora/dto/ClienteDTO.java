package com.br.fiap.nexora.dto;

import com.br.fiap.nexora.enums.PerfilInvestidor;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record ClienteDTO(

        @Schema(description = "Nome completo do cliente", example = "Marcelo Silva")
        @NotBlank
        String nome,

        @Schema(description = "Email do cliente", example = "marcelosilva@gmail.com")
        @NotBlank
        @Email
        String email,

        @Schema(description = "CPF do cliente (11 dígitos)", example = "12345678901")
        @NotBlank
        @Pattern(regexp = "\\d{11}")
        String cpf,

        @Schema(description = "Data de nascimento do cliente", example = "1995-08-20")
        @NotNull
        LocalDate dataNascimento,

        @Schema(description = "Perfil de investidor do cliente", example = "MODERADO")
        @NotNull
        PerfilInvestidor perfilInvestidor,

        @Schema(description = "Telefone do cliente", example = "11912345678")
        @NotBlank
        String telefone,

        @Schema(description = "Endereço do cliente")
        @NotNull
        EnderecoDTO endereco,

        @Schema(description = "Senha de acesso do cliente", example = "senhaSegura123")
        @NotBlank
        String senha

) { }

