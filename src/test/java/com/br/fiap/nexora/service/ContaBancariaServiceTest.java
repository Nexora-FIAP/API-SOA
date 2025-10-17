package com.br.fiap.nexora.service;

import com.br.fiap.nexora.dto.ContaBancariaDTO;
import com.br.fiap.nexora.enums.TipoConta;
import com.br.fiap.nexora.model.Cliente;
import com.br.fiap.nexora.model.ContaBancaria;
import com.br.fiap.nexora.repository.ClienteRepository;
import com.br.fiap.nexora.repository.ContaBancariaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContaBancariaServiceTest {

    @Mock
    private ContaBancariaRepository contaRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ContaBancariaService contaService;

    private ContaBancariaDTO dto;
    private Cliente cliente;
    private ContaBancaria conta;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        cliente = new Cliente();
        cliente.setCpf("12345678900");

        dto = new ContaBancariaDTO(null, "Banco Nexora", 12345, "0001", TipoConta.CORRENTE, cliente.getCpf(), 1500.0f);
        conta = new ContaBancaria(dto, cliente);
    }

    @Test
    void deveCriarContaComSucesso() {
        when(clienteRepository.findById(dto.cpfCliente())).thenReturn(Optional.of(cliente));
        when(contaRepository.existsByNumeroContaAndClienteCpf(dto.numeroConta(), dto.cpfCliente())).thenReturn(false);
        when(contaRepository.save(any())).thenReturn(conta);

        ContaBancaria resultado = contaService.criarConta(dto);

        assertEquals("Banco Nexora", resultado.getBanco());
        assertEquals(1500.0f, resultado.getSaldoAtual());
    }

    @Test
    void deveLancarExcecaoSeClienteNaoExistirAoCriarConta() {
        when(clienteRepository.findById(dto.cpfCliente())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> contaService.criarConta(dto));
    }

    @Test
    void deveLancarExcecaoSeContaJaExistirParaCliente() {
        when(clienteRepository.findById(dto.cpfCliente())).thenReturn(Optional.of(cliente));
        when(contaRepository.existsByNumeroContaAndClienteCpf(dto.numeroConta(), dto.cpfCliente())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> contaService.criarConta(dto));
    }

    @Test
    void deveAtualizarContaComSucesso() {
        when(contaRepository.findById(1L)).thenReturn(Optional.of(conta));
        when(clienteRepository.findById(dto.cpfCliente())).thenReturn(Optional.of(cliente));
        when(contaRepository.save(any())).thenReturn(conta);

        Optional<ContaBancaria> resultado = contaService.atualizarConta(1L, dto);

        assertTrue(resultado.isPresent());
        assertEquals("Banco Nexora", resultado.get().getBanco());
    }

    @Test
    void deveBuscarContaPorId() {
        when(contaRepository.findById(1L)).thenReturn(Optional.of(conta));

        Optional<ContaBancaria> resultado = contaService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
    }

    @Test
    void deveBuscarTodasAsContas() {
        when(contaRepository.findAll()).thenReturn(List.of(conta));

        List<ContaBancaria> resultado = contaService.buscarTodas();

        assertFalse(resultado.isEmpty());
    }

    @Test
    void deveBuscarContasPorCpfCliente() {
        when(contaRepository.findByClienteCpf("12345678900")).thenReturn(List.of(conta));

        List<ContaBancaria> resultado = contaService.buscarPorCpfCliente("12345678900");

        assertEquals(1, resultado.size());
    }

    @Test
    void deveDeletarContaComSucesso() {
        when(contaRepository.existsById(1L)).thenReturn(true);
        doNothing().when(contaRepository).deleteById(1L);

        boolean resultado = contaService.deletarConta(1L);

        assertTrue(resultado);
    }

    @Test
    void deveRetornarFalseAoDeletarContaInexistente() {
        when(contaRepository.existsById(99L)).thenReturn(false);

        boolean resultado = contaService.deletarConta(99L);

        assertFalse(resultado);
    }
}