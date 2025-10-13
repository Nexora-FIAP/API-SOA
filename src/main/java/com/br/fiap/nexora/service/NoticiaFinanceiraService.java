package com.br.fiap.nexora.service;

import com.br.fiap.nexora.dto.NoticiaFinanceiraDTO;
import com.br.fiap.nexora.model.NoticiaFinanceira;
import com.br.fiap.nexora.repository.NoticiaFinanceiraRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoticiaFinanceiraService {

    @Autowired
    private NoticiaFinanceiraRepository noticiaRepository;

    @Transactional
    public NoticiaFinanceira criarNoticia(NoticiaFinanceiraDTO dto) {
        NoticiaFinanceira noticia = new NoticiaFinanceira(dto);
        return noticiaRepository.save(noticia);
    }

    public Optional<NoticiaFinanceira> buscarPorId(Long id) {
        return noticiaRepository.findById(id);
    }

    public List<NoticiaFinanceira> buscarTodas() {
        return noticiaRepository.findAll();
    }

    @Transactional
    public Optional<NoticiaFinanceira> atualizarNoticia(Long id, NoticiaFinanceiraDTO dto) {
        return noticiaRepository.findById(id).map(noticia -> {
            noticia.atualizar(dto);
            return noticiaRepository.save(noticia);
        });
    }

    @Transactional
    public boolean deletarNoticia(Long id) {
        if (!noticiaRepository.existsById(id)) {
            return false;
        }
        noticiaRepository.deleteById(id);
        return true;
    }
}