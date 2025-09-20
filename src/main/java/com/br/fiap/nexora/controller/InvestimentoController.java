package com.br.fiap.nexora.controller;

import com.br.fiap.nexora.dto.InvestimentoDTO;
import com.br.fiap.nexora.model.Cliente;
import com.br.fiap.nexora.model.Investimento;
import com.br.fiap.nexora.repository.ClienteRepository;
import com.br.fiap.nexora.repository.InvestimentoRepository;
import jakarta.transaction.Transactional;
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
    private InvestimentoRepository investimentoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    // POST - criar investimento
    @PostMapping
    @Transactional
    public ResponseEntity<Investimento> cadastrarInvestimento(@RequestBody @Valid InvestimentoDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.cpfCliente())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Investimento investimento = new Investimento(dto, cliente);
        investimentoRepository.save(investimento);
        return ResponseEntity.status(HttpStatus.CREATED).body(investimento);
    }

    // GET por ID
    @GetMapping("/{id}")
    public ResponseEntity<Investimento> buscarInvestimento(@PathVariable Long id) {
        return investimentoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET todos os investimentos
    @GetMapping
    public ResponseEntity<List<Investimento>> buscarTodosInvestimentos() {
        List<Investimento> investimentos = investimentoRepository.findAll();
        if (investimentos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(investimentos);
    }

    // GET por cliente
    @GetMapping("/cliente/{cpf}")
    public ResponseEntity<List<Investimento>> buscarInvestimentosPorCliente(@PathVariable String cpf) {
        List<Investimento> investimentos = investimentoRepository.findByClienteCpf(cpf);
        if (investimentos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(investimentos);
    }

    // PUT - atualizar investimento
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Investimento> atualizarInvestimento(@PathVariable Long id, @RequestBody @Valid InvestimentoDTO dto) {
        return investimentoRepository.findById(id)
                .map(investimento -> {
                    Cliente cliente = clienteRepository.findById(dto.cpfCliente())
                            .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

                    investimento.atualizar(dto, cliente);
                    investimentoRepository.save(investimento);
                    return ResponseEntity.ok(investimento);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE - remover investimento
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletarInvestimento(@PathVariable Long id) {
        if (!investimentoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        investimentoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
