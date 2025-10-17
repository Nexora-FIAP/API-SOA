package com.br.fiap.nexora.controller;

import com.br.fiap.nexora.dto.ContaBancariaDTO;
import com.br.fiap.nexora.enums.TipoConta;
import com.br.fiap.nexora.model.Cliente;
import com.br.fiap.nexora.model.ContaBancaria;
import com.br.fiap.nexora.service.ContaBancariaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContaBancariaControllerTest {

    @Mock
    private ContaBancariaService contaBancariaService;

    @InjectMocks
    private ContaBancariaController contaBancariaController;

    private ContaBancariaDTO dto;
    private ContaBancaria conta;
    private Cliente cliente;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        cliente = new Cliente();
        cliente.setCpf("12345678900");

        dto = new ContaBancariaDTO(null, "Banco Nexora", 12345, "0001", TipoConta.CORRENTE, cliente.getCpf(), 1500.0f);
        conta = new ContaBancaria(dto, cliente);
    }

    @Test
    void deveCadastrarContaComSucesso() {
        when(contaBancariaService.criarConta(dto)).thenReturn(conta);

        ResponseEntity<ContaBancaria> response = contaBancariaController.cadastrarConta(dto);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(conta, response.getBody());
    }

    @Test
    void deveBuscarContaPorId() {
        when(contaBancariaService.buscarPorId(1L)).thenReturn(Optional.of(conta));

        ResponseEntity<ContaBancaria> response = contaBancariaController.buscarConta(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(conta, response.getBody());
    }

    @Test
    void deveRetornarNotFoundAoBuscarContaInexistente() {
        when(contaBancariaService.buscarPorId(99L)).thenReturn(Optional.empty());

        ResponseEntity<ContaBancaria> response = contaBancariaController.buscarConta(99L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void deveListarTodasAsContas() {
        when(contaBancariaService.buscarTodas()).thenReturn(List.of(conta));

        ResponseEntity<List<ContaBancaria>> response = contaBancariaController.buscarTodasContas();

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void deveRetornarNoContentSeNaoHouverContas() {
        when(contaBancariaService.buscarTodas()).thenReturn(List.of());

        ResponseEntity<List<ContaBancaria>> response = contaBancariaController.buscarTodasContas();

        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void deveAtualizarContaComSucesso() {
        when(contaBancariaService.atualizarConta(1L, dto)).thenReturn(Optional.of(conta));

        ResponseEntity<ContaBancaria> response = contaBancariaController.atualizarConta(1L, dto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(conta, response.getBody());
    }

    @Test
    void deveRetornarNotFoundAoAtualizarContaInexistente() {
        when(contaBancariaService.atualizarConta(99L, dto)).thenReturn(Optional.empty());

        ResponseEntity<ContaBancaria> response = contaBancariaController.atualizarConta(99L, dto);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void deveDeletarContaComSucesso() {
        when(contaBancariaService.deletarConta(1L)).thenReturn(true);

        ResponseEntity<Void> response = contaBancariaController.deletarConta(1L);

        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void deveRetornarNotFoundAoDeletarContaInexistente() {
        when(contaBancariaService.deletarConta(99L)).thenReturn(false);

        ResponseEntity<Void> response = contaBancariaController.deletarConta(99L);

        assertEquals(404, response.getStatusCodeValue());
    }
}