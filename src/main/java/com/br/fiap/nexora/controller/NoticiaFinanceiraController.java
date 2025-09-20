package com.br.fiap.nexora.controller;

import com.br.fiap.nexora.dto.NoticiaFinanceiraDTO;
import com.br.fiap.nexora.model.NoticiaFinanceira;
import com.br.fiap.nexora.repository.NoticiaFinanceiraRepository;
import jakarta.transaction.Transactional;
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
    private NoticiaFinanceiraRepository noticiaRepository;

    // POST - criar notícia
    @PostMapping
    @Transactional
    public ResponseEntity<NoticiaFinanceira> cadastrarNoticia(@RequestBody @Valid NoticiaFinanceiraDTO dto) {
        NoticiaFinanceira noticia = new NoticiaFinanceira(dto);
        noticiaRepository.save(noticia);
        return ResponseEntity.status(HttpStatus.CREATED).body(noticia);
    }

    // GET por ID
    @GetMapping("/{id}")
    public ResponseEntity<NoticiaFinanceira> buscarNoticia(@PathVariable Long id) {
        return noticiaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET - todas as notícias
    @GetMapping
    public ResponseEntity<List<NoticiaFinanceira>> buscarTodasNoticias() {
        List<NoticiaFinanceira> noticias = noticiaRepository.findAll();
        if (noticias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(noticias);
    }

    // PUT - atualizar notícia
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<NoticiaFinanceira> atualizarNoticia(@PathVariable Long id, @RequestBody @Valid NoticiaFinanceiraDTO dto) {
        return noticiaRepository.findById(id)
                .map(noticia -> {
                    noticia.atualizar(dto);
                    noticiaRepository.save(noticia);
                    return ResponseEntity.ok(noticia);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE - remover notícia
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletarNoticia(@PathVariable Long id) {
        if (!noticiaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        noticiaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
