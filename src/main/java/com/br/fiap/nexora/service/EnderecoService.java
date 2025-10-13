package com.br.fiap.nexora.service;

import com.br.fiap.nexora.dto.EnderecoDTO;
import com.br.fiap.nexora.model.Endereco;
import com.br.fiap.nexora.repository.EnderecoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Transactional
    public Endereco criarEndereco(EnderecoDTO dto) {
        Endereco endereco = new Endereco(dto);
        return enderecoRepository.save(endereco);
    }

    public List<Endereco> buscarTodos() {
        return enderecoRepository.findAll();
    }

    public Optional<Endereco> buscarPorId(Long id) {
        return enderecoRepository.findById(id);
    }

    public List<Endereco> buscarPorCep(String cep) {
        return enderecoRepository.findByCep(cep);
    }

    @Transactional
    public Optional<Endereco> atualizarEndereco(Long id, EnderecoDTO dto) {
        return enderecoRepository.findById(id).map(endereco -> {
            endereco.atualizar(dto);
            return enderecoRepository.save(endereco);
        });
    }

    @Transactional
    public boolean deletarEndereco(Long id) {
        if (!enderecoRepository.existsById(id)) {
            return false;
        }
        enderecoRepository.deleteById(id);
        return true;
    }
}