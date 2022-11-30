package com.dh.consultorioOdontologico.entity.dto;

import com.dh.consultorioOdontologico.entity.Endereco;
import com.dh.consultorioOdontologico.service.EnderecoService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PacienteDTO {
    private String nome;
    private String sobrenome;
    private EnderecoDTO enderecoDTO;
    private String rg;
    private Timestamp dataRegistro;

   public EnderecoDTO setEndereco(Endereco endereco){
        ObjectMapper mapper = new ObjectMapper();
        return this.enderecoDTO = mapper.convertValue(endereco, EnderecoDTO.class);
   }
}
