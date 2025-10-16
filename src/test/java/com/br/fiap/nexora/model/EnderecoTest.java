package com.br.fiap.nexora.model;

import com.br.fiap.nexora.dto.EnderecoDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnderecoTest {

    @Test
    void deveCriarEnderecoAPartirDoDTO() {
        EnderecoDTO dto = new EnderecoDTO(
                1L,
                "12345-678",
                "Rua das Flores",
                100,
                "Apto 202",
                "Centro",
                "São Paulo",
                "SP"
        );

        Endereco endereco = new Endereco(dto);

        assertEquals(dto.cep(), endereco.getCep());
        assertEquals(dto.rua(), endereco.getRua());
        assertEquals(dto.numero(), endereco.getNumero());
        assertEquals(dto.complemento(), endereco.getComplemento());
        assertEquals(dto.bairro(), endereco.getBairro());
        assertEquals(dto.cidade(), endereco.getCidade());
        assertEquals(dto.uf(), endereco.getUf());
    }

    @Test
    void deveAtualizarEnderecoComNovoDTO() {
        EnderecoDTO dtoInicial = new EnderecoDTO(
                1L,
                "11111-111",
                "Rua A",
                10,
                "Casa",
                "Bairro A",
                "Cidade A",
                "AA"
        );

        Endereco endereco = new Endereco(dtoInicial);

        EnderecoDTO dtoNovo = new EnderecoDTO(
                2L,
                "22222-222",
                "Rua B",
                20,
                "Apto 5",
                "Bairro B",
                "Cidade B",
                "BB"
        );

        endereco.atualizar(dtoNovo);

        assertEquals("22222-222", endereco.getCep());
        assertEquals("Rua B", endereco.getRua());
        assertEquals(20, endereco.getNumero());
        assertEquals("Apto 5", endereco.getComplemento());
        assertEquals("Bairro B", endereco.getBairro());
        assertEquals("Cidade B", endereco.getCidade());
        assertEquals("BB", endereco.getUf());
    }
}
