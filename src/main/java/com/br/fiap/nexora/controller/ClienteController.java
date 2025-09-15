package com.br.fiap.nexora.controller;

import com.br.fiap.nexora.repository.ClienteRepository;
import jakarta.persistence.Id;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    ClienteRepository ClienteRepository;

    @PostMapping
    @Transactional
    public void cadastrarCliente(@RequestBody @Valid)
}
