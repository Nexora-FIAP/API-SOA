package com.br.fiap.nexora.controller;

import com.br.fiap.nexora.dto.InvestimentoDTO;
import com.br.fiap.nexora.model.Investimento;
import com.br.fiap.nexora.service.InvestimentoService;
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

    @PostMapping
    public ResponseEntity<Investimento> cadastrarInvestimento(@RequestBody @Valid InvestimentoDTO dto) {
        Investimento investimento = investimentoService.criarInvestimento(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(investimento);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Investimento> buscarInvestimento(@PathVariable Long id) {
        return investimentoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Investimento>> buscarTodosInvestimentos() {
        List<Investimento> investimentos = investimentoService.buscarTodos();
        if (investimentos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(investimentos);
    }

    @GetMapping("/cliente/{cpf}")
    public ResponseEntity<List<Investimento>> buscarInvestimentosPorCliente(@PathVariable String cpf) {
        List<Investimento> investimentos = investimentoService.buscarPorCpfCliente(cpf);
        if (investimentos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(investimentos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Investimento> atualizarInvestimento(@PathVariable Long id, @RequestBody @Valid InvestimentoDTO dto) {
        return investimentoService.atualizarInvestimento(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarInvestimento(@PathVariable Long id) {
        boolean deletado = investimentoService.deletarInvestimento(id);
        if (!deletado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}