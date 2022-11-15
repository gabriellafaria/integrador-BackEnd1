package com.dh.consultorioOdontologico.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class Endereco {
    private int id;
    private String rua;
    private int numero;
    private String cidade;
    private String siglaEstado;

    public Endereco(String rua, int numero, String cidade, String siglaEstado) {
        this.rua = rua;
        this.numero = numero;
        this.cidade = cidade;
        this.siglaEstado = siglaEstado;
    }
}
