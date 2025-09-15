package com.br.fiap.nexora.model;

import com.br.fiap.nexora.enums.TipoQuiz;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity(name = "QuizResultado")
@Getter
@Setter
public class QuizResultado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    TipoQuiz tipo;

    String resultado;

    LocalDate dataFeita;

    @ManyToOne
    @JoinColumn(name = "cpf_cliente")
    Cliente cliente;
}
