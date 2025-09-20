package com.br.fiap.nexora.controller;

import com.br.fiap.nexora.dto.QuizResultadoDTO;
import com.br.fiap.nexora.model.Cliente;
import com.br.fiap.nexora.model.QuizResultado;
import com.br.fiap.nexora.repository.ClienteRepository;
import com.br.fiap.nexora.repository.QuizResultadoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizResultadoController {

    @Autowired
    private QuizResultadoRepository quizRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    // POST - criar quiz
    @PostMapping
    @Transactional
    public ResponseEntity<QuizResultado> cadastrarQuiz(@RequestBody @Valid QuizResultadoDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.cpfCliente())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        QuizResultado quiz = new QuizResultado(dto, cliente);
        quizRepository.save(quiz);
        return ResponseEntity.status(HttpStatus.CREATED).body(quiz);
    }

    // GET por ID
    @GetMapping("/{id}")
    public ResponseEntity<QuizResultado> buscarQuiz(@PathVariable Long id) {
        return quizRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET todos os quizzes
    @GetMapping
    public ResponseEntity<List<QuizResultado>> buscarTodosQuizzes() {
        List<QuizResultado> quizzes = quizRepository.findAll();
        if (quizzes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(quizzes);
    }

    // GET quizzes de um cliente
    @GetMapping("/cliente/{cpf}")
    public ResponseEntity<List<QuizResultado>> buscarQuizzesPorCliente(@PathVariable String cpf) {
        List<QuizResultado> quizzes = quizRepository.findByClienteCpf(cpf);
        if (quizzes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(quizzes);
    }

    // PUT - atualizar quiz
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<QuizResultado> atualizarQuiz(@PathVariable Long id, @RequestBody @Valid QuizResultadoDTO dto) {
        return quizRepository.findById(id)
                .map(quiz -> {
                    Cliente cliente = clienteRepository.findById(dto.cpfCliente())
                            .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

                    quiz.atualizar(dto, cliente);
                    quizRepository.save(quiz);
                    return ResponseEntity.ok(quiz);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE - remover quiz
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletarQuiz(@PathVariable Long id) {
        if (!quizRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        quizRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
