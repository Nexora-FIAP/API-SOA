package com.br.fiap.nexora.service;

import com.br.fiap.nexora.dto.EnderecoDTO;
import com.br.fiap.nexora.model.Endereco;
import com.br.fiap.nexora.repository.EnderecoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnderecoServiceTest {

    @Mock
    private EnderecoRepository enderecoRepository;

    @InjectMocks
    private EnderecoService enderecoService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCriarEndereco() {
        EnderecoDTO dto = new EnderecoDTO(null, "12345-678", "Rua A", 10, "Apto", "Bairro", "Cidade", "SP");
        Endereco endereco = new Endereco(dto);

        when(enderecoRepository.save(any())).thenReturn(endereco);

        Endereco resultado = enderecoService.criarEndereco(dto);

        assertEquals(endereco, resultado);
    }

    @Test
    void deveBuscarTodosEnderecos() {
        when(enderecoRepository.findAll()).thenReturn(List.of(new Endereco()));

        List<Endereco> resultado = enderecoService.buscarTodos();

        assertFalse(resultado.isEmpty());
    }

    @Test
    void deveBuscarEnderecoPorId() {
        Endereco endereco = new Endereco();
        when(enderecoRepository.findById(1L)).thenReturn(Optional.of(endereco));

        Optional<Endereco> resultado = enderecoService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
    }

    @Test
    void deveBuscarEnderecosPorCep() {
        when(enderecoRepository.findByCep("12345-678")).thenReturn(List.of(new Endereco()));

        List<Endereco> resultado = enderecoService.buscarPorCep("12345-678");

        assertFalse(resultado.isEmpty());
    }

    @Test
    void deveAtualizarEnderecoExistente() {
        EnderecoDTO dto = new EnderecoDTO(1L, "12345-678", "Rua A", 10, "Apto", "Bairro", "Cidade", "SP");
        Endereco endereco = new Endereco(dto);

        when(enderecoRepository.findById(1L)).thenReturn(Optional.of(endereco));
        when(enderecoRepository.save(any())).thenReturn(endereco);

        Optional<Endereco> resultado = enderecoService.atualizarEndereco(1L, dto);

        assertTrue(resultado.isPresent());
    }

    @Test
    void deveRetornarFalseAoDeletarEnderecoInexistente() {
        when(enderecoRepository.existsById(99L)).thenReturn(false);

        boolean resultado = enderecoService.deletarEndereco(99L);

        assertFalse(resultado);
    }

    @Test
    void deveDeletarEnderecoExistente() {
        when(enderecoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(enderecoRepository).deleteById(1L);

        boolean resultado = enderecoService.deletarEndereco(1L);

        assertTrue(resultado);
    }
}