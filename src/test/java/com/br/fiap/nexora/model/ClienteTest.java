package com.br.fiap.nexora.model;

import com.br.fiap.nexora.dto.ClienteDTO;
import com.br.fiap.nexora.dto.EnderecoDTO;
import com.br.fiap.nexora.enums.PerfilInvestidor;
import com.br.fiap.nexora.repository.EnderecoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ClienteTest {

    private EnderecoRepository enderecoRepository;

    @BeforeEach
    void setup() {
        enderecoRepository = Mockito.mock(EnderecoRepository.class);
    }

    @Test
    void deveCriarClienteAPartirDoDTO() {
        EnderecoDTO enderecoDTO = new EnderecoDTO(
                1L, "12345-678", "Rua Teste", 100,
                "Apto 10", "Centro", "São Paulo", "SP"
        );

        ClienteDTO dto = new ClienteDTO(
                "12345678900",
                "Leticia Resina",
                "lety@email.com",
                LocalDate.of(2004, 10, 15),
                PerfilInvestidor.MODERADO,
                "11999999999",
                enderecoDTO,
                "senha123"
        );

        Cliente cliente = new Cliente(dto);

        assertEquals(dto.cpf(), cliente.getCpf());
        assertEquals(dto.nome(), cliente.getNome());
        assertEquals(dto.email(), cliente.getEmail());
        assertEquals(dto.dataNascimento(), cliente.getDataNascimento());
        assertEquals(dto.perfilInvestidor(), cliente.getPerfilInvestidor());
        assertEquals(dto.telefone(), cliente.getTelefone());
        assertNotNull(cliente.getEndereco());
        assertEquals(dto.endereco().cep(), cliente.getEndereco().getCep());
        assertEquals(dto.senha(), cliente.getSenha());
        assertNotNull(cliente.getCriadoEm());
    }

    @Test
    void deveAtualizarClienteComNovoDTOEEnderecoExistente() {
        Endereco enderecoExistente = new Endereco(
                1L, "99999-999", "Rua Velha", 50,
                "Casa", "Centro", "Rio de Janeiro", "RJ"
        );

        when(enderecoRepository.findById(1L)).thenReturn(Optional.of(enderecoExistente));

        EnderecoDTO dtoNovoEndereco = new EnderecoDTO(
                1L, "12345-678", "Rua Nova", 100,
                "Apto 10", "Bairro Novo", "São Paulo", "SP"
        );

        ClienteDTO dtoNovo = new ClienteDTO(
                "12345678900",
                "Novo Nome",
                "novo@email.com",
                LocalDate.of(2000, 1, 1),
                PerfilInvestidor.ARROJADO,
                "11988888888",
                dtoNovoEndereco,
                "novaSenha"
        );

        Cliente cliente = new Cliente();
        cliente.atualizar(dtoNovo, enderecoRepository);

        assertEquals(dtoNovo.nome(), cliente.getNome());
        assertEquals(dtoNovo.email(), cliente.getEmail());
        assertEquals(dtoNovo.telefone(), cliente.getTelefone());
        assertEquals(dtoNovo.perfilInvestidor(), cliente.getPerfilInvestidor());
        assertEquals(dtoNovo.dataNascimento(), cliente.getDataNascimento());
        assertEquals(enderecoExistente, cliente.getEndereco());
    }

    @Test
    void deveExecutarPreUpdateEAtribuirAtualizadoEm() {
        Cliente cliente = new Cliente();
        assertNull(cliente.getAtualizadoEm());

        cliente.preUpdate();

        assertNotNull(cliente.getAtualizadoEm());
        assertTrue(cliente.getAtualizadoEm().isBefore(LocalDateTime.now().plusSeconds(1)));
    }
}
