package com.br.fiap.nexora.model;

import com.br.fiap.nexora.enums.TipoQuiz;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity(name = "QuizResultado")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
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
