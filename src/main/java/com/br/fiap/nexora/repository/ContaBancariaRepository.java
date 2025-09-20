package com.br.fiap.nexora.repository;

import com.br.fiap.nexora.model.ContaBancaria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContaBancariaRepository extends JpaRepository<ContaBancaria, Long> {
    List<ContaBancaria> findByClienteCpf(String cpf); // buscar contas de um cliente
    boolean existsByNumeroContaAndClienteCpf(int numeroConta, String cpf);

}
