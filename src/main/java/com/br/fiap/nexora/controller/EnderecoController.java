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

import java.util.List;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {

    @Autowired
    private EnderecoRepository enderecoRepository;

    // POST - criar endereço
    @PostMapping
    @Transactional
    public ResponseEntity<Endereco> cadastrarEndereco(@RequestBody @Valid EnderecoDTO enderecoDTO) {
        Endereco endereco = new Endereco(enderecoDTO);
        enderecoRepository.save(endereco);
        return ResponseEntity.status(HttpStatus.CREATED).body(endereco);
    }

    // GET por id
    @GetMapping("/{id}")
    public ResponseEntity<Endereco> buscarEndereco(@PathVariable Long id) {
        return enderecoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET por CEP
    @GetMapping("/cep/{cep}")
    public ResponseEntity<List<Endereco>> buscarPorCep(@PathVariable String cep) {
        List<Endereco> enderecos = enderecoRepository.findByCep(cep);
        if (enderecos.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(enderecos);
    }

    // PUT - atualizar endereço
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Endereco> atualizarEndereco(@PathVariable Long id, @RequestBody @Valid EnderecoDTO enderecoDTO) {
        return enderecoRepository.findById(id)
                .map(endereco -> {
                    endereco.atualizar(enderecoDTO);
                    enderecoRepository.save(endereco);
                    return ResponseEntity.ok(endereco);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE - deletar endereço
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletarEndereco(@PathVariable Long id) {
        if (!enderecoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        enderecoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
