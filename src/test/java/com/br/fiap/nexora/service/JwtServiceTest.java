package com.br.fiap.nexora.service;

import com.br.fiap.nexora.enums.PerfilInvestidor;
import com.br.fiap.nexora.model.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;
    private Cliente cliente;

    @BeforeEach
    void setup() {
        jwtService = new JwtService();

        cliente = new Cliente();
        cliente.setCpf("12345678900");
        cliente.setNome("Leticia");
        cliente.setPerfilInvestidor(PerfilInvestidor.MODERADO);
    }

    @Test
    void deveGerarTokenValidoParaCliente() {
        String token = jwtService.gerarToken(cliente);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void deveValidarTokenERetornarCpf() {
        String token = jwtService.gerarToken(cliente);

        String cpfExtraido = jwtService.validarToken(token);

        assertEquals(cliente.getCpf(), cpfExtraido);
    }
}