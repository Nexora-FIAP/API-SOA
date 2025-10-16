package com.br.fiap.nexora.repository;

import com.br.fiap.nexora.dto.EnderecoDTO;
import com.br.fiap.nexora.enums.PerfilInvestidor;
import com.br.fiap.nexora.model.Cliente;
import com.br.fiap.nexora.model.Endereco;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("Deve salvar e buscar cliente por CPF")
    void deveSalvarEBuscarClientePorCpf() {
        // Arrange
        Endereco endereco = new Endereco();
        endereco.setCep("12345-678");
        endereco.setRua("Rua Teste");
        endereco.setNumero(100);
        endereco.setComplemento("Apto 10");
        endereco.setBairro("Centro");
        endereco.setCidade("São Paulo");
        endereco.setUf("SP");

        Cliente cliente = new Cliente();
        cliente.setCpf("12345678900");
        cliente.setNome("Leticia Resina");
        cliente.setEmail("lety@email.com");
        cliente.setDataNascimento(LocalDate.of(2004, 10, 15));
        cliente.setPerfilInvestidor(PerfilInvestidor.MODERADO);
        cliente.setTelefone("11999999999");
        cliente.setEndereco(endereco);
        cliente.setSenha("senha123");

        clienteRepository.save(cliente);

        // Act
        Optional<Cliente> encontrado = clienteRepository.findById("12345678900");

        // Assert
        assertTrue(encontrado.isPresent());
        assertEquals("Leticia Resina", encontrado.get().getNome());
        assertEquals("12345-678", encontrado.get().getEndereco().getCep());
    }

    @Test
    @DisplayName("Deve deletar cliente por CPF")
    void deveDeletarClientePorCpf() {
        Cliente cliente = new Cliente();
        cliente.setCpf("98765432100");
        cliente.setNome("Cliente Teste");
        cliente.setEmail("teste@email.com");
        cliente.setDataNascimento(LocalDate.of(1990, 1, 1));
        cliente.setPerfilInvestidor(PerfilInvestidor.CONSERVADOR);
        cliente.setTelefone("11988888888");
        cliente.setEndereco(new Endereco());
        cliente.setSenha("senha");

        clienteRepository.save(cliente);

        clienteRepository.deleteById("98765432100");

        Optional<Cliente> resultado = clienteRepository.findById("98765432100");
        assertFalse(resultado.isPresent());
    }
}