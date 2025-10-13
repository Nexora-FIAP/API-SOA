package com.br.fiap.nexora.service;

import com.br.fiap.nexora.dto.TransacaoBancariaDTO;
import com.br.fiap.nexora.model.ContaBancaria;
import com.br.fiap.nexora.model.TransacaoBancaria;
import com.br.fiap.nexora.repository.ContaBancariaRepository;
import com.br.fiap.nexora.repository.TransacaoBancariaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransacaoBancariaService {

    @Autowired
    private TransacaoBancariaRepository transacaoRepository;

    @Autowired
    private ContaBancariaRepository contaRepository;

    @Transactional
    public TransacaoBancaria criarTransacao(TransacaoBancariaDTO dto) {
        ContaBancaria conta = contaRepository.findById(dto.contaBancariaId())
                .orElseThrow(() -> new RuntimeException("Conta bancária não encontrada"));

        TransacaoBancaria transacao = new TransacaoBancaria(dto, conta);
        return transacaoRepository.save(transacao);
    }

    public Optional<TransacaoBancaria> buscarPorId(Long id) {
        return transacaoRepository.findById(id);
    }

    public List<TransacaoBancaria> buscarTodas() {
        return transacaoRepository.findAll();
    }

    public List<TransacaoBancaria> buscarPorConta(Long contaId) {
        return transacaoRepository.findByContaBancariaId(contaId);
    }

    @Transactional
    public Optional<TransacaoBancaria> atualizarTransacao(Long id, TransacaoBancariaDTO dto) {
        return transacaoRepository.findById(id).map(transacao -> {
            ContaBancaria conta = contaRepository.findById(dto.contaBancariaId())
                    .orElseThrow(() -> new RuntimeException("Conta bancária não encontrada"));

            transacao.atualizar(dto, conta);
            return transacaoRepository.save(transacao);
        });
    }

    @Transactional
    public boolean deletarTransacao(Long id) {
        if (!transacaoRepository.existsById(id)) {
            return false;
        }
        transacaoRepository.deleteById(id);
        return true;
    }
}