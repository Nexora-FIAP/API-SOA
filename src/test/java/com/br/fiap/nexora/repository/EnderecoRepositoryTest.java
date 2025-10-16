package com.br.fiap.nexora.repository;

import com.br.fiap.nexora.model.Endereco;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EnderecoRepositoryTest {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Test
    @DisplayName("Deve encontrar endereços pelo CEP")
    void deveEncontrarEnderecoPorCep() {
        // Arrange
        Endereco endereco1 = new Endereco();
        endereco1.setCep("12345-678");
        endereco1.setRua("Rua das Flores");
        endereco1.setNumero(100);
        endereco1.setComplemento("Apto 202");
        endereco1.setBairro("Centro");
        endereco1.setCidade("São Paulo");
        endereco1.setUf("SP");

        Endereco endereco2 = new Endereco();
        endereco2.setCep("12345-678");
        endereco2.setRua("Rua das Palmeiras");
        endereco2.setNumero(200);
        endereco2.setComplemento("Casa");
        endereco2.setBairro("Jardim");
        endereco2.setCidade("São Paulo");
        endereco2.setUf("SP");

        enderecoRepository.save(endereco1);
        enderecoRepository.save(endereco2);

        // Act
        List<Endereco> encontrados = enderecoRepository.findByCep("12345-678");

        // Assert
        assertEquals(2, encontrados.size());
        assertTrue(encontrados.stream().anyMatch(e -> e.getRua().equals("Rua das Flores")));
        assertTrue(encontrados.stream().anyMatch(e -> e.getRua().equals("Rua das Palmeiras")));
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não encontrar CEP")
    void deveRetornarListaVaziaQuandoCepNaoExiste() {
        List<Endereco> encontrados = enderecoRepository.findByCep("00000-000");
        assertTrue(encontrados.isEmpty());
    }
}