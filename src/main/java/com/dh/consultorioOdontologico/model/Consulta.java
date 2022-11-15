package com.dh.consultorioOdontologico.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Consulta {
    private int id;
    private int idPaciente;
    private int idDentista;
    private LocalDateTime dataConsulta;
    //private Ti

    public Consulta(Paciente paciente, Dentista dentista, LocalDateTime dataConsulta) {
        this.idPaciente = paciente.getId();
        this.idDentista = dentista.getId();
        this.dataConsulta = dataConsulta;
    }
}
