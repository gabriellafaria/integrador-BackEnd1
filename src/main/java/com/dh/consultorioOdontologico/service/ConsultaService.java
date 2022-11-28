package com.dh.consultorioOdontologico.service;

import com.dh.consultorioOdontologico.entity.Consulta;
import com.dh.consultorioOdontologico.entity.dto.ConsultaDTO;
import com.dh.consultorioOdontologico.repository.ConsultaRepository;
import com.dh.consultorioOdontologico.repository.DentistaRepository;
import com.dh.consultorioOdontologico.repository.PacienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConsultaService {
    @Autowired
    ConsultaRepository consultaRepository;
    @Autowired
    PacienteRepository pacienteRepository;
    @Autowired
    DentistaRepository dentistaRepository;

    public ResponseEntity salvar(Consulta consulta){
        try {
            if(pacienteRepository.findByRg(consulta.getRgPaciente()) == null){
                return new ResponseEntity("Não há paciente com o RG: " + consulta.getRgPaciente() + " cadastrado no sistema.", HttpStatus.NOT_FOUND);
            }
            if(dentistaRepository.findByMatricula(consulta.getMatriculaDentista()) == null){
                return new ResponseEntity("Não há dentista com a matrícula: " + consulta.getMatriculaDentista() + " cadastrado no sistema!", HttpStatus.NOT_FOUND);
            }
            if(consulta.getDataConsulta().before(Timestamp.valueOf(LocalDateTime.now()))){
                return new ResponseEntity("A data de consulta não pode ser anterior ao dia de hoje.", HttpStatus.FORBIDDEN);
            }
            if(medicoIndisponivel(consulta)){
                return new ResponseEntity("O médico está já possui uma consulta marcada neste horário!", HttpStatus.NOT_ACCEPTABLE);
            }
            Consulta consultaSalva = consultaRepository.save(consulta);
            return new ResponseEntity("Consulta do paciente de RG: " + consultaSalva.getRgPaciente() + " com dentista de matrícula: " + consulta.getMatriculaDentista() + " salva.", HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity("Erro ao cadastrar consulta.", HttpStatus.BAD_REQUEST);
        }
    }

    public List<ConsultaDTO> buscarTodasConsultas() {
        List<Consulta> listaConsulta = consultaRepository.findAll();
        List<ConsultaDTO> listaConsultaDTO = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        for (Consulta consulta : listaConsulta){
            ConsultaDTO consultaDTO = mapper.convertValue(consulta, ConsultaDTO.class);
            listaConsultaDTO.add(consultaDTO);
        }
        return listaConsultaDTO;
    }

    public ResponseEntity deletar(Consulta consulta){
        List<Consulta> listaConsulta = consultaRepository.findAll();
        for(Consulta consulta1 : listaConsulta){
            if(consulta1.getRgPaciente().equals(consulta.getRgPaciente()) && consulta1.getMatriculaDentista() == consulta.getMatriculaDentista() && consulta1.getDataConsulta().equals(consulta.getDataConsulta())){
                consultaRepository.delete(consulta1);
                return new ResponseEntity("Consulta exlcuída com sucesso!", HttpStatus.OK);
            }
        }
        return new ResponseEntity("A consulta que você deseja excluir não existe.", HttpStatus.NOT_FOUND);
    }

    public boolean medicoIndisponivel(Consulta consulta){
        List<Consulta> listaConsultas = consultaRepository.findAll();
        for(Consulta consulta1 : listaConsultas)
            if(consulta1.getMatriculaDentista() == consulta.getMatriculaDentista() && consulta1.getDataConsulta().equals(consulta.getDataConsulta()))
                return true;
        return false;
    }


//    public List<ConsultaDTO> findConsultByRg(String rgPaciente){
//        List<Consulta> listaConsulta = repository.findAllByRg(rgPaciente);
//        List<ConsultaDTO> listaConsultaDTO = new ArrayList<>();
//        ObjectMapper mapper = new ObjectMapper();
//        for(Consulta consulta : listaConsulta){
//            ConsultaDTO consultaDTO = mapper.convertValue(consulta, ConsultaDTO.class);
//            listaConsultaDTO.add(consultaDTO);
//        }
//        return listaConsultaDTO;
//    }
}
