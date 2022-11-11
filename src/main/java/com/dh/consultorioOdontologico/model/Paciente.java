package com.dh.consultorioOdontologico.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Paciente {
    private String nome;
    private String sobrenome;
    private Endereco endereco;
    private String rg;
    private LocalDate dataRegistro;
}
