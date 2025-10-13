package com.br.fiap.nexora.service;

import com.br.fiap.nexora.dto.QuizResultadoDTO;
import com.br.fiap.nexora.model.Cliente;
import com.br.fiap.nexora.model.QuizResultado;
import com.br.fiap.nexora.repository.ClienteRepository;
import com.br.fiap.nexora.repository.QuizResultadoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizResultadoService {

    @Autowired
    private QuizResultadoRepository quizRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public QuizResultado criarQuiz(QuizResultadoDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.cpfCliente())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        QuizResultado quiz = new QuizResultado(dto, cliente);
        return quizRepository.save(quiz);
    }

    public Optional<QuizResultado> buscarPorId(Long id) {
        return quizRepository.findById(id);
    }

    public List<QuizResultado> buscarTodos() {
        return quizRepository.findAll();
    }

    public List<QuizResultado> buscarPorCpfCliente(String cpf) {
        return quizRepository.findByClienteCpf(cpf);
    }

    @Transactional
    public Optional<QuizResultado> atualizarQuiz(Long id, QuizResultadoDTO dto) {
        return quizRepository.findById(id).map(quiz -> {
            Cliente cliente = clienteRepository.findById(dto.cpfCliente())
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

            quiz.atualizar(dto, cliente);
            return quizRepository.save(quiz);
        });
    }

    @Transactional
    public boolean deletarQuiz(Long id) {
        if (!quizRepository.existsById(id)) {
            return false;
        }
        quizRepository.deleteById(id);
        return true;
    }
}