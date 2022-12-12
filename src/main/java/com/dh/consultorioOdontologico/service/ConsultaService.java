package com.dh.consultorioOdontologico.service;

import com.dh.consultorioOdontologico.entity.Consulta;
import com.dh.consultorioOdontologico.entity.Dentista;
import com.dh.consultorioOdontologico.entity.Paciente;
import com.dh.consultorioOdontologico.entity.dto.ConsultaDTO;
import com.dh.consultorioOdontologico.repository.ConsultaRepository;
import com.dh.consultorioOdontologico.repository.DentistaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.dh.consultorioOdontologico.repository.PacienteRepository;

import org.apache.log4j.Logger;
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
    static final Logger logger = Logger.getLogger(ConsultaService.class);
    @Autowired
    ConsultaRepository consultaRepository;
    @Autowired
    PacienteRepository pacienteRepository;
    @Autowired
    DentistaRepository dentistaRepository;

    public ResponseEntity salvar(ConsultaDTO consultaDTO){
        try {
            logger.info("Iniciando salvamento de uma nova consuta.");
            String rgPaciente = consultaDTO.getRgPaciente();
            int matriculaDentista = consultaDTO.getMatriculaDentista();
            Optional<Paciente> paciente = Optional.ofNullable(pacienteRepository.findByRg(rgPaciente));
            Optional<Dentista> dentista = Optional.ofNullable(dentistaRepository.findByMatricula(matriculaDentista));
            if(paciente.isEmpty()){
                logger.error("Paciente não encontrado para salvar uma nova consulta.");
                return new ResponseEntity("Não há paciente com o RG: " + consultaDTO.getRgPaciente() + " cadastrado no sistema.", HttpStatus.NOT_FOUND);
            }
            if(dentista.isEmpty()){
                logger.error("Dentista não encontrado para salvar uma nova consulta.");
                return new ResponseEntity("Não há dentista com a matrícula: " + consultaDTO.getMatriculaDentista() + " cadastrado no sistema!", HttpStatus.NOT_FOUND);
            }
            if(consultaDTO.getDataConsulta().before(Timestamp.valueOf(LocalDateTime.now()))){
                logger.error("Tentativa de salvar uma consulta anterior ao dia e horários atuais.");
                return new ResponseEntity("A data de consulta não pode ser anterior ao dia de hoje.", HttpStatus.FORBIDDEN);
            }
            if(medicoIndisponivel(consultaDTO)){
                logger.error("O médico já tem uma consulta marcada no horário solicitado.");
                return new ResponseEntity("O médico está já possui uma consulta marcada neste horário!", HttpStatus.NOT_ACCEPTABLE);
            }
            Consulta consulta = new Consulta();
            consulta.setDataConsulta(consultaDTO.getDataConsulta());
            consulta.setIdDentista(dentista.get().getId());
            consulta.setIdPaciente(paciente.get().getId());
            consultaDTO.setChave();
            consultaRepository.save(consulta);
            return new ResponseEntity("Consulta do paciente de RG: " + consultaDTO.getRgPaciente() + " com dentista de matrícula: " + consultaDTO.getMatriculaDentista() + " salva.", HttpStatus.CREATED);
        } catch (Exception e){
            e.printStackTrace();
            logger.error("Erro ao cadastrar nova consulta.");
            return new ResponseEntity("Erro ao cadastrar consulta.", HttpStatus.BAD_REQUEST);
        }
    }

    public List<ConsultaDTO> buscarTodasConsultas() {
        logger.info("Buscando todas as consultas marcadas.");
        List<Consulta> listaConsulta = consultaRepository.findAll();
        List<ConsultaDTO> listaConsultaDTO = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        for (Consulta consulta : listaConsulta){
            Optional<Paciente> paciente = pacienteRepository.findById(consulta.getIdPaciente());
            Optional<Dentista> dentista = dentistaRepository.findById(consulta.getIdDentista());
            ConsultaDTO consultaDTO = new ConsultaDTO(paciente.get().getRg(), dentista.get().getMatricula(), consulta.getDataConsulta(), consulta.getChave());
            listaConsultaDTO.add(consultaDTO);
        }
        logger.info("Trazendo a lista de consultas marcadas.");
        return listaConsultaDTO;
    }

    public ResponseEntity deletarConsulta(ConsultaDTO consultaDTO){
        try{
            logger.info("Buscando consulta para deletar.");
            System.out.println(consultaDTO.getChave());
            Optional<Consulta> consulta = consultaRepository.findByChave(consultaDTO.getChave());
            System.out.println(consulta.get().getChave());
            consultaRepository.delete(consulta.get());
            logger.info("Deletando consulta pedida.");
            return new ResponseEntity("Consulta deletada com sucesso", HttpStatus.OK);
        }catch (Exception e) {
            logger.error("Erro ao deletar consulta.");
            return new ResponseEntity("A consulta que você deseja excluir não existe!", HttpStatus.NOT_FOUND);
        }
    }

    public boolean medicoIndisponivel(ConsultaDTO consultaDTO){
        logger.info("Verificando a disponibilidade do médico para marcação de consulta.");
        List<Consulta> listaConsultas = consultaRepository.findAll();
        Optional<Dentista> dentista = Optional.ofNullable(dentistaRepository.findByMatricula(consultaDTO.getMatriculaDentista()));
        for(Consulta consulta : listaConsultas)
            if(dentista.get().getId() == consulta.getIdDentista() && consulta.getDataConsulta().equals(consulta.getDataConsulta())){
                logger.warn("Médico indisponível para consulta.");
                return true;
            }
        logger.info("Médico disponível para marcar a consulta.");
        return false;
    }

    public ResponseEntity alterarConsultaParcial(ConsultaDTO consultaDTO) {
        try{
            logger.info("Buscando consulta para realizar alteração.");
            Optional<Consulta> consulta = consultaRepository.findByChave(consultaDTO.getChave());
            Optional<Dentista> dentista = Optional.ofNullable(dentistaRepository.findByMatricula(consultaDTO.getMatriculaDentista()));
            Optional<Paciente> paciente = Optional.ofNullable(pacienteRepository.findByRg(consultaDTO.getRgPaciente()));
            if(String.valueOf(consultaDTO.getMatriculaDentista()) != null)
                logger.info("Alterando dentista da consulta.");
            consulta.get().setIdDentista(dentista.get().getId());
            if(consultaDTO.getRgPaciente() != null)
                logger.info("Alterando paciente da consulta");
            consulta.get().setIdPaciente(paciente.get().getId());
            if(consultaDTO.getDataConsulta() != null)
                logger.info("alterando data da consulta");
            consulta.get().setDataConsulta(consultaDTO.getDataConsulta());
            consultaDTO.setChave();
            consulta.get().setChave(consultaDTO.getChave());
            consultaRepository.save(consulta.get());
            logger.info("Consulta alterada com sucesso.");
            return new ResponseEntity("Consulta alterada com sucesso!", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Não é possível alterar a consulta.");
            return new ResponseEntity("Algo deu errado com a alteração da consulta!", HttpStatus.BAD_REQUEST);
        }
    }

}
