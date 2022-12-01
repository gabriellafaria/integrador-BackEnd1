package com.dh.consultorioOdontologico.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String rua;
    @Column(nullable = false)
    private int numero;
    @Column(nullable = false, length = 50)
    private String cidade;
    @Column(nullable = false, length = 2)
    private String siglaEstado;

}
