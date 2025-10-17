package com.br.fiap.nexora.service;

import com.br.fiap.nexora.dto.NoticiaFinanceiraDTO;
import com.br.fiap.nexora.model.NoticiaFinanceira;
import com.br.fiap.nexora.repository.NoticiaFinanceiraRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NoticiaFinanceiraServiceTest {

    @Mock
    private NoticiaFinanceiraRepository noticiaRepository;

    @InjectMocks
    private NoticiaFinanceiraService noticiaService;

    private NoticiaFinanceiraDTO dto;
    private NoticiaFinanceira noticia;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        dto = new NoticiaFinanceiraDTO(
                null,
                "Alta do Dólar Impacta Mercado",
                "O dólar apresentou alta de 2% nesta segunda-feira.",
                "Valor Econômico",
                LocalDate.of(2024, 6, 10)
        );

        noticia = new NoticiaFinanceira(dto);
        noticia.setId(1L);
    }

    @Test
    void deveCriarNoticiaComSucesso() {
        when(noticiaRepository.save(any(NoticiaFinanceira.class))).thenReturn(noticia);

        NoticiaFinanceira criada = noticiaService.criarNoticia(dto);

        assertNotNull(criada);
        assertEquals("Alta do Dólar Impacta Mercado", criada.getTitulo());
        verify(noticiaRepository, times(1)).save(any(NoticiaFinanceira.class));
    }

    @Test
    void deveBuscarNoticiaPorIdExistente() {
        when(noticiaRepository.findById(1L)).thenReturn(Optional.of(noticia));

        Optional<NoticiaFinanceira> resultado = noticiaService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Valor Econômico", resultado.get().getFonte());
        verify(noticiaRepository, times(1)).findById(1L);
    }

    @Test
    void deveRetornarVazioQuandoNoticiaNaoExistir() {
        when(noticiaRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<NoticiaFinanceira> resultado = noticiaService.buscarPorId(999L);

        assertTrue(resultado.isEmpty());
        verify(noticiaRepository, times(1)).findById(999L);
    }

    @Test
    void deveRetornarTodasAsNoticias() {
        when(noticiaRepository.findAll()).thenReturn(List.of(noticia));

        List<NoticiaFinanceira> noticias = noticiaService.buscarTodas();

        assertEquals(1, noticias.size());
        verify(noticiaRepository, times(1)).findAll();
    }

    @Test
    void deveAtualizarNoticiaComSucesso() {
        NoticiaFinanceiraDTO novoDto = new NoticiaFinanceiraDTO(
                1L,
                "Inflação Fica Abaixo do Esperado",
                "O índice de inflação ficou em 0,2% no mês de maio.",
                "Estadão",
                LocalDate.of(2024, 6, 12)
        );

        when(noticiaRepository.findById(1L)).thenReturn(Optional.of(noticia));
        when(noticiaRepository.save(any(NoticiaFinanceira.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<NoticiaFinanceira> atualizada = noticiaService.atualizarNoticia(1L, novoDto);

        assertTrue(atualizada.isPresent());
        assertEquals("Inflação Fica Abaixo do Esperado", atualizada.get().getTitulo());
        assertEquals("Estadão", atualizada.get().getFonte());
        verify(noticiaRepository, times(1)).findById(1L);
        verify(noticiaRepository, times(1)).save(any(NoticiaFinanceira.class));
    }

    @Test
    void deveRetornarVazioAoAtualizarNoticiaInexistente() {
        when(noticiaRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<NoticiaFinanceira> resultado = noticiaService.atualizarNoticia(999L, dto);

        assertTrue(resultado.isEmpty());
        verify(noticiaRepository, times(1)).findById(999L);
        verify(noticiaRepository, never()).save(any());
    }

    @Test
    void deveDeletarNoticiaComSucesso() {
        when(noticiaRepository.existsById(1L)).thenReturn(true);

        boolean resultado = noticiaService.deletarNoticia(1L);

        assertTrue(resultado);
        verify(noticiaRepository, times(1)).existsById(1L);
        verify(noticiaRepository, times(1)).deleteById(1L);
    }

    @Test
    void naoDeveDeletarQuandoNoticiaNaoExistir() {
        when(noticiaRepository.existsById(999L)).thenReturn(false);

        boolean resultado = noticiaService.deletarNoticia(999L);

        assertFalse(resultado);
        verify(noticiaRepository, times(1)).existsById(999L);
        verify(noticiaRepository, never()).deleteById(anyLong());
    }
}
