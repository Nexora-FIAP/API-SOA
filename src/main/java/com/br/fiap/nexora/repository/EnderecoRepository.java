package com.br.fiap.nexora.repository;

import com.br.fiap.nexora.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    List<Endereco> findByCep(String cep);
}
