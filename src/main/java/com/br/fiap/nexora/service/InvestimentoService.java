package com.br.fiap.nexora.service;

import com.br.fiap.nexora.dto.InvestimentoDTO;
import com.br.fiap.nexora.model.Cliente;
import com.br.fiap.nexora.model.Investimento;
import com.br.fiap.nexora.repository.ClienteRepository;
import com.br.fiap.nexora.repository.InvestimentoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvestimentoService {

    @Autowired
    private InvestimentoRepository investimentoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public Investimento criarInvestimento(InvestimentoDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.cpfCliente())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Investimento investimento = new Investimento(dto, cliente);
        return investimentoRepository.save(investimento);
    }

    public Optional<Investimento> buscarPorId(Long id) {
        return investimentoRepository.findById(id);
    }

    public List<Investimento> buscarTodos() {
        return investimentoRepository.findAll();
    }

    public List<Investimento> buscarPorCpfCliente(String cpf) {
        return investimentoRepository.findByClienteCpf(cpf);
    }

    @Transactional
    public Optional<Investimento> atualizarInvestimento(Long id, InvestimentoDTO dto) {
        return investimentoRepository.findById(id).map(investimento -> {
            Cliente cliente = clienteRepository.findById(dto.cpfCliente())
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

            investimento.atualizar(dto, cliente);
            return investimentoRepository.save(investimento);
        });
    }

    @Transactional
    public boolean deletarInvestimento(Long id) {
        if (!investimentoRepository.existsById(id)) {
            return false;
        }
        investimentoRepository.deleteById(id);
        return true;
    }
}