package com.br.fiap.nexora.repository;

import com.br.fiap.nexora.model.QuizResultado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizResultadoRepository extends JpaRepository<QuizResultado, Long> {
    List<QuizResultado> findByClienteCpf(String cpf); // buscar quizzes de um cliente
}
