package com.dh.consultorioOdontologico.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Consulta {
    private int id;
    private Paciente paciente;
    private Dentista dentista;
    private LocalDate dataConsulta;
}
