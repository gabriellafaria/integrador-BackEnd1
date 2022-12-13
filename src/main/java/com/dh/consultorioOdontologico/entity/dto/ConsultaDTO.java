package com.dh.consultorioOdontologico.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsultaDTO {
    @NotBlank
    private String rgPaciente;
    @NotNull
    private int matriculaDentista;
    @NotNull
    private Timestamp dataConsulta;
    private String chave;

    public String setChave(){
        this.chave = rgPaciente+matriculaDentista+dataConsulta.toInstant();
        return this.chave;
    }
}
