package com.dh.consultorioOdontologico.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PacienteDTO {
    private String nome;
    private String sobrenome;
    private int idEndereco;
    private String rg;
    private Timestamp dataRegistro;
}
