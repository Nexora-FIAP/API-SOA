package com.br.fiap.nexora.model;

import com.br.fiap.nexora.dto.EnderecoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnderecoTest {

    private EnderecoDTO dto;

    @BeforeEach
    void setUp() {
        dto = new EnderecoDTO(
                null,
                "06543-000",
                "Rua das Flores",
                123,
                "Apto 45",
                "Centro",
                "Santana de Parnaíba",
                "SP"
        );
    }

    @Test
    void deveConstruirEnderecoCorretamenteAPartirDoDTO() {
        Endereco endereco = new Endereco(dto);

        assertNull(endereco.getId());
        assertEquals("06543-000", endereco.getCep());
        assertEquals("Rua das Flores", endereco.getRua());
        assertEquals(123, endereco.getNumero());
        assertEquals("Apto 45", endereco.getComplemento());
        assertEquals("Centro", endereco.getBairro());
        assertEquals("Santana de Parnaíba", endereco.getCidade());
        assertEquals("SP", endereco.getUf());
    }

    @Test
    void deveAtualizarEnderecoComNovoDTO() {
        Endereco endereco = new Endereco(dto);

        EnderecoDTO novoDto = new EnderecoDTO(
                null,
                "06544-111",
                "Rua Nova",
                456,
                "Casa 2",
                "Jardim",
                "Barueri",
                "SP"
        );

        endereco.atualizar(novoDto);

        assertEquals("06544-111", endereco.getCep());
        assertEquals("Rua Nova", endereco.getRua());
        assertEquals(456, endereco.getNumero());
        assertEquals("Casa 2", endereco.getComplemento());
        assertEquals("Jardim", endereco.getBairro());
        assertEquals("Barueri", endereco.getCidade());
        assertEquals("SP", endereco.getUf());
    }
}