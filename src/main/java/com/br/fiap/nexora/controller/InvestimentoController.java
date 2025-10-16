package com.br.fiap.nexora.controller;

import com.br.fiap.nexora.dto.InvestimentoDTO;
import com.br.fiap.nexora.model.Investimento;
import com.br.fiap.nexora.service.InvestimentoService;
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
@RequestMapping("/investimento")
public class InvestimentoController {

    @Autowired
    private InvestimentoService investimentoService;

    @Operation(summary = "Cadastra um novo investimento", description = "Cria um novo investimento vinculado a um cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Investimento cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<Investimento> cadastrarInvestimento(@RequestBody @Valid InvestimentoDTO dto) {
        Investimento investimento = investimentoService.criarInvestimento(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(investimento);
    }

    @Operation(summary = "Busca investimento por ID", description = "Retorna os dados de um investimento específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Investimento encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Investimento não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Investimento> buscarInvestimento(@PathVariable Long id) {
        return investimentoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Lista todos os investimentos", description = "Retorna todos os investimentos cadastrados no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de investimentos retornada com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum investimento cadastrado")
    })
    @GetMapping
    public ResponseEntity<List<Investimento>> buscarTodosInvestimentos() {
        List<Investimento> investimentos = investimentoService.buscarTodos();
        if (investimentos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(investimentos);
    }

    @Operation(summary = "Busca investimentos por CPF do cliente", description = "Retorna todos os investimentos vinculados ao CPF informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Investimentos encontrados com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum investimento encontrado para o CPF informado")
    })
    @GetMapping("/cliente/{cpf}")
    public ResponseEntity<List<Investimento>> buscarInvestimentosPorCliente(@PathVariable String cpf) {
        List<Investimento> investimentos = investimentoService.buscarPorCpfCliente(cpf);
        if (investimentos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(investimentos);
    }

    @Operation(summary = "Atualiza um investimento", description = "Atualiza os dados de um investimento existente pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Investimento atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Investimento não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Investimento> atualizarInvestimento(@PathVariable Long id, @RequestBody @Valid InvestimentoDTO dto) {
        return investimentoService.atualizarInvestimento(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deleta um investimento", description = "Remove um investimento do sistema pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Investimento deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Investimento não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarInvestimento(@PathVariable Long id) {
        boolean deletado = investimentoService.deletarInvestimento(id);
        if (!deletado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}