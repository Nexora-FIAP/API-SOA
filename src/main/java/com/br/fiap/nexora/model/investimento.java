package com.br.fiap.nexora.model;

import com.br.fiap.nexora.enums.TipoInvestimento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "Investimento")
@Getter
@Setter
public class investimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    TipoInvestimento tipo;

}
