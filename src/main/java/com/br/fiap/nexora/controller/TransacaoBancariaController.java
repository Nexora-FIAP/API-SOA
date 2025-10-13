package com.br.fiap.nexora.controller;

import com.br.fiap.nexora.dto.TransacaoBancariaDTO;
import com.br.fiap.nexora.model.TransacaoBancaria;
import com.br.fiap.nexora.service.TransacaoBancariaService;
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

    @PostMapping
    public ResponseEntity<TransacaoBancaria> cadastrarTransacao(@RequestBody @Valid TransacaoBancariaDTO dto) {
        TransacaoBancaria transacao = transacaoService.criarTransacao(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(transacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransacaoBancaria> buscarTransacao(@PathVariable Long id) {
        return transacaoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<TransacaoBancaria>> buscarTodasTransacoes() {
        List<TransacaoBancaria> transacoes = transacaoService.buscarTodas();
        if (transacoes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(transacoes);
    }

    @GetMapping("/conta/{contaId}")
    public ResponseEntity<List<TransacaoBancaria>> buscarTransacoesPorConta(@PathVariable Long contaId) {
        List<TransacaoBancaria> transacoes = transacaoService.buscarPorConta(contaId);
        if (transacoes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(transacoes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransacaoBancaria> atualizarTransacao(@PathVariable Long id, @RequestBody @Valid TransacaoBancariaDTO dto) {
        return transacaoService.atualizarTransacao(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTransacao(@PathVariable Long id) {
        boolean deletado = transacaoService.deletarTransacao(id);
        if (!deletado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}