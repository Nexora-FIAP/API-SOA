package com.br.fiap.nexora.repository;

import com.br.fiap.nexora.enums.TipoInvestimento;
import com.br.fiap.nexora.model.Cliente;
import com.br.fiap.nexora.model.Investimento;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class InvestimentoRepositoryTest {

    @Autowired
    private InvestimentoRepository investimentoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void deveSalvarERecuperarInvestimentoPorCpf() {
        Cliente cliente = new Cliente();
        cliente.setCpf("98765432100");
        cliente.setNome("Lety Investidora");
        cliente.setEmail("lety@email.com");
        cliente.setDataNascimento(LocalDate.of(2000, 1, 1));
        cliente.setSenha("senha123");

        clienteRepository.save(cliente);

        Investimento investimento = new Investimento();
        investimento.setTipo(TipoInvestimento.TESOURO_SELIC);
        investimento.setValorAplicado(5000.0f);
        investimento.setDataAplicacao(LocalDate.of(2024, 1, 10));
        investimento.setRendimentoAtual(5500.0f);
        investimento.setCliente(cliente);

        investimentoRepository.save(investimento);

        List<Investimento> encontrados = investimentoRepository.findByClienteCpf("98765432100");

        assertFalse(encontrados.isEmpty());
        assertEquals(TipoInvestimento.TESOURO_SELIC, encontrados.get(0).getTipo());
    }
}