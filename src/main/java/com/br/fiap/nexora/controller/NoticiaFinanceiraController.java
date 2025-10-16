package com.br.fiap.nexora.controller;

import com.br.fiap.nexora.dto.NoticiaFinanceiraDTO;
import com.br.fiap.nexora.model.NoticiaFinanceira;
import com.br.fiap.nexora.service.NoticiaFinanceiraService;
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
@RequestMapping("/noticia")
public class NoticiaFinanceiraController {

    @Autowired
    private NoticiaFinanceiraService noticiaService;

    @Operation(summary = "Cadastra uma nova notícia financeira", description = "Cria uma nova notícia relacionada ao mercado financeiro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Notícia cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<NoticiaFinanceira> cadastrarNoticia(@RequestBody @Valid NoticiaFinanceiraDTO dto) {
        NoticiaFinanceira noticia = noticiaService.criarNoticia(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(noticia);
    }

    @Operation(summary = "Busca notícia por ID", description = "Retorna os dados de uma notícia financeira específica pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notícia encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Notícia não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<NoticiaFinanceira> buscarNoticia(@PathVariable Long id) {
        return noticiaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Lista todas as notícias financeiras", description = "Retorna todas as notícias financeiras cadastradas no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de notícias retornada com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhuma notícia cadastrada")
    })
    @GetMapping
    public ResponseEntity<List<NoticiaFinanceira>> buscarTodasNoticias() {
        List<NoticiaFinanceira> noticias = noticiaService.buscarTodas();
        if (noticias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(noticias);
    }

    @Operation(summary = "Atualiza uma notícia financeira", description = "Atualiza os dados de uma notícia existente pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notícia atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Notícia não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<NoticiaFinanceira> atualizarNoticia(@PathVariable Long id, @RequestBody @Valid NoticiaFinanceiraDTO dto) {
        return noticiaService.atualizarNoticia(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deleta uma notícia financeira", description = "Remove uma notícia financeira do sistema pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Notícia deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Notícia não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarNoticia(@PathVariable Long id) {
        boolean deletado = noticiaService.deletarNoticia(id);
        if (!deletado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}