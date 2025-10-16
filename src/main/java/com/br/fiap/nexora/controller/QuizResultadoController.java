package com.br.fiap.nexora.controller;

import com.br.fiap.nexora.dto.QuizResultadoDTO;
import com.br.fiap.nexora.model.QuizResultado;
import com.br.fiap.nexora.service.QuizResultadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Cadastra um novo resultado de quiz", description = "Registra o resultado de um quiz respondido por um cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Resultado cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<QuizResultado> cadastrarQuiz(@RequestBody @Valid QuizResultadoDTO dto) {
        QuizResultado quiz = quizService.criarQuiz(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(quiz);
    }

    @Operation(summary = "Busca resultado de quiz por ID", description = "Retorna os dados de um resultado de quiz específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Resultado não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<QuizResultado> buscarQuiz(@PathVariable Long id) {
        return quizService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Lista todos os resultados de quiz", description = "Retorna todos os resultados de quiz cadastrados no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de resultados retornada com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum resultado cadastrado")
    })
    @GetMapping
    public ResponseEntity<List<QuizResultado>> buscarTodosQuizzes() {
        List<QuizResultado> quizzes = quizService.buscarTodos();
        if (quizzes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(quizzes);
    }

    @Operation(summary = "Busca resultados de quiz por CPF do cliente", description = "Retorna todos os resultados de quiz vinculados ao CPF informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultados encontrados com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum resultado encontrado para o CPF informado")
    })
    @GetMapping("/cliente/{cpf}")
    public ResponseEntity<List<QuizResultado>> buscarQuizzesPorCliente(@PathVariable String cpf) {
        List<QuizResultado> quizzes = quizService.buscarPorCpfCliente(cpf);
        if (quizzes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(quizzes);
    }

    @Operation(summary = "Atualiza um resultado de quiz", description = "Atualiza os dados de um resultado de quiz existente pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Resultado não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<QuizResultado> atualizarQuiz(@PathVariable Long id, @RequestBody @Valid QuizResultadoDTO dto) {
        return quizService.atualizarQuiz(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deleta um resultado de quiz", description = "Remove um resultado de quiz do sistema pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Resultado deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Resultado não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarQuiz(@PathVariable Long id) {
        boolean deletado = quizService.deletarQuiz(id);
        if (!deletado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}