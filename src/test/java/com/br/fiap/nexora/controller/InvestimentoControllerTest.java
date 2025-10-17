package com.br.fiap.nexora.controller;

import com.br.fiap.nexora.dto.InvestimentoDTO;
import com.br.fiap.nexora.enums.TipoInvestimento;
import com.br.fiap.nexora.model.Cliente;
import com.br.fiap.nexora.model.Investimento;
import com.br.fiap.nexora.service.InvestimentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InvestimentoControllerTest {

    @Mock
    private InvestimentoService investimentoService;

    @InjectMocks
    private InvestimentoController investimentoController;

    private Cliente cliente;
    private InvestimentoDTO dto;
    private Investimento investimento;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        cliente = new Cliente();
        cliente.setCpf("98765432100");
        cliente.setNome("Lety Investidora");

        dto = new InvestimentoDTO(
                null,
                TipoInvestimento.TESOURO_SELIC,
                5000.0f,
                LocalDate.of(2024, 1, 10),
                5500.0f,
                cliente.getCpf()
        );

        investimento = new Investimento(dto, cliente);
    }

    @Test
    void deveCadastrarInvestimentoComSucesso() {
        when(investimentoService.criarInvestimento(dto)).thenReturn(investimento);

        ResponseEntity<Investimento> response = investimentoController.cadastrarInvestimento(dto);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(investimento, response.getBody());
    }

    @Test
    void deveBuscarInvestimentoPorId() {
        when(investimentoService.buscarPorId(1L)).thenReturn(Optional.of(investimento));

        ResponseEntity<Investimento> response = investimentoController.buscarInvestimento(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(investimento, response.getBody());
    }

    @Test
    void deveRetornarNotFoundAoBuscarInvestimentoInexistente() {
        when(investimentoService.buscarPorId(99L)).thenReturn(Optional.empty());

        ResponseEntity<Investimento> response = investimentoController.buscarInvestimento(99L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void deveListarTodosInvestimentos() {
        when(investimentoService.buscarTodos()).thenReturn(List.of(investimento));

        ResponseEntity<List<Investimento>> response = investimentoController.buscarTodosInvestimentos();

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void deveRetornarNoContentSeNaoHouverInvestimentos() {
        when(investimentoService.buscarTodos()).thenReturn(List.of());

        ResponseEntity<List<Investimento>> response = investimentoController.buscarTodosInvestimentos();

        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void deveBuscarInvestimentosPorCpfCliente() {
        when(investimentoService.buscarPorCpfCliente("98765432100")).thenReturn(List.of(investimento));

        ResponseEntity<List<Investimento>> response = investimentoController.buscarInvestimentosPorCliente("98765432100");

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void deveRetornarNoContentAoBuscarInvestimentosPorCpfSemResultados() {
        when(investimentoService.buscarPorCpfCliente("00000000000")).thenReturn(List.of());

        ResponseEntity<List<Investimento>> response = investimentoController.buscarInvestimentosPorCliente("00000000000");

        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void deveAtualizarInvestimentoComSucesso() {
        when(investimentoService.atualizarInvestimento(1L, dto)).thenReturn(Optional.of(investimento));

        ResponseEntity<Investimento> response = investimentoController.atualizarInvestimento(1L, dto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(investimento, response.getBody());
    }

    @Test
    void deveRetornarNotFoundAoAtualizarInvestimentoInexistente() {
        when(investimentoService.atualizarInvestimento(99L, dto)).thenReturn(Optional.empty());

        ResponseEntity<Investimento> response = investimentoController.atualizarInvestimento(99L, dto);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void deveDeletarInvestimentoComSucesso() {
        when(investimentoService.deletarInvestimento(1L)).thenReturn(true);

        ResponseEntity<Void> response = investimentoController.deletarInvestimento(1L);

        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void deveRetornarNotFoundAoDeletarInvestimentoInexistente() {
        when(investimentoService.deletarInvestimento(99L)).thenReturn(false);

        ResponseEntity<Void> response = investimentoController.deletarInvestimento(99L);

        assertEquals(404, response.getStatusCodeValue());
    }
}