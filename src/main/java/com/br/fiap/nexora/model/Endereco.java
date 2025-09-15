package com.br.fiap.nexora.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "Endereco")
public class Endereco {
    @Id
    int cep;
    String rua;
    int numero;
    String complemento;
    String bairro;
    String cidade;
    String uf;
}
