package com.dh.consultorioOdontologico.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class DentistaDTO {
    @NotBlank
    private String nome;
    private String sobrenome;
    private int matricula;

    @OneToOne(cascade = CascadeType.ALL)
    UsuarioDTO usuario;
}