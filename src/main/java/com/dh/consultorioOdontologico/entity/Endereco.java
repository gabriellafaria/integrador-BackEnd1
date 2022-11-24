package com.dh.consultorioOdontologico.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Endereco {
    private int id;
    private String rua;
    private int numero;
    private String cidade;
    private String siglaEstado;

}
