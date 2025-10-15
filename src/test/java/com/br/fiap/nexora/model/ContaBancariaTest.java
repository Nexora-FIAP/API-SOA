package com.br.fiap.nexora.model;

import com.br.fiap.nexora.dto.ContaBancariaDTO;
import com.br.fiap.nexora.enums.TipoConta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ContaBancariaTest {

    private ContaBancariaDTO dto;
    private Cliente clienteMock;

    @BeforeEach
    void setUp() {
        dto = new ContaBancariaDTO(
                null,
                "Banco XPTO",
                123456,
                "0001",
                TipoConta.CORRENTE,
                "12345678900",
                1000.00f
        );

        clienteMock = Mockito.mock(Cliente.class);
    }

    @Test
    void deveConstruirContaBancariaCorretamente() {
        ContaBancaria conta = new ContaBancaria(dto, clienteMock);

        assertNull(conta.getId());
        assertEquals("Banco XPTO", conta.getBanco());
        assertEquals(123456, conta.getNumeroConta());
        assertEquals("0001", conta.getAgencia());
        assertEquals(TipoConta.CORRENTE, conta.getTipoConta());
        assertEquals(1000.00f, conta.getSaldoAtual());
        assertSame(clienteMock, conta.getCliente());
        assertNotNull(conta.getAtualizadoEm());
    }

    @Test
    void deveAtualizarContaBancariaCorretamente() {
        ContaBancaria conta = new ContaBancaria(dto, clienteMock);

        ContaBancariaDTO novoDto = new ContaBancariaDTO(
                null,
                "Banco Atualizado",
                654321,
                "0022",
                TipoConta.POUPANCA,
                "98765432100",
                2500.00f
        );

        Cliente novoClienteMock = Mockito.mock(Cliente.class);

        LocalDateTime antesAtualizacao = conta.getAtualizadoEm();
        conta.atualizar(novoDto, novoClienteMock);

        assertEquals("Banco Atualizado", conta.getBanco());
        assertEquals(654321, conta.getNumeroConta());
        assertEquals("0022", conta.getAgencia());
        assertEquals(TipoConta.POUPANCA, conta.getTipoConta());
        assertEquals(2500.00f, conta.getSaldoAtual());
        assertSame(novoClienteMock, conta.getCliente());
        assertTrue(conta.getAtualizadoEm().isAfter(antesAtualizacao));
    }
}