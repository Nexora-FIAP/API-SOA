package com.br.fiap.nexora.service;

import com.br.fiap.nexora.dto.ClienteDTO;
import com.br.fiap.nexora.dto.EnderecoDTO;
import com.br.fiap.nexora.enums.PerfilInvestidor;
import com.br.fiap.nexora.model.Cliente;
import com.br.fiap.nexora.model.Endereco;
import com.br.fiap.nexora.repository.ClienteRepository;
import com.br.fiap.nexora.repository.EnderecoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ClienteService clienteService;

    private ClienteDTO clienteDTO;
    private Cliente cliente;
    private Endereco endereco;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        EnderecoDTO enderecoDTO = new EnderecoDTO(
                1L, "12345-678", "Rua A", 10, "Apto", "Centro", "São Paulo", "SP"
        );

        clienteDTO = new ClienteDTO(
                "12345678900", "Leticia", "lety@email.com",
                LocalDate.of(2004, 10, 15), PerfilInvestidor.MODERADO,
                "11999999999", enderecoDTO, "senha123"
        );

        endereco = new Endereco(enderecoDTO);
        cliente = new Cliente(clienteDTO);
        cliente.setEndereco(endereco);
    }

    @Test
    void deveCriarClienteComSenhaCriptografada() {
        when(passwordEncoder.encode("senha123")).thenReturn("criptografada");

        Cliente clienteComSenhaCriptografada = new Cliente(clienteDTO);
        clienteComSenhaCriptografada.setSenha("criptografada");

        when(clienteRepository.save(any())).thenReturn(clienteComSenhaCriptografada);

        Cliente resultado = clienteService.criarCliente(clienteDTO);

        assertEquals("criptografada", resultado.getSenha());
    }

    @Test
    void deveAtualizarClienteExistente() {
        when(clienteRepository.findById("12345678900")).thenReturn(Optional.of(cliente));
        when(enderecoRepository.findById(1L)).thenReturn(Optional.of(endereco));
        when(clienteRepository.save(any())).thenReturn(cliente);

        Cliente resultado = clienteService.atualizarCliente("12345678900", clienteDTO);

        assertEquals(cliente, resultado);
    }

    @Test
    void deveLancarExcecaoAoAtualizarClienteInexistente() {
        when(clienteRepository.findById("00000000000")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> clienteService.atualizarCliente("00000000000", clienteDTO));
    }

    @Test
    void deveBuscarClientePorCpf() {
        // Cria um cliente com nome correto
        Cliente clienteComNome = new Cliente();
        clienteComNome.setCpf("12345678900");
        clienteComNome.setNome("Leticia");

        when(clienteRepository.findById("12345678900")).thenReturn(Optional.of(clienteComNome));

        Optional<Cliente> resultado = clienteService.buscarPorCpf("12345678900");

        assertTrue(resultado.isPresent());
        assertEquals("Leticia", resultado.get().getNome());
    }


    @Test
    void deveBuscarTodosClientes() {
        when(clienteRepository.findAll()).thenReturn(List.of(cliente));

        List<Cliente> resultado = clienteService.buscarTodos();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
    }

    @Test
    void deveDeletarCliente() {
        doNothing().when(clienteRepository).deleteById("12345678900");

        clienteService.deletarCliente("12345678900");

        verify(clienteRepository, times(1)).deleteById("12345678900");
    }
}