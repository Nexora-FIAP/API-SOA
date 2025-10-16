package com.br.fiap.nexora.service;

import com.br.fiap.nexora.dto.EnderecoDTO;
import com.br.fiap.nexora.model.Endereco;
import com.br.fiap.nexora.repository.EnderecoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnderecoServiceTest {

    private EnderecoRepository enderecoRepository;
    private EnderecoService enderecoService;

    @BeforeEach
    void setUp() {
        enderecoRepository = mock(EnderecoRepository.class);
        enderecoService = new EnderecoService();
        ReflectionTestUtils.setField(enderecoService, "enderecoRepository", enderecoRepository);
    }

    @Test
    @DisplayName("Deve criar um novo endereço")
    void deveCriarEndereco() {
        EnderecoDTO dto = new EnderecoDTO(
                null, "12345-678", "Rua Teste", 100,
                "Apto 10", "Centro", "São Paulo", "SP"
        );

        Endereco enderecoMock = new Endereco(dto);
        enderecoMock.setId(1L);

        when(enderecoRepository.save(any(Endereco.class))).thenReturn(enderecoMock);

        Endereco resultado = enderecoService.criarEndereco(dto);

        assertEquals("12345-678", resultado.getCep());
        assertEquals("Rua Teste", resultado.getRua());
        verify(enderecoRepository).save(any(Endereco.class));
    }

    @Test
    @DisplayName("Deve buscar todos os endereços")
    void deveBuscarTodosEnderecos() {
        when(enderecoRepository.findAll()).thenReturn(List.of(new Endereco(), new Endereco()));

        List<Endereco> resultado = enderecoService.buscarTodos();

        assertEquals(2, resultado.size());
        verify(enderecoRepository).findAll();
    }

    @Test
    @DisplayName("Deve buscar endereço por ID")
    void deveBuscarEnderecoPorId() {
        Endereco endereco = new Endereco();
        endereco.setId(1L);

        when(enderecoRepository.findById(1L)).thenReturn(Optional.of(endereco));

        Optional<Endereco> resultado = enderecoService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
    }

    @Test
    @DisplayName("Deve buscar endereço por CEP")
    void deveBuscarEnderecoPorCep() {
        when(enderecoRepository.findByCep("12345-678")).thenReturn(List.of(new Endereco()));

        List<Endereco> resultado = enderecoService.buscarPorCep("12345-678");

        assertEquals(1, resultado.size());
        verify(enderecoRepository).findByCep("12345-678");
    }

    @Test
    @DisplayName("Deve atualizar endereço existente")
    void deveAtualizarEndereco() {
        Endereco enderecoExistente = new Endereco();
        enderecoExistente.setId(1L);
        enderecoExistente.setCep("00000-000");

        EnderecoDTO dto = new EnderecoDTO(
                null, "99999-999", "Rua Nova", 200,
                "Casa", "Bairro Novo", "Rio", "RJ"
        );

        when(enderecoRepository.findById(1L)).thenReturn(Optional.of(enderecoExistente));
        when(enderecoRepository.save(any(Endereco.class))).thenReturn(enderecoExistente);

        Optional<Endereco> resultado = enderecoService.atualizarEndereco(1L, dto);

        assertTrue(resultado.isPresent());
        assertEquals("99999-999", resultado.get().getCep());
    }

    @Test
    @DisplayName("Deve deletar endereço existente")
    void deveDeletarEndereco() {
        when(enderecoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(enderecoRepository).deleteById(1L);

        boolean resultado = enderecoService.deletarEndereco(1L);

        assertTrue(resultado);
        verify(enderecoRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Não deve deletar endereço inexistente")
    void naoDeveDeletarEnderecoInexistente() {
        when(enderecoRepository.existsById(99L)).thenReturn(false);

        boolean resultado = enderecoService.deletarEndereco(99L);

        assertFalse(resultado);
        verify(enderecoRepository, never()).deleteById(anyLong());
    }
}