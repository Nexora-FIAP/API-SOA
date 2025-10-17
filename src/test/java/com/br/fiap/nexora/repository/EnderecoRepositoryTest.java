package com.br.fiap.nexora.repository;

import com.br.fiap.nexora.model.Endereco;
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
    void deveSalvarERecuperarEndereco() {
        Endereco endereco = new Endereco();
        endereco.setCep("12345-678");
        endereco.setRua("Rua A");
        endereco.setNumero(10);
        endereco.setComplemento("Apto");
        endereco.setBairro("Bairro A");
        endereco.setCidade("Cidade A");
        endereco.setUf("SP");

        Endereco salvo = enderecoRepository.save(endereco);

        assertNotNull(salvo.getId());
        assertEquals("12345-678", salvo.getCep());
    }

    @Test
    void deveBuscarEnderecoPorCep() {
        Endereco endereco1 = new Endereco();
        endereco1.setCep("99999-999");
        endereco1.setRua("Rua B");
        endereco1.setNumero(20);
        endereco1.setComplemento("Casa");
        endereco1.setBairro("Bairro B");
        endereco1.setCidade("Cidade B");
        endereco1.setUf("RJ");

        Endereco endereco2 = new Endereco();
        endereco2.setCep("99999-999");
        endereco2.setRua("Rua C");
        endereco2.setNumero(30);
        endereco2.setComplemento("Sala");
        endereco2.setBairro("Bairro C");
        endereco2.setCidade("Cidade C");
        endereco2.setUf("RJ");

        enderecoRepository.save(endereco1);
        enderecoRepository.save(endereco2);

        List<Endereco> encontrados = enderecoRepository.findByCep("99999-999");

        assertEquals(2, encontrados.size());
        assertTrue(encontrados.stream().allMatch(e -> e.getCep().equals("99999-999")));
    }
}