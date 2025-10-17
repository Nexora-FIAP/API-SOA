package com.br.fiap.nexora.repository;

import com.br.fiap.nexora.model.NoticiaFinanceira;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class NoticiaFinanceiraRepositoryTest {

    @Autowired
    private NoticiaFinanceiraRepository noticiaRepository;

    @Test
    void deveSalvarENaoRetornarVazioAoBuscarPorId() {
        // Arrange
        NoticiaFinanceira noticia = new NoticiaFinanceira();
        noticia.setTitulo("Alta do Dólar Impacta Mercado");
        noticia.setConteudo("O dólar apresentou alta de 2% nesta segunda-feira.");
        noticia.setFonte("Valor Econômico");
        noticia.setDataPublicacao(LocalDate.of(2024, 6, 10));

        // Act
        NoticiaFinanceira salva = noticiaRepository.save(noticia);
        Optional<NoticiaFinanceira> encontrada = noticiaRepository.findById(salva.getId());

        // Assert
        assertTrue(encontrada.isPresent());
        assertEquals("Alta do Dólar Impacta Mercado", encontrada.get().getTitulo());
        assertEquals("Valor Econômico", encontrada.get().getFonte());
    }

    @Test
    void deveListarTodasAsNoticias() {
        NoticiaFinanceira n1 = new NoticiaFinanceira();
        n1.setTitulo("Notícia 1");
        n1.setConteudo("Conteúdo 1");
        n1.setFonte("Fonte 1");
        n1.setDataPublicacao(LocalDate.of(2024, 6, 1));

        NoticiaFinanceira n2 = new NoticiaFinanceira();
        n2.setTitulo("Notícia 2");
        n2.setConteudo("Conteúdo 2");
        n2.setFonte("Fonte 2");
        n2.setDataPublicacao(LocalDate.of(2024, 6, 2));

        noticiaRepository.save(n1);
        noticiaRepository.save(n2);

        var lista = noticiaRepository.findAll();

        assertEquals(2, lista.size());
    }

    @Test
    void deveDeletarNoticiaComSucesso() {
        NoticiaFinanceira noticia = new NoticiaFinanceira();
        noticia.setTitulo("Para Deletar");
        noticia.setConteudo("Conteúdo");
        noticia.setFonte("Fonte");
        noticia.setDataPublicacao(LocalDate.now());

        NoticiaFinanceira salva = noticiaRepository.save(noticia);
        Long id = salva.getId();

        noticiaRepository.deleteById(id);

        Optional<NoticiaFinanceira> encontrada = noticiaRepository.findById(id);
        assertTrue(encontrada.isEmpty());
    }
}
