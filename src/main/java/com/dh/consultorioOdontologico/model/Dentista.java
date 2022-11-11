package com.dh.consultorioOdontologico.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Dentista {

    private String nome;
    private String sobrenome;
    private int matricula;

}
