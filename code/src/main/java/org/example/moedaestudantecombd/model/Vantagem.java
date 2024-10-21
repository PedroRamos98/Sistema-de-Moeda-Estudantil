package org.example.moedaestudantecombd.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Vantagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;
    private double custo;

    @Lob
    private String fotoProduto;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

}
