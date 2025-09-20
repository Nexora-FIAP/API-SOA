package com.br.fiap.nexora.controller;

import com.br.fiap.nexora.dto.ContaBancariaDTO;
import com.br.fiap.nexora.model.Cliente;
import com.br.fiap.nexora.model.ContaBancaria;
import com.br.fiap.nexora.repository.ClienteRepository;
import com.br.fiap.nexora.repository.ContaBancariaRepository;
import jakarta.transaction.Transactional;
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
    private ContaBancariaRepository contaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    // POST - criar conta bancária
    @PostMapping
    @Transactional
    public ResponseEntity<ContaBancaria> cadastrarConta(@RequestBody @Valid ContaBancariaDTO contaDTO) {
        Cliente cliente = clienteRepository.findById(contaDTO.cpfCliente())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        boolean contaExistente = contaRepository
                .existsByNumeroContaAndClienteCpf(contaDTO.numeroConta(), contaDTO.cpfCliente());

        if (contaExistente) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409 - já existe
        }

        ContaBancaria conta = new ContaBancaria(contaDTO, cliente);
        contaRepository.save(conta);
        return ResponseEntity.status(HttpStatus.CREATED).body(conta);
    }

    // PUT - atualizar conta
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ContaBancaria> atualizarConta(@PathVariable Long id, @RequestBody @Valid ContaBancariaDTO contaDTO) {
        return contaRepository.findById(id)
                .map(conta -> {
                    Cliente cliente = clienteRepository.findById(contaDTO.cpfCliente())
                            .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

                    conta.atualizar(contaDTO, cliente);
                    contaRepository.save(conta);
                    return ResponseEntity.ok(conta);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // GET por ID
    @GetMapping("/{id}")
    public ResponseEntity<ContaBancaria> buscarConta(@PathVariable Long id) {
        return contaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET - listar todas as contas
    @GetMapping
    public ResponseEntity<List<ContaBancaria>> buscarTodasContas() {
        List<ContaBancaria> contas = contaRepository.findAll();
        if (contas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(contas);
    }

    // GET - listar contas de um cliente pelo CPF
    @GetMapping("/cliente/{cpf}")
    public ResponseEntity<List<ContaBancaria>> buscarContasPorCliente(@PathVariable String cpf) {
        List<ContaBancaria> contas = contaRepository.findByClienteCpf(cpf);
        if (contas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(contas);
    }

    // DELETE - deletar conta por ID
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletarConta(@PathVariable Long id) {
        if (!contaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        contaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
