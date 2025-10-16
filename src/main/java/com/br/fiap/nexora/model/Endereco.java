package com.br.fiap.nexora.model;

import com.br.fiap.nexora.dto.EnderecoDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Endereco")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cep;
    private String rua;
    private int numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;

    // Construtor a partir do DTO
    public Endereco(EnderecoDTO enderecoDTO) {
        this.cep = enderecoDTO.cep();
        this.rua = enderecoDTO.rua();
        this.numero = enderecoDTO.numero();
        this.complemento = enderecoDTO.complemento();
        this.bairro = enderecoDTO.bairro();
        this.cidade = enderecoDTO.cidade();
        this.uf = enderecoDTO.uf();
    }

    // Atualiza a entidade com dados do DTO
    public void atualizar(EnderecoDTO enderecoDTO) {
        this.cep = enderecoDTO.cep();
        this.rua = enderecoDTO.rua();
        this.numero = enderecoDTO.numero();
        this.complemento = enderecoDTO.complemento();
        this.bairro = enderecoDTO.bairro();
        this.cidade = enderecoDTO.cidade();
        this.uf = enderecoDTO.uf();
    }
}
