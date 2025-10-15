package com.br.fiap.nexora.model;

import com.br.fiap.nexora.dto.TransacaoBancariaDTO;
import com.br.fiap.nexora.enums.TipoTransacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TransacaoBancariaTest {

    private TransacaoBancariaDTO dto;
    private ContaBancaria contaMock;

    @BeforeEach
    void setUp() {
        dto = new TransacaoBancariaDTO(
                "Pagamento de boleto",
                LocalDate.of(2025, 10, 14),
                150.75f,
                TipoTransacao.SAIDA,
                "Contas"
        );

        contaMock = Mockito.mock(ContaBancaria.class);
    }

    @Test
    void deveConstruirTransacaoBancariaCorretamente() {
        TransacaoBancaria transacao = new TransacaoBancaria(dto, contaMock);

        assertNull(transacao.getId());
        assertEquals("Pagamento de boleto", transacao.getDescricao());
        assertEquals(LocalDate.of(2025, 10, 14), transacao.getDataTransacao());
        assertEquals(150.75f, transacao.getValor());
        assertEquals(TipoTransacao.SAIDA, transacao.getTransacao());
        assertEquals("Contas", transacao.getCategoria());
        assertEquals(contaMock, transacao.getContaBancaria());
    }

    @Test
    void deveAtualizarTransacaoBancariaCorretamente() {
        TransacaoBancaria transacao = new TransacaoBancaria(dto, contaMock);

        TransacaoBancariaDTO novoDto = new TransacaoBancariaDTO(
                "Transferência recebida",
                LocalDate.of(2025, 10, 15),
                300.00f,
                TipoTransacao.ENTRADA,
                "Transferências"
        );

        ContaBancaria novaContaMock = Mockito.mock(ContaBancaria.class);
        transacao.atualizar(novoDto, novaContaMock);

        assertEquals("Transferência recebida", transacao.getDescricao());
        assertEquals(LocalDate.of(2025, 10, 15), transacao.getDataTransacao());
        assertEquals(300.00f, transacao.getValor());
        assertEquals(TipoTransacao.ENTRADA, transacao.getTransacao());
        assertEquals("Transferências", transacao.getCategoria());
        assertEquals(novaContaMock, transacao.getContaBancaria());
    }
}