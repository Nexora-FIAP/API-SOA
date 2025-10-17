package com.br.fiap.nexora.controller;

import com.br.fiap.nexora.dto.EnderecoDTO;
import com.br.fiap.nexora.model.Endereco;
import com.br.fiap.nexora.service.EnderecoService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnderecoControllerTest {

    @Mock
    private EnderecoService enderecoService;

    @InjectMocks
    private EnderecoController enderecoController;

    public EnderecoControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCadastrarEnderecoComSucesso() {
        EnderecoDTO dto = new EnderecoDTO(null, "12345-678", "Rua A", 10, "Apto", "Bairro", "Cidade", "SP");
        Endereco endereco = new Endereco(dto);

        when(enderecoService.criarEndereco(dto)).thenReturn(endereco);

        ResponseEntity<Endereco> response = enderecoController.cadastrarEndereco(dto);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(endereco, response.getBody());
    }

    @Test
    void deveRetornarListaDeEnderecos() {
        Endereco endereco = new Endereco();
        when(enderecoService.buscarTodos()).thenReturn(List.of(endereco));

        ResponseEntity<List<Endereco>> response = enderecoController.buscarTodosEnderecos();

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void deveRetornarNoContentQuandoListaVazia() {
        when(enderecoService.buscarTodos()).thenReturn(List.of());

        ResponseEntity<List<Endereco>> response = enderecoController.buscarTodosEnderecos();

        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void deveBuscarEnderecoPorId() {
        Endereco endereco = new Endereco();
        when(enderecoService.buscarPorId(1L)).thenReturn(Optional.of(endereco));

        ResponseEntity<Endereco> response = enderecoController.buscarEndereco(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(endereco, response.getBody());
    }

    @Test
    void deveRetornarNotFoundAoBuscarEnderecoPorIdInexistente() {
        when(enderecoService.buscarPorId(99L)).thenReturn(Optional.empty());

        ResponseEntity<Endereco> response = enderecoController.buscarEndereco(99L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void deveBuscarEnderecosPorCep() {
        Endereco endereco = new Endereco();
        when(enderecoService.buscarPorCep("12345-678")).thenReturn(List.of(endereco));

        ResponseEntity<List<Endereco>> response = enderecoController.buscarPorCep("12345-678");

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void deveRetornarNotFoundAoBuscarPorCepSemResultados() {
        when(enderecoService.buscarPorCep("00000-000")).thenReturn(List.of());

        ResponseEntity<List<Endereco>> response = enderecoController.buscarPorCep("00000-000");

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void deveAtualizarEnderecoComSucesso() {
        EnderecoDTO dto = new EnderecoDTO(1L, "12345-678", "Rua A", 10, "Apto", "Bairro", "Cidade", "SP");
        Endereco enderecoAtualizado = new Endereco(dto);

        when(enderecoService.atualizarEndereco(1L, dto)).thenReturn(Optional.of(enderecoAtualizado));

        ResponseEntity<Endereco> response = enderecoController.atualizarEndereco(1L, dto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(enderecoAtualizado, response.getBody());
    }

    @Test
    void deveRetornarNotFoundAoAtualizarEnderecoInexistente() {
        EnderecoDTO dto = new EnderecoDTO(1L, "12345-678", "Rua A", 10, "Apto", "Bairro", "Cidade", "SP");

        when(enderecoService.atualizarEndereco(1L, dto)).thenReturn(Optional.empty());

        ResponseEntity<Endereco> response = enderecoController.atualizarEndereco(1L, dto);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void deveDeletarEnderecoComSucesso() {
        when(enderecoService.deletarEndereco(1L)).thenReturn(true);

        ResponseEntity<Void> response = enderecoController.deletarEndereco(1L);

        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void deveRetornarNotFoundAoDeletarEnderecoInexistente() {
        when(enderecoService.deletarEndereco(99L)).thenReturn(false);

        ResponseEntity<Void> response = enderecoController.deletarEndereco(99L);

        assertEquals(404, response.getStatusCodeValue());
    }
}