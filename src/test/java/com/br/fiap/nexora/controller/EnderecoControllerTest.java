package com.br.fiap.nexora.controller;

import com.br.fiap.nexora.dto.EnderecoDTO;
import com.br.fiap.nexora.model.Endereco;
import com.br.fiap.nexora.service.EnderecoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(EnderecoController.class)
class EnderecoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnderecoService enderecoService;

    @Autowired
    private ObjectMapper objectMapper;

    private EnderecoDTO getSampleDTO() {
        return new EnderecoDTO(
                null, "12345-678", "Rua Teste", 100,
                "Apto 10", "Centro", "São Paulo", "SP"
        );
    }

    private Endereco getSampleEndereco() {
        Endereco endereco = new Endereco(getSampleDTO());
        endereco.setId(1L);
        return endereco;
    }

    @Test
    @DisplayName("POST /endereco - deve cadastrar endereço")
    void deveCadastrarEndereco() throws Exception {
        Endereco endereco = getSampleEndereco();
        Mockito.when(enderecoService.criarEndereco(any())).thenReturn(endereco);

        mockMvc.perform(post("/endereco")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getSampleDTO())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(endereco.getId()))
                .andExpect(jsonPath("$.cep").value(endereco.getCep()));
    }

    @Test
    @DisplayName("GET /endereco - deve retornar lista de endereços")
    void deveBuscarTodosEnderecos() throws Exception {
        Mockito.when(enderecoService.buscarTodos()).thenReturn(List.of(getSampleEndereco()));

        mockMvc.perform(get("/endereco"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cep").value("12345-678"));
    }

    @Test
    @DisplayName("GET /endereco - deve retornar 204 se lista vazia")
    void deveRetornarNoContentSeListaVazia() throws Exception {
        Mockito.when(enderecoService.buscarTodos()).thenReturn(List.of());

        mockMvc.perform(get("/endereco"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("GET /endereco/{id} - deve retornar endereço por ID")
    void deveBuscarEnderecoPorId() throws Exception {
        Mockito.when(enderecoService.buscarPorId(1L)).thenReturn(Optional.of(getSampleEndereco()));

        mockMvc.perform(get("/endereco/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("GET /endereco/{id} - deve retornar 404 se não encontrado")
    void deveRetornarNotFoundPorId() throws Exception {
        Mockito.when(enderecoService.buscarPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/endereco/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /endereco/cep/{cep} - deve buscar por CEP")
    void deveBuscarPorCep() throws Exception {
        Mockito.when(enderecoService.buscarPorCep("12345-678")).thenReturn(List.of(getSampleEndereco()));

        mockMvc.perform(get("/endereco/cep/12345-678"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cep").value("12345-678"));
    }

    @Test
    @DisplayName("GET /endereco/cep/{cep} - deve retornar 404 se não encontrar")
    void deveRetornarNotFoundPorCep() throws Exception {
        Mockito.when(enderecoService.buscarPorCep("00000-000")).thenReturn(List.of());

        mockMvc.perform(get("/endereco/cep/00000-000"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /endereco/{id} - deve atualizar endereço")
    void deveAtualizarEndereco() throws Exception {
        Endereco atualizado = getSampleEndereco();
        atualizado.setCep("99999-999");

        Mockito.when(enderecoService.atualizarEndereco(eq(1L), any())).thenReturn(Optional.of(atualizado));

        mockMvc.perform(put("/endereco/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getSampleDTO())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cep").value("99999-999"));
    }

    @Test
    @DisplayName("PUT /endereco/{id} - deve retornar 404 se não encontrar")
    void naoDeveAtualizarEnderecoInexistente() throws Exception {
        Mockito.when(enderecoService.atualizarEndereco(eq(99L), any())).thenReturn(Optional.empty());

        mockMvc.perform(put("/endereco/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getSampleDTO())))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /endereco/{id} - deve deletar endereço")
    void deveDeletarEndereco() throws Exception {
        Mockito.when(enderecoService.deletarEndereco(1L)).thenReturn(true);

        mockMvc.perform(delete("/endereco/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /endereco/{id} - deve retornar 404 se não encontrar")
    void naoDeveDeletarEnderecoInexistente() throws Exception {
        Mockito.when(enderecoService.deletarEndereco(99L)).thenReturn(false);

        mockMvc.perform(delete("/endereco/99"))
                .andExpect(status().isNotFound());
    }
}