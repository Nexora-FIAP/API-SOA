package com.br.fiap.nexora.model;

import com.br.fiap.nexora.dto.QuizResultadoDTO;
import com.br.fiap.nexora.enums.TipoQuiz;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity(name = "QuizResultado")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class QuizResultado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    TipoQuiz tipo;

    String resultado;

    LocalDate dataFeita;

    @ManyToOne
    @JoinColumn(name = "cpf_cliente")
    @JsonBackReference
    Cliente cliente;

    // Construtor que recebe DTO
    public QuizResultado(QuizResultadoDTO dto, Cliente cliente) {
        this.tipo = dto.tipo();
        this.resultado = dto.resultado();
        this.dataFeita = dto.dataFeita();
        this.cliente = cliente;
    }

    // Método para atualizar a partir de DTO
    public void atualizar(QuizResultadoDTO dto, Cliente cliente) {
        this.tipo = dto.tipo();
        this.resultado = dto.resultado();
        this.dataFeita = dto.dataFeita();
        this.cliente = cliente;
    }
}
