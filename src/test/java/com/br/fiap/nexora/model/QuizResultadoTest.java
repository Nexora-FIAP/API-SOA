package com.br.fiap.nexora.model;

import com.br.fiap.nexora.dto.QuizResultadoDTO;
import com.br.fiap.nexora.enums.TipoQuiz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class QuizResultadoTest {

    private Cliente cliente;
    private QuizResultadoDTO dto;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setCpf("12345678900");
        cliente.setNome("Lety");

        dto = new QuizResultadoDTO(
                null,
                TipoQuiz.INVESTIDOR,
                "Perfil Moderado",
                LocalDate.of(2024, 6, 5),
                cliente.getCpf()
        );
    }

    @Test
    void deveCriarQuizResultadoAPartirDoDTO() {
        QuizResultado quiz = new QuizResultado(dto, cliente);

        assertEquals(TipoQuiz.INVESTIDOR, quiz.getTipo());
        assertEquals("Perfil Moderado", quiz.getResultado());
        assertEquals(LocalDate.of(2024, 6, 5), quiz.getDataFeita());
        assertEquals(cliente, quiz.getCliente());
    }

    @Test
    void deveAtualizarQuizResultadoComNovoDTO() {
        QuizResultado quiz = new QuizResultado(dto, cliente);

        QuizResultadoDTO novoDto = new QuizResultadoDTO(
                quiz.getId(),
                TipoQuiz.INVESTIMENTO,
                "Perfil Arrojado",
                LocalDate.of(2024, 6, 10),
                cliente.getCpf()
        );

        quiz.atualizar(novoDto, cliente);

        assertEquals(TipoQuiz.INVESTIMENTO, quiz.getTipo());
        assertEquals("Perfil Arrojado", quiz.getResultado());
        assertEquals(LocalDate.of(2024, 6, 10), quiz.getDataFeita());
        assertEquals(cliente, quiz.getCliente());
    }

    @Test
    void equalsEHashCodeDevemUsarId() {
        QuizResultado q1 = new QuizResultado(dto, cliente);
        QuizResultado q2 = new QuizResultado(dto, cliente);

        q1.id = 1L;
        q2.id = 1L;

        assertEquals(q1, q2);
        assertEquals(q1.hashCode(), q2.hashCode());
    }

    @Test
    void naoDeveSerIgualQuandoIdForDiferente() {
        QuizResultado q1 = new QuizResultado(dto, cliente);
        QuizResultado q2 = new QuizResultado(dto, cliente);

        q1.id = 1L;
        q2.id = 2L;

        assertNotEquals(q1, q2);
    }
}