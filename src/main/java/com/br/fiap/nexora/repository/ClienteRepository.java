package com.br.fiap.nexora.repository;

import com.br.fiap.nexora.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
