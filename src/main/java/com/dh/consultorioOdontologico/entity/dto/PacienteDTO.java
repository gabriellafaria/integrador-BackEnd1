package com.dh.consultorioOdontologico.entity.dto;

import com.dh.consultorioOdontologico.entity.Endereco;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PacienteDTO {
    @NotBlank
    private String nome;
    private String sobrenome;
    @Size(min= 6, max = 15, message = "O tamanho do campo deve ser maior que 6 e menor que 15.")
    private String rg;

    private EnderecoDTO enderecoDTO;

    private Timestamp dataRegistro;

   public EnderecoDTO setEnderecoDTO(Endereco endereco){
        ObjectMapper mapper = new ObjectMapper();
        return this.enderecoDTO = mapper.convertValue(endereco, EnderecoDTO.class);
   }
}
