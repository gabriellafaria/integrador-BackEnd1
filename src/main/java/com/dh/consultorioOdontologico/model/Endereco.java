package com.dh.consultorioOdontologico.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Endereco {
    private String rua;
    private int numero;
    private String cidade;
    private String siglaEstado;
}
