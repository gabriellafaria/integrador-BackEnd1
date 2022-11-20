package com.dh.consultorioOdontologico.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Consulta {
    private int id;
    private int idPaciente;
    private int idDentista;
    private LocalDateTime dataConsulta;
    //private Ti

}
