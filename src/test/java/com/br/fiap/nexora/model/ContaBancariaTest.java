package com.br.fiap.nexora.model;

import com.br.fiap.nexora.dto.ContaBancariaDTO;
import com.br.fiap.nexora.enums.TipoConta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ContaBancariaTest {


    private Cliente cliente;
    private ContaBancariaDTO dto;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setCpf("12345678900");
        cliente.setNome("Lety Teste");

        dto = new ContaBancariaDTO(
                null,
                "Banco Nexora",
                12345,
                "0001",
                TipoConta.CORRENTE,
                cliente.getCpf(),
                1500.0f
        );
    }

    @Test
    void deveCriarContaCorretamenteAPartirDoDTO() {
        ContaBancaria conta = new ContaBancaria(dto, cliente);

        assertEquals("Banco Nexora", conta.getBanco());
        assertEquals(12345, conta.getNumeroConta());
        assertEquals("0001", conta.getAgencia());
        assertEquals(TipoConta.CORRENTE, conta.getTipoConta());
        assertEquals(cliente, conta.getCliente());
        assertEquals(1500.0f, conta.getSaldoAtual());
        assertNotNull(conta.getAtualizadoEm());
    }

    @Test
    void deveAtualizarDadosCorretamenteComDTO() {
        ContaBancaria conta = new ContaBancaria(dto, cliente);

        ContaBancariaDTO novoDto = new ContaBancariaDTO(
                conta.getId(),
                "Banco Atualizado",
                54321,
                "9999",
                TipoConta.POUPANÇA,
                cliente.getCpf(),
                2000.0f
        );

        conta.atualizar(novoDto, cliente);

        assertEquals("Banco Atualizado", conta.getBanco());
        assertEquals(54321, conta.getNumeroConta());
        assertEquals("9999", conta.getAgencia());
        assertEquals(TipoConta.POUPANÇA, conta.getTipoConta());
        assertEquals(2000.0f, conta.getSaldoAtual());
    }

    @Test
    void deveInicializarComListaDeTransacoesVazia() {
        ContaBancaria conta = new ContaBancaria(dto, cliente);
        assertNotNull(conta.getTransacoes());
        assertTrue(conta.getTransacoes().isEmpty());
    }

    @Test
    void equalsEHashCodeDevemUsarId() {
        ContaBancaria c1 = new ContaBancaria(dto, cliente);
        ContaBancaria c2 = new ContaBancaria(dto, cliente);

        c1.id = 1L;
        c2.id = 1L;

        assertEquals(c1, c2);
        assertEquals(c1.hashCode(), c2.hashCode());
    }

    @Test
    void naoDeveSerIgualQuandoIdForDiferente() {
        ContaBancaria c1 = new ContaBancaria(dto, cliente);
        ContaBancaria c2 = new ContaBancaria(dto, cliente);

        c1.id = 1L;
        c2.id = 2L;

        assertNotEquals(c1, c2);
    }


}
