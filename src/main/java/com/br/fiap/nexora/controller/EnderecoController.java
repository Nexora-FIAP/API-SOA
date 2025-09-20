package com.br.fiap.nexora.controller;

import com.br.fiap.nexora.dto.EnderecoDTO;
import com.br.fiap.nexora.model.Endereco;
import com.br.fiap.nexora.repository.EnderecoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<Endereco> cadastrarEndereco(@RequestBody @Valid EnderecoDTO enderecoDTO) {
        Endereco endereco = new Endereco(enderecoDTO);
        enderecoRepository.save(endereco);
        return ResponseEntity.status(HttpStatus.CREATED).body(endereco);
    }

    @GetMapping("/{cep}")
    public ResponseEntity<Endereco> buscarEndereco(@PathVariable String cep) {
        return enderecoRepository.findById(cep)
                .map(endereco -> ResponseEntity.ok().body(endereco))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletarEndereco(@PathVariable String id) {
        if (!enderecoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        enderecoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
