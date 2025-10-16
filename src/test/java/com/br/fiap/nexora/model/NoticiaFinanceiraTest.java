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
                "Alta do Dólar Impacta Mercado",
                "O dólar apresentou alta de 2% nesta segunda-feira.",
                "Valor Econômico",
                LocalDate.of(2024, 6, 10)
        );
    }

    @Test
    void deveCriarNoticiaFinanceiraAPartirDoDTO() {
        NoticiaFinanceira noticia = new NoticiaFinanceira(dto);

        assertEquals("Alta do Dólar Impacta Mercado", noticia.getTitulo());
        assertEquals("O dólar apresentou alta de 2% nesta segunda-feira.", noticia.getConteudo());
        assertEquals("Valor Econômico", noticia.getFonte());
        assertEquals(LocalDate.of(2024, 6, 10), noticia.getDataPublicacao());
    }

    @Test
    void deveAtualizarNoticiaFinanceiraComNovoDTO() {
        NoticiaFinanceira noticia = new NoticiaFinanceira(dto);

        NoticiaFinanceiraDTO novoDto = new NoticiaFinanceiraDTO(
                noticia.getId(),
                "Inflação Fica Abaixo do Esperado",
                "O índice de inflação ficou em 0,2% no mês de maio.",
                "Estadão",
                LocalDate.of(2024, 6, 12)
        );

        noticia.atualizar(novoDto);

        assertEquals("Inflação Fica Abaixo do Esperado", noticia.getTitulo());
        assertEquals("O índice de inflação ficou em 0,2% no mês de maio.", noticia.getConteudo());
        assertEquals("Estadão", noticia.getFonte());
        assertEquals(LocalDate.of(2024, 6, 12), noticia.getDataPublicacao());
    }

    @Test
    void equalsEHashCodeDevemUsarId() {
        NoticiaFinanceira n1 = new NoticiaFinanceira(dto);
        NoticiaFinanceira n2 = new NoticiaFinanceira(dto);

        n1.id = 1L;
        n2.id = 1L;

        assertEquals(n1, n2);
        assertEquals(n1.hashCode(), n2.hashCode());
    }

    @Test
    void naoDeveSerIgualQuandoIdForDiferente() {
        NoticiaFinanceira n1 = new NoticiaFinanceira(dto);
        NoticiaFinanceira n2 = new NoticiaFinanceira(dto);

        n1.id = 1L;
        n2.id = 2L;

        assertNotEquals(n1, n2);
    }
}