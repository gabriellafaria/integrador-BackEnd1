package com.dh.consultorioOdontologico.service;

import com.dh.consultorioOdontologico.entity.Consulta;
import com.dh.consultorioOdontologico.entity.dto.ConsultaDTO;
import com.dh.consultorioOdontologico.repository.ConsultaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConsultaService {
    @Autowired
    ConsultaRepository repository;

    public ResponseEntity salvar(Consulta consulta){
        try {
            Consulta consultaSalva = repository.save(consulta);
            return new ResponseEntity("Consulta do RG: " + consulta.getRgPaciente() + "e matricula: " + consulta.getMatriculaDentista() + " salvo.", HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity("Erro ao cadastrar consulta.", HttpStatus.BAD_REQUEST);
        }
    }

    public List<ConsultaDTO> buscar() {
        List<Consulta> listaConsulta = repository.findAll();
        List<ConsultaDTO> listaConsultaDTO = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        for (Consulta consulta : listaConsulta){
            ConsultaDTO consultaDTO = mapper.convertValue(consulta, ConsultaDTO.class);
            listaConsultaDTO.add(consultaDTO);
        }
        return listaConsultaDTO;
    }
}
