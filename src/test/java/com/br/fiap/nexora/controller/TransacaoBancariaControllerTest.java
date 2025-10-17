package com.br.fiap.nexora.controller;

import com.br.fiap.nexora.dto.TransacaoBancariaDTO;
import com.br.fiap.nexora.enums.TipoTransacao;
import com.br.fiap.nexora.model.ContaBancaria;
import com.br.fiap.nexora.model.TransacaoBancaria;
import com.br.fiap.nexora.service.TransacaoBancariaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransacaoBancariaControllerTest {

    @Mock
    private TransacaoBancariaService transacaoService;

    @InjectMocks
    private TransacaoBancariaController transacaoController;

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
    void deveCadastrarTransacaoComSucesso() {
        when(transacaoService.criarTransacao(dto)).thenReturn(transacao);
        var response = transacaoController.cadastrarTransacao(dto);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(transacao, response.getBody());
    }

    @Test
    void deveBuscarTransacaoPorId() {
        when(transacaoService.buscarPorId(1L)).thenReturn(Optional.of(transacao));
        var response = transacaoController.buscarTransacao(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(transacao, response.getBody());
    }

    @Test
    void deveRetornarNotFoundAoBuscarTransacaoInexistente() {
        when(transacaoService.buscarPorId(99L)).thenReturn(Optional.empty());
        var response = transacaoController.buscarTransacao(99L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void deveListarTodasTransacoes() {
        when(transacaoService.buscarTodas()).thenReturn(List.of(transacao));
        var response = transacaoController.buscarTodasTransacoes();
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void deveRetornarNoContentSeNaoHouverTransacoes() {
        when(transacaoService.buscarTodas()).thenReturn(List.of());
        var response = transacaoController.buscarTodasTransacoes();
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void deveBuscarTransacoesPorConta() {
        when(transacaoService.buscarPorConta(1L)).thenReturn(List.of(transacao));
        var response = transacaoController.buscarTransacoesPorConta(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void deveRetornarNoContentAoBuscarTransacoesPorContaSemResultados() {
        when(transacaoService.buscarPorConta(99L)).thenReturn(List.of());
        var response = transacaoController.buscarTransacoesPorConta(99L);
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void deveAtualizarTransacaoComSucesso() {
        when(transacaoService.atualizarTransacao(1L, dto)).thenReturn(Optional.of(transacao));
        var response = transacaoController.atualizarTransacao(1L, dto);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(transacao, response.getBody());
    }

    @Test
    void deveRetornarNotFoundAoAtualizarTransacaoInexistente() {
        when(transacaoService.atualizarTransacao(99L, dto)).thenReturn(Optional.empty());
        var response = transacaoController.atualizarTransacao(99L, dto);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void deveDeletarTransacaoComSucesso() {
        when(transacaoService.deletarTransacao(1L)).thenReturn(true);
        var response = transacaoController.deletarTransacao(1L);
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void deveRetornarNotFoundAoDeletarTransacaoInexistente() {
        when(transacaoService.deletarTransacao(99L)).thenReturn(false);
        var response = transacaoController.deletarTransacao(99L);
        assertEquals(404, response.getStatusCodeValue());
    }
}