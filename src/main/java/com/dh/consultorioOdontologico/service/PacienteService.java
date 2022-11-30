package com.dh.consultorioOdontologico.service;

import com.dh.consultorioOdontologico.entity.Endereco;
import com.dh.consultorioOdontologico.entity.Paciente;
import com.dh.consultorioOdontologico.entity.dto.EnderecoDTO;
import com.dh.consultorioOdontologico.entity.dto.PacienteDTO;
import com.dh.consultorioOdontologico.repository.PacienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {
    @Autowired
    PacienteRepository pacienteRepository;

    static final Logger logger = Logger.getLogger(PacienteService.class);

    public List<PacienteDTO> buscar(){
        logger.info("Iniciando operação para buscar os pacientes.");
        List<Paciente> pacienteList = pacienteRepository.findAll();
        List<PacienteDTO> pacienteDTOList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        for(Paciente paciente: pacienteList){
            pacienteDTOList.add(mapper.convertValue(paciente, PacienteDTO.class));
        }
        logger.info("Exibindo os pacientes.");
        return pacienteDTOList;
    }

    public ResponseEntity salvar(Paciente paciente){
        try{
            logger.info("Iniciando operação para salvar o paciente.");
            paciente.setDataRegistro(Timestamp.from(Instant.now()));
            Paciente pacienteSalvo = pacienteRepository.save(paciente);
            logger.info("Paciente " + pacienteSalvo.getNome() + "salvo com sucesso.");
            return new ResponseEntity("Paciente " + pacienteSalvo.getNome() + " criado com sucesso!", HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Erro ao salvar o paciente.");
            return new ResponseEntity<>("Erro ao cadastrar o paciente", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity deletar(String  rg){
        logger.info("Iniciada operação para deletar o paciente com o Rg " + rg);
       Optional<Paciente> paciente = Optional.ofNullable(pacienteRepository.findByRg(rg));
       if(paciente.isEmpty()) {
           logger.error("Erro ao excluir o paciente. Não foi localizado nenhum cadastro com o rg " + rg);
           return new ResponseEntity<>("Id do paciente não existe!", HttpStatus.BAD_REQUEST);
       }
       pacienteRepository.deleteById(paciente.get().getId());
        logger.info("Paciente deletado!");
        return new ResponseEntity("Paciente delatado com sucesso!", HttpStatus.OK);
    }

    public ResponseEntity buscarPorRg(String rg) {
        logger.info("iniciando operação para buscar o paciente com o rg " + rg);
        ObjectMapper mapper = new ObjectMapper();
        Optional<Paciente> paciente = Optional.ofNullable(pacienteRepository.findByRg(rg));
        if(paciente.isEmpty()) {
            logger.error("Paciente com o rg " + rg + " não localizado.");
            return new ResponseEntity("Paciente com o rg " + rg + " não encontrado", HttpStatus.BAD_REQUEST);
        }
        PacienteDTO pacienteDTO = mapper.convertValue(paciente.get(), PacienteDTO.class);
        logger.info("Paciente com o rg " + rg + " localizado com sucesso.");
        return new ResponseEntity(pacienteDTO, HttpStatus.OK);
    }
}