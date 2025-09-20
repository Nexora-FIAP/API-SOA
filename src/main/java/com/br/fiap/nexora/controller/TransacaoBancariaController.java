package com.br.fiap.nexora.controller;

import com.br.fiap.nexora.dto.TransacaoBancariaDTO;
import com.br.fiap.nexora.model.ContaBancaria;
import com.br.fiap.nexora.model.TransacaoBancaria;
import com.br.fiap.nexora.repository.ContaBancariaRepository;
import com.br.fiap.nexora.repository.TransacaoBancariaRepository;
import jakarta.transaction.Transactional;
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
    private TransacaoBancariaRepository transacaoRepository;

    @Autowired
    private ContaBancariaRepository contaRepository;

    // POST - criar transação
    @PostMapping
    @Transactional
    public ResponseEntity<TransacaoBancaria> cadastrarTransacao(@RequestBody @Valid TransacaoBancariaDTO dto) {
        ContaBancaria conta = contaRepository.findById(dto.contaBancariaId())
                .orElseThrow(() -> new RuntimeException("Conta bancária não encontrada"));

        TransacaoBancaria transacao = new TransacaoBancaria(dto, conta);
        transacaoRepository.save(transacao);
        return ResponseEntity.status(HttpStatus.CREATED).body(transacao);
    }

    // GET por ID
    @GetMapping("/{id}")
    public ResponseEntity<TransacaoBancaria> buscarTransacao(@PathVariable Long id) {
        return transacaoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET todas as transações
    @GetMapping
    public ResponseEntity<List<TransacaoBancaria>> buscarTodasTransacoes() {
        List<TransacaoBancaria> transacoes = transacaoRepository.findAll();
        if (transacoes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(transacoes);
    }

    // GET transações de uma conta
    @GetMapping("/conta/{contaId}")
    public ResponseEntity<List<TransacaoBancaria>> buscarTransacoesPorConta(@PathVariable Long contaId) {
        List<TransacaoBancaria> transacoes = transacaoRepository.findByContaBancariaId(contaId);
        if (transacoes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(transacoes);
    }

    // PUT - atualizar transação
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TransacaoBancaria> atualizarTransacao(@PathVariable Long id, @RequestBody @Valid TransacaoBancariaDTO dto) {
        return transacaoRepository.findById(id)
                .map(transacao -> {
                    ContaBancaria conta = contaRepository.findById(dto.contaBancariaId())
                            .orElseThrow(() -> new RuntimeException("Conta bancária não encontrada"));

                    transacao.atualizar(dto, conta);
                    transacaoRepository.save(transacao);
                    return ResponseEntity.ok(transacao);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE - remover transação
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletarTransacao(@PathVariable Long id) {
        if (!transacaoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        transacaoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
