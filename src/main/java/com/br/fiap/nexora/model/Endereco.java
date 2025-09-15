package com.br.fiap.nexora.model;

import jakarta.persistence.Id;

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
