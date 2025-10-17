package com.br.fiap.nexora.repository;

import com.br.fiap.nexora.enums.TipoConta;
import com.br.fiap.nexora.model.Cliente;
import com.br.fiap.nexora.model.ContaBancaria;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ContaBancariaRepositoryTest {

    @Autowired
    private ContaBancariaRepository contaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void deveSalvarERecuperarContaPorCpf() {
        Cliente cliente = new Cliente();
        cliente.setCpf("12345678900");
        cliente.setNome("Leticia");
        cliente.setEmail("lety@email.com");
        cliente.setDataNascimento(LocalDate.of(2004, 10, 15));
        cliente.setSenha("senha123");

        clienteRepository.save(cliente); // 💡 Salva o cliente antes

        ContaBancaria conta = new ContaBancaria();
        conta.setBanco("Banco Teste");
        conta.setNumeroConta(12345);
        conta.setAgencia("0001");
        conta.setTipoConta(TipoConta.CORRENTE);
        conta.setCliente(cliente);
        conta.setSaldoAtual(1000.0f);

        contaRepository.save(conta);

        List<ContaBancaria> contas = contaRepository.findByClienteCpf("12345678900");

        assertFalse(contas.isEmpty());
        assertEquals("Banco Teste", contas.get(0).getBanco());
    }

    @Test
    void deveVerificarExistenciaDeContaPorNumeroECPF() {
        Cliente cliente = new Cliente();
        cliente.setCpf("12345678900");
        cliente.setNome("Leticia");
        cliente.setEmail("lety@email.com");
        cliente.setDataNascimento(LocalDate.of(2004, 10, 15));
        cliente.setSenha("senha123");

        clienteRepository.save(cliente); // 💡 Salva o cliente antes

        ContaBancaria conta = new ContaBancaria();
        conta.setNumeroConta(12345);
        conta.setAgencia("0001");
        conta.setBanco("Banco Teste");
        conta.setTipoConta(TipoConta.CORRENTE);
        conta.setCliente(cliente);
        conta.setSaldoAtual(500.0f);

        contaRepository.save(conta);

        boolean existe = contaRepository.existsByNumeroContaAndClienteCpf(12345, "12345678900");

        assertTrue(existe);
    }
}