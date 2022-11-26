package com.dh.consultorioOdontologico.dto;


import com.dh.consultorioOdontologico.entity.Endereco;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnderecoDTO {
    private String rua;
    private int numero;
    private String cidade;
    private String SiglaEstado;

    public EnderecoDTO(Endereco endereco) {
    }
}
