package com.br.fiap.nexora.service;

import com.br.fiap.nexora.dto.InvestimentoDTO;
import com.br.fiap.nexora.enums.TipoInvestimento;
import com.br.fiap.nexora.model.Cliente;
import com.br.fiap.nexora.model.Investimento;
import com.br.fiap.nexora.repository.ClienteRepository;
import com.br.fiap.nexora.repository.InvestimentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InvestimentoServiceTest {

    @Mock
    private InvestimentoRepository investimentoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private InvestimentoService investimentoService;

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
    void deveCriarInvestimentoComSucesso() {
        when(clienteRepository.findById(dto.cpfCliente())).thenReturn(Optional.of(cliente));
        when(investimentoRepository.save(any())).thenReturn(investimento);

        Investimento resultado = investimentoService.criarInvestimento(dto);

        assertEquals(TipoInvestimento.TESOURO_SELIC, resultado.getTipo());
        assertEquals(5000.0f, resultado.getValorAplicado());
    }

    @Test
    void deveLancarExcecaoSeClienteNaoExistirAoCriarInvestimento() {
        when(clienteRepository.findById(dto.cpfCliente())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> investimentoService.criarInvestimento(dto));
    }

    @Test
    void deveAtualizarInvestimentoComSucesso() {
        when(investimentoRepository.findById(1L)).thenReturn(Optional.of(investimento));
        when(clienteRepository.findById(dto.cpfCliente())).thenReturn(Optional.of(cliente));
        when(investimentoRepository.save(any())).thenReturn(investimento);

        Optional<Investimento> resultado = investimentoService.atualizarInvestimento(1L, dto);

        assertTrue(resultado.isPresent());
        assertEquals(TipoInvestimento.TESOURO_SELIC, resultado.get().getTipo());
    }

    @Test
    void deveBuscarInvestimentoPorId() {
        when(investimentoRepository.findById(1L)).thenReturn(Optional.of(investimento));

        Optional<Investimento> resultado = investimentoService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
    }

    @Test
    void deveBuscarTodosInvestimentos() {
        when(investimentoRepository.findAll()).thenReturn(List.of(investimento));

        List<Investimento> resultado = investimentoService.buscarTodos();

        assertFalse(resultado.isEmpty());
    }

    @Test
    void deveBuscarInvestimentosPorCpfCliente() {
        when(investimentoRepository.findByClienteCpf("98765432100")).thenReturn(List.of(investimento));

        List<Investimento> resultado = investimentoService.buscarPorCpfCliente("98765432100");

        assertEquals(1, resultado.size());
    }

    @Test
    void deveDeletarInvestimentoComSucesso() {
        when(investimentoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(investimentoRepository).deleteById(1L);

        boolean resultado = investimentoService.deletarInvestimento(1L);

        assertTrue(resultado);
    }

    @Test
    void deveRetornarFalseAoDeletarInvestimentoInexistente() {
        when(investimentoRepository.existsById(99L)).thenReturn(false);

        boolean resultado = investimentoService.deletarInvestimento(99L);

        assertFalse(resultado);
    }
}