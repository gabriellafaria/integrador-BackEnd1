package com.dh.consultorioOdontologico.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @NotBlank
    private String sobrenome;
    @NotBlank
    @Size(min= 6, max = 15, message = "O tamanho do campo deve ser maior que 6 e menor que 15.")
    private String rg;
    @NotNull
    private EnderecoDTO endereco;

    private Timestamp dataRegistro;

   /*public EnderecoDTO setEnderecoDTO(Endereco endereco){
        ObjectMapper mapper = new ObjectMapper();
        return this.endereco = mapper.convertValue(endereco, EnderecoDTO.class);
   }*/

    @OneToOne(cascade = CascadeType.ALL)
    UsuarioDTO usuario;
}
