package com.br.fiap.nexora.service;

import com.br.fiap.nexora.dto.TransacaoBancariaDTO;
import com.br.fiap.nexora.enums.TipoTransacao;
import com.br.fiap.nexora.model.ContaBancaria;
import com.br.fiap.nexora.model.TransacaoBancaria;
import com.br.fiap.nexora.repository.ContaBancariaRepository;
import com.br.fiap.nexora.repository.TransacaoBancariaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransacaoBancariaServiceTest {

    @Mock
    private TransacaoBancariaRepository transacaoRepository;

    @Mock
    private ContaBancariaRepository contaRepository;

    @InjectMocks
    private TransacaoBancariaService transacaoService;

    private ContaBancaria conta;
    private TransacaoBancariaDTO dto;
    private TransacaoBancaria transacao;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        conta = new ContaBancaria();
        conta.setId(1L);
        conta.setBanco("Banco XP");

        dto = new TransacaoBancariaDTO(null, "Depósito inicial", LocalDate.of(2024, 6, 1),
                1000.0f, TipoTransacao.ENTRADA, "Renda", conta.getId());

        transacao = new TransacaoBancaria(dto, conta);
        transacao.setId(1L);
    }

    @Test
    void deveCriarTransacaoComSucesso() {
        when(contaRepository.findById(dto.contaBancariaId())).thenReturn(Optional.of(conta));
        when(transacaoRepository.save(any())).thenReturn(transacao);

        TransacaoBancaria resultado = transacaoService.criarTransacao(dto);

        assertEquals("Depósito inicial", resultado.getDescricao());
        assertEquals(conta, resultado.getContaBancaria());
    }

    @Test
    void deveLancarExcecaoSeContaNaoExistirAoCriarTransacao() {
        when(contaRepository.findById(dto.contaBancariaId())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> transacaoService.criarTransacao(dto));
    }

    @Test
    void deveBuscarTransacaoPorId() {
        when(transacaoRepository.findById(1L)).thenReturn(Optional.of(transacao));

        Optional<TransacaoBancaria> resultado = transacaoService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(transacao, resultado.get());
    }

    @Test
    void deveBuscarTodasTransacoes() {
        when(transacaoRepository.findAll()).thenReturn(List.of(transacao));

        List<TransacaoBancaria> resultado = transacaoService.buscarTodas();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
    }

    @Test
    void deveBuscarTransacoesPorConta() {
        when(transacaoRepository.findByContaBancariaId(1L)).thenReturn(List.of(transacao));

        List<TransacaoBancaria> resultado = transacaoService.buscarPorConta(1L);

        assertEquals(1, resultado.size());
        assertEquals("Depósito inicial", resultado.get(0).getDescricao());
    }

    @Test
    void deveAtualizarTransacaoComSucesso() {
        when(transacaoRepository.findById(1L)).thenReturn(Optional.of(transacao));
        when(contaRepository.findById(dto.contaBancariaId())).thenReturn(Optional.of(conta));
        when(transacaoRepository.save(any())).thenReturn(transacao);

        Optional<TransacaoBancaria> resultado = transacaoService.atualizarTransacao(1L, dto);

        assertTrue(resultado.isPresent());
        assertEquals("Depósito inicial", resultado.get().getDescricao());
    }

    @Test
    void deveDeletarTransacaoComSucesso() {
        when(transacaoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(transacaoRepository).deleteById(1L);

        boolean resultado = transacaoService.deletarTransacao(1L);

        assertTrue(resultado);
    }

    @Test
    void deveRetornarFalseAoDeletarTransacaoInexistente() {
        when(transacaoRepository.existsById(99L)).thenReturn(false);

        boolean resultado = transacaoService.deletarTransacao(99L);

        assertFalse(resultado);
    }
}