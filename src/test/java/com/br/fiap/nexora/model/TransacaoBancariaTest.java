package com.br.fiap.nexora.model;

import com.br.fiap.nexora.dto.TransacaoBancariaDTO;
import com.br.fiap.nexora.enums.TipoTransacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;


class TransacaoBancariaTest {

    private ContaBancaria conta;
    private TransacaoBancariaDTO dto;

    @BeforeEach
    void setUp() {
        conta = new ContaBancaria();
        conta.id = 1L;
        conta.banco = "Banco XP";
        conta.numeroConta = 123456;
        conta.agencia = "0001";
        conta.saldoAtual = 5000.0f;

        dto = new TransacaoBancariaDTO(
                null,
                "Depósito inicial",
                LocalDate.of(2024, 6, 1),
                1000.0f,
                TipoTransacao.ENTRADA,
                "Renda",
                conta.getId()
        );
    }

    @Test
    void deveCriarTransacaoBancariaAPartirDoDTO() {
        TransacaoBancaria transacao = new TransacaoBancaria(dto, conta);

        assertEquals("Depósito inicial", transacao.getDescricao());
        assertEquals(LocalDate.of(2024, 6, 1), transacao.getDataTransacao());
        assertEquals(1000.0f, transacao.getValor());
        assertEquals(TipoTransacao.ENTRADA, transacao.getTransacao());
        assertEquals("Renda", transacao.getCategoria());
        assertEquals(conta, transacao.getContaBancaria());
    }

    @Test
    void deveAtualizarTransacaoBancariaComNovoDTO() {
        TransacaoBancaria transacao = new TransacaoBancaria(dto, conta);

        TransacaoBancariaDTO novoDto = new TransacaoBancariaDTO(
                transacao.getId(),
                "Pagamento de conta",
                LocalDate.of(2024, 6, 10),
                200.0f,
                TipoTransacao.SAÍDA,
                "Despesas",
                conta.getId()
        );

        transacao.atualizar(novoDto, conta);

        assertEquals("Pagamento de conta", transacao.getDescricao());
        assertEquals(LocalDate.of(2024, 6, 10), transacao.getDataTransacao());
        assertEquals(200.0f, transacao.getValor());
        assertEquals(TipoTransacao.SAÍDA, transacao.getTransacao());
        assertEquals("Despesas", transacao.getCategoria());
        assertEquals(conta, transacao.getContaBancaria());
    }

    @Test
    void equalsEHashCodeDevemUsarId() {
        TransacaoBancaria t1 = new TransacaoBancaria(dto, conta);
        TransacaoBancaria t2 = new TransacaoBancaria(dto, conta);

        t1.id = 1L;
        t2.id = 1L;

        assertEquals(t1, t2);
        assertEquals(t1.hashCode(), t2.hashCode());
    }

    @Test
    void naoDeveSerIgualQuandoIdForDiferente() {
        TransacaoBancaria t1 = new TransacaoBancaria(dto, conta);
        TransacaoBancaria t2 = new TransacaoBancaria(dto, conta);

        t1.id = 1L;
        t2.id = 2L;

        assertNotEquals(t1, t2);
    }

}