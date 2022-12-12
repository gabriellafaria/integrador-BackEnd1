package com.dh.consultorioOdontologico.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsultaDTO {
    private String rgPaciente;
    private int matriculaDentista;
    private Timestamp dataConsulta;
    private String chave;

    public String setChave(){
        this.chave = rgPaciente+matriculaDentista+dataConsulta;
        return this.chave;
    }
}
