package com.br.fiap.nexora.controller;

import com.br.fiap.nexora.dto.TransacaoBancariaDTO;
import com.br.fiap.nexora.model.TransacaoBancaria;
import com.br.fiap.nexora.service.TransacaoBancariaService;
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
@RequestMapping("/transacao")
public class TransacaoBancariaController {

    @Autowired
    private TransacaoBancariaService transacaoService;

    @Operation(summary = "Cadastra uma nova transação bancária", description = "Registra uma transação bancária vinculada a uma conta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transação cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<TransacaoBancaria> cadastrarTransacao(@RequestBody @Valid TransacaoBancariaDTO dto) {
        TransacaoBancaria transacao = transacaoService.criarTransacao(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(transacao);
    }

    @Operation(summary = "Busca transação por ID", description = "Retorna os dados de uma transação específica pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transação encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Transação não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TransacaoBancaria> buscarTransacao(@PathVariable Long id) {
        return transacaoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Lista todas as transações bancárias", description = "Retorna todas as transações bancárias cadastradas no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de transações retornada com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhuma transação cadastrada")
    })
    @GetMapping
    public ResponseEntity<List<TransacaoBancaria>> buscarTodasTransacoes() {
        List<TransacaoBancaria> transacoes = transacaoService.buscarTodas();
        if (transacoes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(transacoes);
    }

    @Operation(summary = "Busca transações por ID da conta", description = "Retorna todas as transações vinculadas à conta informada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transações encontradas com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhuma transação encontrada para a conta informada")
    })
    @GetMapping("/conta/{contaId}")
    public ResponseEntity<List<TransacaoBancaria>> buscarTransacoesPorConta(@PathVariable Long contaId) {
        List<TransacaoBancaria> transacoes = transacaoService.buscarPorConta(contaId);
        if (transacoes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(transacoes);
    }

    @Operation(summary = "Atualiza uma transação bancária", description = "Atualiza os dados de uma transação existente pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transação atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Transação não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TransacaoBancaria> atualizarTransacao(@PathVariable Long id, @RequestBody @Valid TransacaoBancariaDTO dto) {
        return transacaoService.atualizarTransacao(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deleta uma transação bancária", description = "Remove uma transação bancária do sistema pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Transação deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Transação não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTransacao(@PathVariable Long id) {
        boolean deletado = transacaoService.deletarTransacao(id);
        if (!deletado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}