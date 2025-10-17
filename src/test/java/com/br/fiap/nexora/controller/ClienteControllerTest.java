package com.br.fiap.nexora.controller;

import com.br.fiap.nexora.dto.ClienteDTO;
import com.br.fiap.nexora.dto.EnderecoDTO;
import com.br.fiap.nexora.enums.PerfilInvestidor;
import com.br.fiap.nexora.model.Cliente;
import com.br.fiap.nexora.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    private ClienteDTO clienteDTO;
    private Cliente cliente;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        EnderecoDTO enderecoDTO = new EnderecoDTO(1L, "12345-678", "Rua A", 10, "Apto", "Centro", "São Paulo", "SP");
        clienteDTO = new ClienteDTO("12345678900", "Leticia", "lety@email.com", LocalDate.of(2004, 10, 15),
                PerfilInvestidor.MODERADO, "11999999999", enderecoDTO, "senha123");
        cliente = new Cliente(clienteDTO);
    }

    @Test
    void deveCadastrarClienteComSucesso() {
        when(clienteService.criarCliente(clienteDTO)).thenReturn(cliente);

        ResponseEntity<Cliente> response = clienteController.cadastrarCliente(clienteDTO);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(cliente, response.getBody());
    }

    @Test
    void deveBuscarClientePorCpf() {
        when(clienteService.buscarPorCpf("12345678900")).thenReturn(Optional.of(cliente));

        ResponseEntity<Cliente> response = clienteController.buscarCliente("12345678900");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(cliente, response.getBody());
    }

    @Test
    void deveRetornarNotFoundAoBuscarCpfInexistente() {
        when(clienteService.buscarPorCpf("00000000000")).thenReturn(Optional.empty());

        ResponseEntity<Cliente> response = clienteController.buscarCliente("00000000000");

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void deveListarTodosClientes() {
        when(clienteService.buscarTodos()).thenReturn(List.of(cliente));

        ResponseEntity<List<Cliente>> response = clienteController.buscarTodosClientes();

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void deveRetornarNoContentSeNaoHouverClientes() {
        when(clienteService.buscarTodos()).thenReturn(List.of());

        ResponseEntity<List<Cliente>> response = clienteController.buscarTodosClientes();

        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void deveAtualizarClienteComSucesso() {
        when(clienteService.atualizarCliente("12345678900", clienteDTO)).thenReturn(cliente);

        ResponseEntity<Cliente> response = clienteController.atualizarCliente("12345678900", clienteDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(cliente, response.getBody());
    }

    @Test
    void deveRetornarNotFoundAoAtualizarClienteInexistente() {
        when(clienteService.atualizarCliente("00000000000", clienteDTO)).thenThrow(new RuntimeException());

        ResponseEntity<Cliente> response = clienteController.atualizarCliente("00000000000", clienteDTO);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void deveDeletarClienteComSucesso() {
        doNothing().when(clienteService).deletarCliente("12345678900");

        ResponseEntity<Void> response = clienteController.deletarCliente("12345678900");

        assertEquals(204, response.getStatusCodeValue());
    }
}