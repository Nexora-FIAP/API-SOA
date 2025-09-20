package com.br.fiap.nexora.repository;

import com.br.fiap.nexora.model.TransacaoBancaria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransacaoBancariaRepository extends JpaRepository<TransacaoBancaria, Long> {
    List<TransacaoBancaria> findByContaBancariaId(Long contaId); // buscar transações de uma conta
}
