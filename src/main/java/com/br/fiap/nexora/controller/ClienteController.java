package com.br.fiap.nexora.controller;

import com.br.fiap.nexora.dto.ClienteDTO;
import com.br.fiap.nexora.model.Cliente;
import com.br.fiap.nexora.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    ClienteRepository clienteRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<Cliente> cadastrarCliente(@RequestBody @Valid ClienteDTO clienteDTO){
        Cliente cliente = new Cliente(clienteDTO);
        clienteRepository.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }
}
