package com.br.fiap.nexora.repository;

import com.br.fiap.nexora.enums.PerfilInvestidor;
import com.br.fiap.nexora.model.Cliente;
import com.br.fiap.nexora.model.Endereco;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void deveSalvarERecuperarClientePorCpf() {
        Endereco endereco = new Endereco();
        endereco.setCep("12345-678");
        endereco.setRua("Rua A");
        endereco.setNumero(10);
        endereco.setComplemento("Apto");
        endereco.setBairro("Centro");
        endereco.setCidade("São Paulo");
        endereco.setUf("SP");

        Cliente cliente = new Cliente();
        cliente.setCpf("12345678900");
        cliente.setNome("Leticia");
        cliente.setEmail("lety@email.com");
        cliente.setDataNascimento(LocalDate.of(2004, 10, 15));
        cliente.setPerfilInvestidor(PerfilInvestidor.MODERADO);
        cliente.setTelefone("11999999999");
        cliente.setEndereco(endereco);
        cliente.setSenha("senha123");

        clienteRepository.save(cliente);

        Optional<Cliente> encontrado = clienteRepository.findById("12345678900");

        assertTrue(encontrado.isPresent());
        assertEquals("Leticia", encontrado.get().getNome());
    }
}