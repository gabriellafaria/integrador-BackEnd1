package com.dh.consultorioOdontologico.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dentista {

    private int id;
    private String nome;
    private String sobrenome;
    private int matricula;

}
