package com.br.fiap.nexora.model;

import com.br.fiap.nexora.dto.NoticiaFinanceiraDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class NoticiaFinanceiraTest {

    private NoticiaFinanceiraDTO dto;

    @BeforeEach
    void setUp() {
        dto = new NoticiaFinanceiraDTO(
                null,
                "Mercado em alta",
                "As ações da bolsa subiram 3% nesta terça-feira.",
                "Valor Econômico",
                LocalDate.of(2025, 10, 14)
        );
    }

    @Test
    void deveConstruirNoticiaFinanceiraCorretamenteAPartirDoDTO() {
        NoticiaFinanceira noticia = new NoticiaFinanceira(dto);

        assertNull(noticia.getId());
        assertEquals("Mercado em alta", noticia.getTitulo());
        assertEquals("As ações da bolsa subiram 3% nesta terça-feira.", noticia.getConteudo());
        assertEquals("Valor Econômico", noticia.getFonte());
        assertEquals(LocalDate.of(2025, 10, 14), noticia.getDataPublicacao());
    }

    @Test
    void deveAtualizarNoticiaFinanceiraCorretamente() {
        NoticiaFinanceira noticia = new NoticiaFinanceira(dto);

        NoticiaFinanceiraDTO novoDto = new NoticiaFinanceiraDTO(
                null,
                "Inflação desacelera",
                "O índice de preços ao consumidor caiu 0,2% em setembro.",
                "Estadão",
                LocalDate.of(2025, 10, 13)
        );

        noticia.atualizar(novoDto);

        assertEquals("Inflação desacelera", noticia.getTitulo());
        assertEquals("O índice de preços ao consumidor caiu 0,2% em setembro.", noticia.getConteudo());
        assertEquals("Estadão", noticia.getFonte());
        assertEquals(LocalDate.of(2025, 10, 13), noticia.getDataPublicacao());
    }
}