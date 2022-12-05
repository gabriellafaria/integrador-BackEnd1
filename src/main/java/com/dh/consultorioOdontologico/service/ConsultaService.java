package com.dh.consultorioOdontologico.service;

import com.dh.consultorioOdontologico.entity.Consulta;
import com.dh.consultorioOdontologico.entity.Dentista;
import com.dh.consultorioOdontologico.entity.Paciente;
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
import java.util.Optional;

@Service
public class ConsultaService {
    static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ConsultaService.class);
    @Autowired
    ConsultaRepository consultaRepository;
    @Autowired
    PacienteRepository pacienteRepository;
    @Autowired
    DentistaRepository dentistaRepository;

    public ResponseEntity salvar(ConsultaDTO consultaDTO){
        try {
            String rgPaciente = consultaDTO.getRgPaciente();
            int matriculaDentista = consultaDTO.getMatriculaDentista();
            Optional<Paciente> paciente = Optional.ofNullable(pacienteRepository.findByRg(rgPaciente));
            Optional<Dentista> dentista = Optional.ofNullable(dentistaRepository.findByMatricula(matriculaDentista));
            if(paciente.isEmpty())
                return new ResponseEntity("Não há paciente com o RG: " + consultaDTO.getRgPaciente() + " cadastrado no sistema.", HttpStatus.NOT_FOUND);
            if(dentista.isEmpty())
                return new ResponseEntity("Não há dentista com a matrícula: " + consultaDTO.getMatriculaDentista() + " cadastrado no sistema!", HttpStatus.NOT_FOUND);
            if(consultaDTO.getDataConsulta().before(Timestamp.valueOf(LocalDateTime.now())))
                return new ResponseEntity("A data de consulta não pode ser anterior ao dia de hoje.", HttpStatus.FORBIDDEN);
            if(medicoIndisponivel(consultaDTO))
                return new ResponseEntity("O médico está já possui uma consulta marcada neste horário!", HttpStatus.NOT_ACCEPTABLE);

            Consulta consulta = new Consulta();
            consulta.setDataConsulta(consultaDTO.getDataConsulta());
            consulta.setIdDentista(dentista.get().getId());
            consulta.setIdPaciente(paciente.get().getId());
            consultaRepository.save(consulta);
            return new ResponseEntity("Consulta do paciente de RG: " + consultaDTO.getRgPaciente() + " com dentista de matrícula: " + consultaDTO.getMatriculaDentista() + " salva.", HttpStatus.CREATED);
        } catch (Exception e){
            e.printStackTrace();
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

    public ResponseEntity deletar(ConsultaDTO consultaDTO){
        try{
            Paciente paciente = pacienteRepository.findByRg(consultaDTO.getRgPaciente());
            Dentista dentista = dentistaRepository.findByMatricula(consultaDTO.getMatriculaDentista());
            ObjectMapper mapper = new ObjectMapper();
            Consulta consulta = mapper.convertValue(consultaDTO, Consulta.class);
            consulta.setIdPaciente(paciente.getId());
            consulta.setIdDentista(dentista.getId());
            consultaRepository.delete(consulta);
            return new ResponseEntity("Consulta deletada com sucesso", HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity("Algo de errado ocorreu com a exclusao!", HttpStatus.UNAUTHORIZED);
        }
    }


//    public ResponseEntity deletar(Consulta consulta){
//        List<Consulta> listaConsulta = consultaRepository.findAll();
//        for(Consulta consulta1 : listaConsulta){
//            if(consulta1.getRgPaciente().equals(consulta.getRgPaciente()) && consulta1.getMatriculaDentista() == consulta.getMatriculaDentista() && consulta1.getDataConsulta().equals(consulta.getDataConsulta())){
//                consultaRepository.delete(consulta1);
//                return new ResponseEntity("Consulta exlcuída com sucesso!", HttpStatus.OK);
//            }
//        }
//        return new ResponseEntity("A consulta que você deseja excluir não existe.", HttpStatus.NOT_FOUND);
//    }

    public boolean medicoIndisponivel(ConsultaDTO consultaDTO){
        List<Consulta> listaConsultas = consultaRepository.findAll();
        ObjectMapper mapper = new ObjectMapper();
        Consulta consulta = mapper.convertValue(consultaDTO, Consulta.class);
        for(Consulta consulta1 : listaConsultas)
            if(consulta1.getIdDentista() == consulta.getIdDentista() && consulta1.getDataConsulta().equals(consulta.getDataConsulta()))
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
