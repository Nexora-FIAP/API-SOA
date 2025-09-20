package com.br.fiap.nexora.model;

import com.br.fiap.nexora.dto.EnderecoDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity(name = "Endereco")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "cep")
public class Endereco {
    @Id
    String cep;
    String rua;
    int numero;
    String complemento;
    String bairro;
    String cidade;
    String uf;

    public Endereco(EnderecoDTO enderecoDTO) {
        this.cep = enderecoDTO.cep();
        this.rua = enderecoDTO.rua();
        this.numero = enderecoDTO.numero();
        this.complemento = enderecoDTO.complemento();
        this.bairro = enderecoDTO.bairro();
        this.cidade = enderecoDTO.cidade();
        this.uf = enderecoDTO.uf();
    }
}
