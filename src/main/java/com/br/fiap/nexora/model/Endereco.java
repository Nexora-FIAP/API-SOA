package com.br.fiap.nexora.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Endereco")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "cep")
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
