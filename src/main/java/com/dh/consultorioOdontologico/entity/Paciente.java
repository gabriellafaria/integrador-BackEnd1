package com.dh.consultorioOdontologico.entity;

import com.dh.consultorioOdontologico.entity.dto.EnderecoDTO;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String nome;
    @Column(nullable = false, length = 100)
    private String sobrenome;
    //@Column(nullable = false)
    //private int idEndereco;
    @Column(nullable = false, unique = true)
    private String rg;
    @OneToOne( cascade = CascadeType.ALL)
    //@JoinColumn(name = "id_endereco")
    private Endereco endereco;
    @Column(nullable = false)
    private Timestamp dataRegistro;

//    public Paciente(String nome, String sobrenome, String rg, LocalDate dataRegistro, Endereco endereco) {
//        this.nome = nome;
//        this.sobrenome = sobrenome;
//        this.rg = rg;
//        this.dataRegistro = dataRegistro;
//        this.idEndereco = endereco.getId();
//    }
}
