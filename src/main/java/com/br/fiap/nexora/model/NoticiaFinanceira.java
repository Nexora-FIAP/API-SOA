package com.br.fiap.nexora.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "NoticiaFinanceira")
@Getter
@Setter
public class NoticiaFinanceira {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String titulo;

    String conteudo;

    String fonte;

    LocalDate dataPublicacao;

}
