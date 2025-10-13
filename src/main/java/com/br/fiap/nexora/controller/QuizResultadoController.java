package com.br.fiap.nexora.controller;

import com.br.fiap.nexora.dto.QuizResultadoDTO;
import com.br.fiap.nexora.model.QuizResultado;
import com.br.fiap.nexora.service.QuizResultadoService;
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
    private QuizResultadoService quizService;

    @PostMapping
    public ResponseEntity<QuizResultado> cadastrarQuiz(@RequestBody @Valid QuizResultadoDTO dto) {
        QuizResultado quiz = quizService.criarQuiz(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(quiz);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizResultado> buscarQuiz(@PathVariable Long id) {
        return quizService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<QuizResultado>> buscarTodosQuizzes() {
        List<QuizResultado> quizzes = quizService.buscarTodos();
        if (quizzes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(quizzes);
    }

    @GetMapping("/cliente/{cpf}")
    public ResponseEntity<List<QuizResultado>> buscarQuizzesPorCliente(@PathVariable String cpf) {
        List<QuizResultado> quizzes = quizService.buscarPorCpfCliente(cpf);
        if (quizzes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(quizzes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuizResultado> atualizarQuiz(@PathVariable Long id, @RequestBody @Valid QuizResultadoDTO dto) {
        return quizService.atualizarQuiz(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarQuiz(@PathVariable Long id) {
        boolean deletado = quizService.deletarQuiz(id);
        if (!deletado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}