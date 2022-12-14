package com.dh.consultorioOdontologico.service;

import com.dh.consultorioOdontologico.entity.Consulta;
import com.dh.consultorioOdontologico.entity.Dentista;
import com.dh.consultorioOdontologico.entity.Paciente;
import com.dh.consultorioOdontologico.entity.dto.ConsultaDTO;
import com.dh.consultorioOdontologico.exception.ResourceNotFoundException;
import com.dh.consultorioOdontologico.repository.ConsultaRepository;
import com.dh.consultorioOdontologico.repository.DentistaRepository;
import com.dh.consultorioOdontologico.repository.PacienteRepository;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
            consulta.setChave(consultaDTO.setChave());
            consulta.setDataConsulta(Timestamp.from(consultaDTO.getDataConsulta().toInstant().plus(3, ChronoUnit.HOURS)));
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
        for (Consulta consulta : listaConsulta){
            Optional<Paciente> paciente = pacienteRepository.findById(consulta.getIdPaciente());
            Optional<Dentista> dentista = dentistaRepository.findById(consulta.getIdDentista());
            ConsultaDTO consultaDTO = new ConsultaDTO(paciente.get().getRg(), dentista.get().getMatricula(), Timestamp.from(consulta.getDataConsulta().toInstant().plus(-3,ChronoUnit.HOURS)), consulta.getChave());
            listaConsultaDTO.add(consultaDTO);
        }
        logger.info("Trazendo a lista de consultas marcadas.");
        System.out.println(listaConsultaDTO);
        return listaConsultaDTO;
    }

    public ResponseEntity deletarConsulta(ConsultaDTO consultaDTO) {
        try{
            logger.info("Buscando consulta para deletar.");
            Optional<Consulta> consulta = consultaRepository.findByChave(consultaDTO.getChave());
            consultaRepository.delete(consulta.get());
            logger.info("Deletando consulta pedida.");
            return new ResponseEntity("Consulta deletada com sucesso", HttpStatus.OK);
        }catch (RuntimeException e) {
            logger.error("Erro ao deletar consulta.");
            return new ResponseEntity("A consulta que você deseja excluir não existe!", HttpStatus.NOT_FOUND);
        }
    }

    public boolean medicoIndisponivel(ConsultaDTO consultaDTO){
        logger.info("Verificando a disponibilidade do médico para marcação de consulta.");
        List<Consulta> listaConsultas = consultaRepository.findAll();
        Optional<Dentista> dentista = Optional.ofNullable(dentistaRepository.findByMatricula(consultaDTO.getMatriculaDentista()));
        for(Consulta consulta : listaConsultas)
            if(dentista.get().getId() == consulta.getIdDentista() && consulta.getDataConsulta().equals(consultaDTO.getDataConsulta())){
                logger.warn("Médico indisponível para consulta.");
                return true;
            }
        logger.info("Médico disponível para marcar a consulta.");
        return false;
    }

    public ResponseEntity alterarConsulta(ConsultaDTO consultaDTO) {
        try{
            logger.info("Buscando consulta para realizar alteração.");
            Optional<Consulta> consulta = consultaRepository.findByChave(consultaDTO.getChave());
            if(consulta.isEmpty())
                throw new ResourceNotFoundException("Consulta não encontrada");
            Optional<Dentista> dentista = Optional.ofNullable(dentistaRepository.findByMatricula(consultaDTO.getMatriculaDentista()));
            if(dentista.isEmpty())
                throw new ResourceNotFoundException("Dentista com matrícula " + consultaDTO.getMatriculaDentista() + " não encontrado para salvar a nova consulta");
            Optional<Paciente> paciente = Optional.ofNullable(pacienteRepository.findByRg(consultaDTO.getRgPaciente()));
            if(paciente.isEmpty())
                throw new ResourceNotFoundException("Paciente com rg " + consultaDTO.getRgPaciente() + " nao encontrado para salvar a nova consulta");
            if(dentista.get().getId() != consulta.get().getIdDentista()) {
                logger.info("Alterando dentista da consulta");
                consulta.get().setIdDentista(dentista.get().getId());
            }
            if(paciente.get().getId() != consulta.get().getIdPaciente()) {
                logger.info("Alterando paciente da consulta");
                consulta.get().setIdPaciente(paciente.get().getId());
            }
            if(consultaDTO.getDataConsulta() != consulta.get().getDataConsulta()){
                logger.info("alterando data da consulta");
                consulta.get().setDataConsulta(Timestamp.from(consultaDTO.getDataConsulta().toInstant().plus(3, ChronoUnit.HOURS)));
            }
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

    public List<ConsultaDTO> buscarConsultasPaciente(String rgPaciente) throws ResourceNotFoundException {
        Optional<Paciente> paciente = Optional.ofNullable(pacienteRepository.findByRg(rgPaciente));
        if(paciente.isEmpty())
            throw new ResourceNotFoundException("Não há paciente cadastrado com o rg: " + rgPaciente);
        List<Consulta> consultaList = consultaRepository.findAllByIdPaciente(paciente.get().getId());
        List<ConsultaDTO> consultaDTOList = new ArrayList();
        for(Consulta consulta : consultaList){
            Optional<Dentista> dentista = dentistaRepository.findById(consulta.getIdDentista());
            ConsultaDTO consultaDTO = new ConsultaDTO(paciente.get().getRg(), dentista.get().getMatricula(), Timestamp.from(consulta.getDataConsulta().toInstant().plus(-3,ChronoUnit.HOURS)),consulta.getChave());
            consultaDTOList.add(consultaDTO);
        }
        return consultaDTOList;
    }

    public List<ConsultaDTO> buscarConsultasDentista(int matriculaDentista) throws ResourceNotFoundException {
        Optional<Dentista> dentista = Optional.ofNullable(dentistaRepository.findByMatricula(matriculaDentista));
        if(dentista.isEmpty())
            throw new ResourceNotFoundException("Não há dentista cadastrado no sistema com a matrícula: " + matriculaDentista);
        List<Consulta> consultaList = consultaRepository.findAllByIdDentista(dentista.get().getId());
        List<ConsultaDTO> consultaDTOList = new ArrayList();
        for(Consulta consulta : consultaList){
            Optional<Paciente> paciente = pacienteRepository.findById(consulta.getIdPaciente());
            ConsultaDTO consultaDTO = new ConsultaDTO(paciente.get().getRg(),dentista.get().getMatricula(),Timestamp.from(consulta.getDataConsulta().toInstant().plus(-3,ChronoUnit.HOURS)),consulta.getChave());
            consultaDTOList.add(consultaDTO);
        }
        return consultaDTOList;
    }
}
