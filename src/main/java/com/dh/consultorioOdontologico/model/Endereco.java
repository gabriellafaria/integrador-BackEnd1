package com.dh.consultorioOdontologico.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
