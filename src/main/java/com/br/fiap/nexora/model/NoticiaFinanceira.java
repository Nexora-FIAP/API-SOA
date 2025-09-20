package com.br.fiap.nexora.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "NoticiaFinanceira")
@Getter
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
