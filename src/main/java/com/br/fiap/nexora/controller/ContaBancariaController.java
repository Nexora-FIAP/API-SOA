package com.br.fiap.nexora.controller;

import com.br.fiap.nexora.dto.ContaBancariaDTO;
import com.br.fiap.nexora.model.ContaBancaria;
import com.br.fiap.nexora.service.ContaBancariaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conta")
public class ContaBancariaController {

    @Autowired
    private ContaBancariaService contaService;

    @PostMapping
    public ResponseEntity<ContaBancaria> cadastrarConta(@RequestBody @Valid ContaBancariaDTO contaDTO) {
        try {
            ContaBancaria conta = contaService.criarConta(contaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(conta);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContaBancaria> atualizarConta(@PathVariable Long id, @RequestBody @Valid ContaBancariaDTO contaDTO) {
        return contaService.atualizarConta(id, contaDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaBancaria> buscarConta(@PathVariable Long id) {
        return contaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ContaBancaria>> buscarTodasContas() {
        List<ContaBancaria> contas = contaService.buscarTodas();
        if (contas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(contas);
    }

    @GetMapping("/cliente/{cpf}")
    public ResponseEntity<List<ContaBancaria>> buscarContasPorCliente(@PathVariable String cpf) {
        List<ContaBancaria> contas = contaService.buscarPorCpfCliente(cpf);
        if (contas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(contas);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarConta(@PathVariable Long id) {
        boolean deletado = contaService.deletarConta(id);
        if (!deletado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}