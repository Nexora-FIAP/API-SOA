package com.br.fiap.nexora.repository;

import com.br.fiap.nexora.enums.TipoTransacao;
import com.br.fiap.nexora.model.ContaBancaria;
import com.br.fiap.nexora.model.TransacaoBancaria;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TransacaoBancariaRepositoryTest {

    @Autowired
    private TransacaoBancariaRepository transacaoRepository;

    @Autowired
    private ContaBancariaRepository contaRepository;

    @Test
    void deveSalvarERecuperarTransacaoPorConta() {
        ContaBancaria conta = new ContaBancaria();
        conta.setBanco("Banco XP");
        conta.setNumeroConta(123456);
        conta.setAgencia("0001");
        conta.setSaldoAtual(5000.0f);

        contaRepository.save(conta);

        TransacaoBancaria transacao = new TransacaoBancaria();
        transacao.setDescricao("Depósito inicial");
        transacao.setDataTransacao(LocalDate.of(2024, 6, 1));
        transacao.setValor(1000.0f);
        transacao.setTransacao(TipoTransacao.ENTRADA);
        transacao.setCategoria("Renda");
        transacao.setContaBancaria(conta);

        transacaoRepository.save(transacao);

        List<TransacaoBancaria> resultados = transacaoRepository.findByContaBancariaId(conta.getId());

        assertFalse(resultados.isEmpty());
        assertEquals("Depósito inicial", resultados.get(0).getDescricao());
        assertEquals(conta.getId(), resultados.get(0).getContaBancaria().getId());
    }
}