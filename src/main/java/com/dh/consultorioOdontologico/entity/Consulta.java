package com.dh.consultorioOdontologico.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column (nullable = false)
    private String rgPaciente;
    @NotBlank
    @Column(nullable = false)
    private int matriculaDentista;
    @NotBlank
    @Column(nullable = false)
    private Timestamp dataConsulta;
}
