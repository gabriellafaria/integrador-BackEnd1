package com.dh.consultorioOdontologico.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank

    @Column(nullable = false, length = 100)
    private String nome;
    @Column(nullable = false, length = 100)
    private String sobrenome;
    @Size(min= 6, max = 15, message = "O tamanho do campo deve ser maior que 6 e menor que 15.")
    @Column(nullable = false, unique = true)
    private String rg;
    @OneToOne(cascade = CascadeType.ALL)
    private Endereco endereco;
    @Column(nullable = false)
    private Timestamp dataRegistro;

   /* public Paciente(String nome, String sobrenome, String rg, Endereco endereco, Timestamp dataRegistro) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.rg = rg;
        this.endereco = endereco;
        this.dataRegistro = dataRegistro;
    }*/
}
