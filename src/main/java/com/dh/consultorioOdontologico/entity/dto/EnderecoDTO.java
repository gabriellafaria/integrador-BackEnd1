package com.dh.consultorioOdontologico.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnderecoDTO {
    private Long id;
    private String rua;
    private int numero;
    private String cidade;
    private String estado;

}
