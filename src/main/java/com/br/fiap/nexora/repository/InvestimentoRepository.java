package com.br.fiap.nexora.repository;

import com.br.fiap.nexora.model.Investimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvestimentoRepository extends JpaRepository<Investimento, Long> {
    List<Investimento> findByClienteCpf(String cpf); // buscar investimentos de um cliente
}
