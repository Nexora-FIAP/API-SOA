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

    // GET e DELETE permanecem iguais
}

