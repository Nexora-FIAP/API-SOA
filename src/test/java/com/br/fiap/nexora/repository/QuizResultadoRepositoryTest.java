package com.br.fiap.nexora.repository;

import com.br.fiap.nexora.enums.TipoQuiz;
import com.br.fiap.nexora.model.Cliente;
import com.br.fiap.nexora.model.QuizResultado;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class QuizResultadoRepositoryTest {

    @Autowired
    private QuizResultadoRepository quizRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void deveSalvarERecuperarQuizPorCpf() {
        Cliente cliente = new Cliente();
        cliente.setCpf("12345678900");
        cliente.setNome("Lety");
        cliente.setEmail("lety@email.com");
        cliente.setDataNascimento(LocalDate.of(2000, 1, 1));
        cliente.setSenha("senha123");

        clienteRepository.save(cliente);

        QuizResultado quiz = new QuizResultado();
        quiz.setTipo(TipoQuiz.INVESTIDOR);
        quiz.setResultado("Perfil Moderado");
        quiz.setDataFeita(LocalDate.of(2024, 6, 5));
        quiz.setCliente(cliente);

        quizRepository.save(quiz);

        List<QuizResultado> encontrados = quizRepository.findByClienteCpf("12345678900");

        assertFalse(encontrados.isEmpty());
        assertEquals("Perfil Moderado", encontrados.get(0).getResultado());
    }
}