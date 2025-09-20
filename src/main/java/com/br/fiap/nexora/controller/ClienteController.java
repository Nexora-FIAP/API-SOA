package com.br.fiap.nexora.controller;

import com.br.fiap.nexora.dto.ClienteDTO;
import com.br.fiap.nexora.model.Cliente;
import com.br.fiap.nexora.repository.ClienteRepository;
import com.br.fiap.nexora.repository.EnderecoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    // POST - criar cliente
    @PostMapping
    @Transactional
    public ResponseEntity<Cliente> cadastrarCliente(@RequestBody @Valid ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente(clienteDTO);
        clienteRepository.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }

    // GET por CPF
    @GetMapping("/{cpf}")
    public ResponseEntity<Cliente> buscarCliente(@PathVariable String cpf) {
        return clienteRepository.findById(cpf)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET - buscar todos os clientes
    @GetMapping
    public ResponseEntity<List<Cliente>> buscarTodosClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        if (clientes.isEmpty()) {
            return ResponseEntity.noContent().build(); // retorna 204 se não tiver nenhum
        }
        return ResponseEntity.ok(clientes);
    }

    // PUT - atualizar cliente
    @PutMapping("/{cpf}")
    @Transactional
    public ResponseEntity<Cliente> atualizarCliente(@PathVariable String cpf, @RequestBody @Valid ClienteDTO clienteDTO) {
        return clienteRepository.findById(cpf)
                .map(cliente -> {
                    cliente.atualizar(clienteDTO, enderecoRepository); // passa os 2 argumentos agora
                    clienteRepository.save(cliente);
                    return ResponseEntity.ok(cliente);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE - deletar cliente
    @DeleteMapping("/{cpf}")
    @Transactional
    public ResponseEntity<Void> deletarCliente(@PathVariable String cpf) {
        if (!clienteRepository.existsById(cpf)) {
            return ResponseEntity.notFound().build();
        }
        clienteRepository.deleteById(cpf);
        return ResponseEntity.noContent().build();
    }
}
