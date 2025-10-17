package com.br.fiap.nexora.controller;

import com.br.fiap.nexora.dto.LoginDTO;
import com.br.fiap.nexora.dto.TokenDTO;
import com.br.fiap.nexora.enums.PerfilInvestidor;
import com.br.fiap.nexora.model.Cliente;
import com.br.fiap.nexora.repository.ClienteRepository;
import com.br.fiap.nexora.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthController authController;

    private Cliente cliente;
    private LoginDTO loginDTO;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        cliente = new Cliente();
        cliente.setCpf("12345678900");
        cliente.setNome("Leticia");
        cliente.setSenha("senha123");
        cliente.setPerfilInvestidor(PerfilInvestidor.MODERADO);

        loginDTO = new LoginDTO(cliente.getCpf(), "senha123");
    }

    @Test
    void deveAutenticarClienteComSucessoERetornarToken() {
        when(clienteRepository.findById(loginDTO.cpf())).thenReturn(Optional.of(cliente));
        when(passwordEncoder.matches(loginDTO.senha(), cliente.getSenha())).thenReturn(true);
        when(jwtService.gerarToken(cliente)).thenReturn("fake.jwt.token");

        ResponseEntity<?> response = authController.login(loginDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof TokenDTO);
        assertEquals("fake.jwt.token", ((TokenDTO) response.getBody()).token());
    }

    @Test
    void deveRetornarUnauthorizedQuandoSenhaInvalida() {
        when(clienteRepository.findById(loginDTO.cpf())).thenReturn(Optional.of(cliente));
        when(passwordEncoder.matches(loginDTO.senha(), cliente.getSenha())).thenReturn(false);

        ResponseEntity<?> response = authController.login(loginDTO);

        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Senha inválida", response.getBody());
    }

    @Test
    void deveLancarExcecaoQuandoCpfNaoEncontrado() {
        when(clienteRepository.findById(loginDTO.cpf())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> authController.login(loginDTO));
        assertEquals("CPF não encontrado", exception.getMessage());
    }
}