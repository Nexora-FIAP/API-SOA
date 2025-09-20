package com.br.fiap.nexora.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity(name = "NoticiaFinanceira")
@Getter
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

}
