package com.br.fiap.nexora.model;

import com.br.fiap.nexora.dto.QuizResultadoDTO;
import com.br.fiap.nexora.enums.TipoQuiz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class QuizResultadoTest {

    private QuizResultadoDTO dto;
    private Cliente clienteMock;

    @BeforeEach
    void setUp() {
        dto = new QuizResultadoDTO(
                null,
                TipoQuiz.PERFIL_INVESTIDOR,
                "Moderado",
                LocalDate.of(2025, 10, 10),
                "12345678900"
        );

        clienteMock = Mockito.mock(Cliente.class);
    }

    @Test
    void deveConstruirQuizResultadoCorretamenteAPartirDoDTO() {
        QuizResultado quiz = new QuizResultado(dto, clienteMock);

        assertNull(quiz.getId());
        assertEquals(TipoQuiz.PERFIL_INVESTIDOR, quiz.getTipo());
        assertEquals("Moderado", quiz.getResultado());
        assertEquals(LocalDate.of(2025, 10, 10), quiz.getDataFeita());
        assertSame(clienteMock, quiz.getCliente());
    }

    @Test
    void deveAtualizarQuizResultadoCorretamente() {
        QuizResultado quiz = new QuizResultado(dto, clienteMock);

        QuizResultadoDTO novoDto = new QuizResultadoDTO(
                null,
                TipoQuiz.CONHECIMENTO_FINANCEIRO,
                "Avançado",
                LocalDate.of(2025, 10, 12),
                "12345678900"
        );

        Cliente novoClienteMock = Mockito.mock(Cliente.class);
        quiz.atualizar(novoDto, novoClienteMock);

        assertEquals(TipoQuiz.CONHECIMENTO_FINANCEIRO, quiz.getTipo());
        assertEquals("Avançado", quiz.getResultado());
        assertEquals(LocalDate.of(2025, 10, 12), quiz.getDataFeita());
        assertSame(novoClienteMock, quiz.getCliente());
    }
}