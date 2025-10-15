package com.br.fiap.nexora.model;

import com.br.fiap.nexora.dto.ClienteDTO;
import com.br.fiap.nexora.dto.EnderecoDTO;
import com.br.fiap.nexora.enums.PerfilInvestidor;
import com.br.fiap.nexora.repository.EnderecoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    private ClienteDTO dto;
    private EnderecoDTO enderecoDTO;

    @BeforeEach
    void setUp() {
        enderecoDTO = new EnderecoDTO(
                null,
                "06543-000",
                "Rua das Flores",
                "Santana de Parnaíba",
                "SP",
                "Brasil"
        );

        dto = new ClienteDTO(
                "12345678900",
                "Leticia",
                "leticia@email.com",
                LocalDate.of(1995, 5, 20),
                PerfilInvestidor.MODERADO,
                "11999999999",
                enderecoDTO,
                "senhaSegura123"
        );
    }

    @Test
    void deveConstruirClienteCorretamenteAPartirDoDTO() {
        Cliente cliente = new Cliente(dto);

        assertEquals("12345678900", cliente.getCpf());
        assertEquals("Leticia", cliente.getNome());
        assertEquals("leticia@email.com", cliente.getEmail());
        assertEquals(LocalDate.of(1995, 5, 20), cliente.getDataNascimento());
        assertEquals(PerfilInvestidor.MODERADO, cliente.getPerfilInvestidor());
        assertEquals("11999999999", cliente.getTelefone());
        assertNotNull(cliente.getEndereco());
        assertEquals("senhaSegura123", cliente.getSenha());
        assertNotNull(cliente.getCriadoEm());
    }

    @Test
    void deveAtualizarClienteComNovoEndereco() {
        Cliente cliente = new Cliente(dto);

        EnderecoRepository enderecoRepository = Mockito.mock(EnderecoRepository.class);

        EnderecoDTO novoEnderecoDTO = new EnderecoDTO(
                null,
                "06544-111",
                "Rua Nova",
                "Barueri",
                "SP",
                "Brasil"
        );

        ClienteDTO novoDto = new ClienteDTO(
                "12345678900",
                "Leticia Atualizada",
                "nova@email.com",
                LocalDate.of(1995, 5, 20),
                PerfilInvestidor.AGRESSIVO,
                "11988888888",
                novoEnderecoDTO,
                "novaSenha123"
        );

        cliente.atualizar(novoDto, enderecoRepository);

        assertEquals("Leticia Atualizada", cliente.getNome());
        assertEquals("nova@email.com", cliente.getEmail());
        assertEquals("11988888888", cliente.getTelefone());
        assertEquals(PerfilInvestidor.AGRESSIVO, cliente.getPerfilInvestidor());
        assertEquals("novaSenha123", cliente.getSenha());
        assertNotNull(cliente.getEndereco());
        assertEquals("Rua Nova", cliente.getEndereco().getLogradouro());
    }

    @Test
    void deveAtualizarClienteComEnderecoExistente() {
        Cliente cliente = new Cliente(dto);

        Endereco enderecoExistente = new Endereco(enderecoDTO);
        enderecoExistente.setId(1L);

        EnderecoDTO dtoComId = new EnderecoDTO(
                1L,
                "06543-000",
                "Rua das Flores",
                "Santana de Parnaíba",
                "SP",
                "Brasil"
        );

        ClienteDTO dtoAtualizado = new ClienteDTO(
                "12345678900",
                "Leticia",
                "atualizado@email.com",
                LocalDate.of(1995, 5, 20),
                PerfilInvestidor.CONSERVADOR,
                "11977777777",
                dtoComId,
                "senhaNova"
        );

        EnderecoRepository enderecoRepository = Mockito.mock(EnderecoRepository.class);
        Mockito.when(enderecoRepository.findById(1L)).thenReturn(java.util.Optional.of(enderecoExistente));

        cliente.atualizar(dtoAtualizado, enderecoRepository);

        assertEquals("atualizado@email.com", cliente.getEmail());
        assertEquals("11977777777", cliente.getTelefone());
        assertEquals(PerfilInvestidor.CONSERVADOR, cliente.getPerfilInvestidor());
        assertEquals(enderecoExistente, cliente.getEndereco());
    }
}