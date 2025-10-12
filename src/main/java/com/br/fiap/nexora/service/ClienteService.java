package com.br.fiap.nexora.service;

import com.br.fiap.nexora.dto.ClienteDTO;
import com.br.fiap.nexora.model.Cliente;
import com.br.fiap.nexora.repository.ClienteRepository;
import com.br.fiap.nexora.repository.EnderecoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Cliente criarCliente(ClienteDTO dto) {
        Cliente cliente = new Cliente(dto);
        cliente.setSenha(passwordEncoder.encode(dto.senha()));
        return clienteRepository.save(cliente);
    }

    @Transactional
    public Cliente atualizarCliente(String cpf, ClienteDTO dto) {
        Cliente cliente = clienteRepository.findById(cpf)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        cliente.atualizar(dto, enderecoRepository);
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> buscarPorCpf(String cpf) {
        return clienteRepository.findById(cpf);
    }

    public List<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    @Transactional
    public void deletarCliente(String cpf) {
        clienteRepository.deleteById(cpf);
    }
}