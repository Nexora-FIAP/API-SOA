package com.br.fiap.nexora.model;

import com.br.fiap.nexora.dto.NoticiaFinanceiraDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity(name = "NoticiaFinanceira")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class NoticiaFinanceira {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String titulo;

    String conteudo;

    String fonte;

    LocalDate dataPublicacao;

    // Construtor que recebe DTO
    public NoticiaFinanceira(NoticiaFinanceiraDTO dto) {
        this.titulo = dto.titulo();
        this.conteudo = dto.conteudo();
        this.fonte = dto.fonte();
        this.dataPublicacao = dto.dataPublicacao();
    }

    // Método para atualizar a partir de DTO
    public void atualizar(NoticiaFinanceiraDTO dto) {
        this.titulo = dto.titulo();
        this.conteudo = dto.conteudo();
        this.fonte = dto.fonte();
        this.dataPublicacao = dto.dataPublicacao();
    }
}
