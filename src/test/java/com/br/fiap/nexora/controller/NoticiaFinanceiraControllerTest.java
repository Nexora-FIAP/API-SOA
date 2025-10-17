package com.br.fiap.nexora.controller;

import com.br.fiap.nexora.dto.NoticiaFinanceiraDTO;
import com.br.fiap.nexora.model.NoticiaFinanceira;
import com.br.fiap.nexora.service.NoticiaFinanceiraService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NoticiaFinanceiraControllerTest {

    @Mock
    private NoticiaFinanceiraService noticiaService;

    @InjectMocks
    private NoticiaFinanceiraController noticiaController;

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
    void deveCadastrarNoticiaComSucesso() {
        when(noticiaService.criarNoticia(dto)).thenReturn(noticia);

        ResponseEntity<NoticiaFinanceira> response = noticiaController.cadastrarNoticia(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(noticia, response.getBody());
        verify(noticiaService, times(1)).criarNoticia(dto);
    }

    @Test
    void deveBuscarNoticiaPorIdComSucesso() {
        when(noticiaService.buscarPorId(1L)).thenReturn(Optional.of(noticia));

        ResponseEntity<NoticiaFinanceira> response = noticiaController.buscarNoticia(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(noticia, response.getBody());
        verify(noticiaService, times(1)).buscarPorId(1L);
    }

    @Test
    void deveRetornarNotFoundAoBuscarNoticiaInexistente() {
        when(noticiaService.buscarPorId(999L)).thenReturn(Optional.empty());

        ResponseEntity<NoticiaFinanceira> response = noticiaController.buscarNoticia(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(noticiaService, times(1)).buscarPorId(999L);
    }

    @Test
    void deveListarTodasAsNoticiasComSucesso() {
        when(noticiaService.buscarTodas()).thenReturn(List.of(noticia));

        ResponseEntity<List<NoticiaFinanceira>> response = noticiaController.buscarTodasNoticias();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(noticiaService, times(1)).buscarTodas();
    }

    @Test
    void deveRetornarNoContentQuandoNaoHouverNoticias() {
        when(noticiaService.buscarTodas()).thenReturn(List.of());

        ResponseEntity<List<NoticiaFinanceira>> response = noticiaController.buscarTodasNoticias();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(noticiaService, times(1)).buscarTodas();
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

        NoticiaFinanceira noticiaAtualizada = new NoticiaFinanceira(novoDto);
        noticiaAtualizada.setId(1L);

        when(noticiaService.atualizarNoticia(1L, novoDto)).thenReturn(Optional.of(noticiaAtualizada));

        ResponseEntity<NoticiaFinanceira> response = noticiaController.atualizarNoticia(1L, novoDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(noticiaAtualizada, response.getBody());
        verify(noticiaService, times(1)).atualizarNoticia(1L, novoDto);
    }

    @Test
    void deveRetornarNotFoundAoAtualizarNoticiaInexistente() {
        when(noticiaService.atualizarNoticia(eq(999L), any(NoticiaFinanceiraDTO.class)))
                .thenReturn(Optional.empty());

        ResponseEntity<NoticiaFinanceira> response = noticiaController.atualizarNoticia(999L, dto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(noticiaService, times(1)).atualizarNoticia(eq(999L), any(NoticiaFinanceiraDTO.class));
    }

    @Test
    void deveDeletarNoticiaComSucesso() {
        when(noticiaService.deletarNoticia(1L)).thenReturn(true);

        ResponseEntity<Void> response = noticiaController.deletarNoticia(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(noticiaService, times(1)).deletarNoticia(1L);
    }

    @Test
    void deveRetornarNotFoundAoDeletarNoticiaInexistente() {
        when(noticiaService.deletarNoticia(999L)).thenReturn(false);

        ResponseEntity<Void> response = noticiaController.deletarNoticia(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(noticiaService, times(1)).deletarNoticia(999L);
    }
}