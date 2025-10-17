package com.br.fiap.nexora.controller;

import com.br.fiap.nexora.dto.QuizResultadoDTO;
import com.br.fiap.nexora.enums.TipoQuiz;
import com.br.fiap.nexora.model.Cliente;
import com.br.fiap.nexora.model.QuizResultado;
import com.br.fiap.nexora.service.QuizResultadoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuizResultadoControllerTest {

    @Mock
    private QuizResultadoService quizService;

    @InjectMocks
    private QuizResultadoController quizController;

    private Cliente cliente;
    private QuizResultadoDTO dto;
    private QuizResultado quiz;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        cliente = new Cliente();
        cliente.setCpf("12345678900");
        cliente.setNome("Lety");

        dto = new QuizResultadoDTO(null, TipoQuiz.INVESTIDOR, "Perfil Moderado",
                LocalDate.of(2024, 6, 5), cliente.getCpf());

        quiz = new QuizResultado(dto, cliente);
        quiz.setId(1L);
    }

    @Test
    void deveCadastrarQuizComSucesso() {
        when(quizService.criarQuiz(dto)).thenReturn(quiz);
        var response = quizController.cadastrarQuiz(dto);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(quiz, response.getBody());
    }

    @Test
    void deveBuscarQuizPorId() {
        when(quizService.buscarPorId(1L)).thenReturn(Optional.of(quiz));
        var response = quizController.buscarQuiz(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(quiz, response.getBody());
    }

    @Test
    void deveRetornarNotFoundAoBuscarQuizInexistente() {
        when(quizService.buscarPorId(99L)).thenReturn(Optional.empty());
        var response = quizController.buscarQuiz(99L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void deveListarTodosQuizzes() {
        when(quizService.buscarTodos()).thenReturn(List.of(quiz));
        var response = quizController.buscarTodosQuizzes();
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void deveRetornarNoContentSeNaoHouverQuizzes() {
        when(quizService.buscarTodos()).thenReturn(List.of());
        var response = quizController.buscarTodosQuizzes();
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void deveBuscarQuizzesPorCpfCliente() {
        when(quizService.buscarPorCpfCliente("12345678900")).thenReturn(List.of(quiz));
        var response = quizController.buscarQuizzesPorCliente("12345678900");
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void deveRetornarNoContentAoBuscarPorCpfSemResultados() {
        when(quizService.buscarPorCpfCliente("00000000000")).thenReturn(List.of());
        var response = quizController.buscarQuizzesPorCliente("00000000000");
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void deveAtualizarQuizComSucesso() {
        when(quizService.atualizarQuiz(1L, dto)).thenReturn(Optional.of(quiz));
        var response = quizController.atualizarQuiz(1L, dto);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(quiz, response.getBody());
    }

    @Test
    void deveRetornarNotFoundAoAtualizarQuizInexistente() {
        when(quizService.atualizarQuiz(99L, dto)).thenReturn(Optional.empty());
        var response = quizController.atualizarQuiz(99L, dto);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void deveDeletarQuizComSucesso() {
        when(quizService.deletarQuiz(1L)).thenReturn(true);
        var response = quizController.deletarQuiz(1L);
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void deveRetornarNotFoundAoDeletarQuizInexistente() {
        when(quizService.deletarQuiz(99L)).thenReturn(false);
        var response = quizController.deletarQuiz(99L);
        assertEquals(404, response.getStatusCodeValue());
    }
}