package com.dh.consultorioOdontologico.entity.dto;


import com.dh.consultorioOdontologico.entity.Endereco;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;


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

//    public EnderecoDTO(Endereco endereco) {
//    }
    @Autowired
    private EnderecoDTO enderecoDTO;

    public EnderecoDTO setEndereco(Endereco endereco){
        ObjectMapper mapper = new ObjectMapper();
        return this.enderecoDTO = mapper.convertValue(endereco, EnderecoDTO.class);
    }
}
