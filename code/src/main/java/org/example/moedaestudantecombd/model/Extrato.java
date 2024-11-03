package org.example.moedaestudantecombd.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Extrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String data;

    @ManyToOne
    private Professor professor;

    @ManyToOne
    private Aluno aluno;

    private String descricao;
    private Integer moedasgastas;


}
