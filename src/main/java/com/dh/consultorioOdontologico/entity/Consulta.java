package com.dh.consultorioOdontologico.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long idPaciente;
    @Column(nullable = false)
    private Long idDentista;
    @Column(nullable = false)
    private Timestamp dataConsulta;
}
