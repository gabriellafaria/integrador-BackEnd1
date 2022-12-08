package com.dh.consultorioOdontologico.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(nullable = false, length = 100)
    private String nome;
    @NotBlank
    @Column(nullable = false, length = 100)
    private String sobrenome;
    @NotBlank
    @Size(min= 6, max = 15, message = "O tamanho do campo deve ser maior que 6 e menor que 15.")
    @Column(nullable = false, unique = true)
    private String rg;
    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    private Endereco endereco;
    @Column(nullable = false)
    private Timestamp dataRegistro;
}
