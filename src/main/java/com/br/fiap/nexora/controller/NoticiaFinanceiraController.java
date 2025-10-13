package com.br.fiap.nexora.controller;

import com.br.fiap.nexora.dto.NoticiaFinanceiraDTO;
import com.br.fiap.nexora.model.NoticiaFinanceira;
import com.br.fiap.nexora.service.NoticiaFinanceiraService;
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

    @PostMapping
    public ResponseEntity<NoticiaFinanceira> cadastrarNoticia(@RequestBody @Valid NoticiaFinanceiraDTO dto) {
        NoticiaFinanceira noticia = noticiaService.criarNoticia(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(noticia);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoticiaFinanceira> buscarNoticia(@PathVariable Long id) {
        return noticiaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<NoticiaFinanceira>> buscarTodasNoticias() {
        List<NoticiaFinanceira> noticias = noticiaService.buscarTodas();
        if (noticias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(noticias);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoticiaFinanceira> atualizarNoticia(@PathVariable Long id, @RequestBody @Valid NoticiaFinanceiraDTO dto) {
        return noticiaService.atualizarNoticia(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarNoticia(@PathVariable Long id) {
        boolean deletado = noticiaService.deletarNoticia(id);
        if (!deletado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}