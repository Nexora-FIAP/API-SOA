package com.br.fiap.nexora.model;

import com.br.fiap.nexora.dto.InvestimentoDTO;
import com.br.fiap.nexora.enums.TipoInvestimento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class InvestimentoTest {

    private InvestimentoDTO dto;
    private Cliente clienteMock;

    @BeforeEach
    void setUp() {
        dto = new InvestimentoDTO(
                null,
                TipoInvestimento.RENDA_FIXA,
                5000.00f,
                LocalDate.of(2025, 10, 1),
                5500.00f,
                "12345678900"
        );

        clienteMock = Mockito.mock(Cliente.class);
    }

    @Test
    void deveConstruirInvestimentoCorretamenteAPartirDoDTO() {
        Investimento investimento = new Investimento(dto, clienteMock);

        assertNull(investimento.getId());
        assertEquals(TipoInvestimento.RENDA_FIXA, investimento.getTipo());
        assertEquals(5000.00f, investimento.getValorAplicado());
        assertEquals(LocalDate.of(2025, 10, 1), investimento.getDataAplicacao());
        assertEquals(5500.00f, investimento.getRendimentoAtual());
        assertSame(clienteMock, investimento.getCliente());
    }

    @Test
    void deveAtualizarInvestimentoCorretamente() {
        Investimento investimento = new Investimento(dto, clienteMock);

        InvestimentoDTO novoDto = new InvestimentoDTO(
                null,
                TipoInvestimento.FUNDOS_IMOBILIARIOS,
                8000.00f,
                LocalDate.of(2025, 10, 10),
                8200.00f,
                "12345678900"
        );

        Cliente novoClienteMock = Mockito.mock(Cliente.class);
        investimento.atualizar(novoDto, novoClienteMock);

        assertEquals(TipoInvestimento.FUNDOS_IMOBILIARIOS, investimento.getTipo());
        assertEquals(8000.00f, investimento.getValorAplicado());
        assertEquals(LocalDate.of(2025, 10, 10), investimento.getDataAplicacao());
        assertEquals(8200.00f, investimento.getRendimentoAtual());
        assertSame(novoClienteMock, investimento.getCliente());
    }
}