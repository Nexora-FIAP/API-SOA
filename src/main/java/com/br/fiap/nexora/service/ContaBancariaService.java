package com.br.fiap.nexora.service;

import com.br.fiap.nexora.dto.ContaBancariaDTO;
import com.br.fiap.nexora.model.Cliente;
import com.br.fiap.nexora.model.ContaBancaria;
import com.br.fiap.nexora.repository.ClienteRepository;
import com.br.fiap.nexora.repository.ContaBancariaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContaBancariaService {

    @Autowired
    private ContaBancariaRepository contaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public ContaBancaria criarConta(ContaBancariaDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.cpfCliente())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        boolean contaExistente = contaRepository.existsByNumeroContaAndClienteCpf(dto.numeroConta(), dto.cpfCliente());
        if (contaExistente) {
            throw new RuntimeException("Conta já existente para este cliente");
        }

        ContaBancaria conta = new ContaBancaria(dto, cliente);
        return contaRepository.save(conta);
    }

    @Transactional
    public Optional<ContaBancaria> atualizarConta(Long id, ContaBancariaDTO dto) {
        return contaRepository.findById(id).map(conta -> {
            Cliente cliente = clienteRepository.findById(dto.cpfCliente())
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

            conta.atualizar(dto, cliente);
            return contaRepository.save(conta);
        });
    }

    public Optional<ContaBancaria> buscarPorId(Long id) {
        return contaRepository.findById(id);
    }

    public List<ContaBancaria> buscarTodas() {
        return contaRepository.findAll();
    }

    public List<ContaBancaria> buscarPorCpfCliente(String cpf) {
        return contaRepository.findByClienteCpf(cpf);
    }

    @Transactional
    public boolean deletarConta(Long id) {
        if (!contaRepository.existsById(id)) {
            return false;
        }
        contaRepository.deleteById(id);
        return true;
    }
}