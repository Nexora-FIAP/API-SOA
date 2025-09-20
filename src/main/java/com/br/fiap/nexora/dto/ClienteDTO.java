package com.br.fiap.nexora.dto;

import com.br.fiap.nexora.enums.PerfilInvestidor;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record ClienteDTO(
        @NotBlank
        String nome,

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Pattern(regexp = "\\d{11}")
        String cpf,

        @NotNull
        LocalDate dataNascimento,

        @NotNull
        PerfilInvestidor perfilInvestidor,

        @NotBlank
        String telefone,

        @NotNull
        @Valid
        EnderecoDTO endereco
) { }
