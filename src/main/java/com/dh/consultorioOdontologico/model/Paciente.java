package com.dh.consultorioOdontologico.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Paciente {
    private int id;
    private String nome;
    private String sobrenome;
    private int idEndereco;
    private String rg;
    private LocalDate dataRegistro;

    public Paciente(String nome, String sobrenome, String rg, LocalDate dataRegistro, Endereco endereco) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.rg = rg;
        this.dataRegistro = dataRegistro;
        this.idEndereco = endereco.getId();
    }


}
