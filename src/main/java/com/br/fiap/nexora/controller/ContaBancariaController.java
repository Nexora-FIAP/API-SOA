package com.br.fiap.nexora.controller;

import com.br.fiap.nexora.dto.ContaBancariaDTO;
import com.br.fiap.nexora.model.ContaBancaria;
import com.br.fiap.nexora.service.ContaBancariaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conta")
public class ContaBancariaController {

    @Autowired
    private ContaBancariaService contaService;

    @Operation(summary = "Cadastra uma nova conta bancária", description = "Cria uma conta bancária vinculada a um cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Conta criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "Conta já existente ou conflito de dados")
    })
    @PostMapping
    public ResponseEntity<ContaBancaria> cadastrarConta(@RequestBody @Valid ContaBancariaDTO contaDTO) {
        try {
            ContaBancaria conta = contaService.criarConta(contaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(conta);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @Operation(summary = "Atualiza uma conta bancária", description = "Atualiza os dados de uma conta bancária existente pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conta atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Conta não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ContaBancaria> atualizarConta(@PathVariable Long id, @RequestBody @Valid ContaBancariaDTO contaDTO) {
        return contaService.atualizarConta(id, contaDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Busca uma conta bancária por ID", description = "Retorna os dados de uma conta bancária específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conta encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Conta não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ContaBancaria> buscarConta(@PathVariable Long id) {
        return contaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Lista todas as contas bancárias", description = "Retorna todas as contas bancárias cadastradas no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de contas retornada com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhuma conta cadastrada")
    })
    @GetMapping
    public ResponseEntity<List<ContaBancaria>> buscarTodasContas() {
        List<ContaBancaria> contas = contaService.buscarTodas();
        if (contas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(contas);
    }

    @Operation(summary = "Busca contas bancárias por CPF do cliente", description = "Retorna todas as contas vinculadas ao CPF informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contas encontradas com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhuma conta encontrada para o CPF informado")
    })
    @GetMapping("/cliente/{cpf}")
    public ResponseEntity<List<ContaBancaria>> buscarContasPorCliente(@PathVariable String cpf) {
        List<ContaBancaria> contas = contaService.buscarPorCpfCliente(cpf);
        if (contas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(contas);
    }

    @Operation(summary = "Deleta uma conta bancária", description = "Remove uma conta bancária do sistema pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Conta deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Conta não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarConta(@PathVariable Long id) {
        boolean deletado = contaService.deletarConta(id);
        if (!deletado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
