package com.br.fiap.nexora.service;

import com.br.fiap.nexora.dto.QuizResultadoDTO;
import com.br.fiap.nexora.enums.TipoQuiz;
import com.br.fiap.nexora.model.Cliente;
import com.br.fiap.nexora.model.QuizResultado;
import com.br.fiap.nexora.repository.ClienteRepository;
import com.br.fiap.nexora.repository.QuizResultadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuizResultadoServiceTest {

    @Mock
    private QuizResultadoRepository quizRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private QuizResultadoService quizService;

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
    void deveCriarQuizComSucesso() {
        when(clienteRepository.findById(dto.cpfCliente())).thenReturn(Optional.of(cliente));
        when(quizRepository.save(any())).thenReturn(quiz);

        QuizResultado resultado = quizService.criarQuiz(dto);

        assertEquals("Perfil Moderado", resultado.getResultado());
        assertEquals(cliente, resultado.getCliente());
    }

    @Test
    void deveLancarExcecaoSeClienteNaoExistirAoCriarQuiz() {
        when(clienteRepository.findById(dto.cpfCliente())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> quizService.criarQuiz(dto));
    }

    @Test
    void deveBuscarQuizPorId() {
        when(quizRepository.findById(1L)).thenReturn(Optional.of(quiz));

        Optional<QuizResultado> resultado = quizService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(quiz, resultado.get());
    }

    @Test
    void deveBuscarTodosQuizzes() {
        when(quizRepository.findAll()).thenReturn(List.of(quiz));

        List<QuizResultado> resultado = quizService.buscarTodos();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
    }

    @Test
    void deveBuscarQuizzesPorCpfCliente() {
        when(quizRepository.findByClienteCpf("12345678900")).thenReturn(List.of(quiz));

        List<QuizResultado> resultado = quizService.buscarPorCpfCliente("12345678900");

        assertEquals(1, resultado.size());
        assertEquals("Perfil Moderado", resultado.get(0).getResultado());
    }

    @Test
    void deveAtualizarQuizComSucesso() {
        when(quizRepository.findById(1L)).thenReturn(Optional.of(quiz));
        when(clienteRepository.findById(dto.cpfCliente())).thenReturn(Optional.of(cliente));
        when(quizRepository.save(any())).thenReturn(quiz);

        Optional<QuizResultado> resultado = quizService.atualizarQuiz(1L, dto);

        assertTrue(resultado.isPresent());
        assertEquals("Perfil Moderado", resultado.get().getResultado());
    }

    @Test
    void deveDeletarQuizComSucesso() {
        when(quizRepository.existsById(1L)).thenReturn(true);
        doNothing().when(quizRepository).deleteById(1L);

        boolean resultado = quizService.deletarQuiz(1L);

        assertTrue(resultado);
    }

    @Test
    void deveRetornarFalseAoDeletarQuizInexistente() {
        when(quizRepository.existsById(99L)).thenReturn(false);

        boolean resultado = quizService.deletarQuiz(99L);

        assertFalse(resultado);
    }
}