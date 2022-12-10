package com.dh.consultorioOdontologico.service;

import com.dh.consultorioOdontologico.entity.Endereco;
import com.dh.consultorioOdontologico.entity.Paciente;
import com.dh.consultorioOdontologico.entity.dto.PacienteDTO;
import com.dh.consultorioOdontologico.exception.Exceptions;
import com.dh.consultorioOdontologico.exception.ResourceNotFoundException;
import com.dh.consultorioOdontologico.repository.PacienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
        logger.info("Exibindo os pacientes:");
        for(Paciente paciente: pacienteList){
            pacienteDTOList.add(mapper.convertValue(paciente, PacienteDTO.class));
            logger.info("Paciente: " + paciente.getNome() + " " + paciente.getSobrenome() + ", rg: " +  paciente.getRg());
        }
        return pacienteDTOList;
    }

    public Paciente salvar(PacienteDTO pacienteDTO){
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("Arrg");
        logger.info("Iniciando operação para salvar o paciente.");
        Endereco endereco = mapper.convertValue(pacienteDTO.getEndereco(), Endereco.class);
        Paciente paciente = mapper.convertValue(pacienteDTO, Paciente.class);
        paciente.setDataRegistro(Timestamp.from(Instant.now()));
        paciente.setEndereco(endereco);
        Paciente pacienteSalvo = pacienteRepository.save(paciente);
        logger.info("Paciente " + pacienteSalvo.getNome() + " salvo com sucesso.");
        return paciente;
    }

    public Optional<Paciente> deletar(String  rg) throws ResourceNotFoundException {
        logger.info("Iniciada operação para deletar o paciente com o Rg " + rg);
       Optional<Paciente> paciente = Optional.ofNullable(pacienteRepository.findByRg(rg));
       if(paciente.isEmpty()) {
           logger.error("Erro ao excluir o paciente. Não foi localizado nenhum cadastro com o rg " + rg);
            throw new ResourceNotFoundException("Não foi localizado o paciente com o Rg informado");
       }
       pacienteRepository.deleteById(paciente.get().getId());
        logger.info("Paciente deletado!");
        return paciente;
    }

    public PacienteDTO buscarPorRg(String rg) throws ResourceNotFoundException {
        logger.info("iniciando operação para buscar o paciente com o rg " + rg);
        ObjectMapper mapper = new ObjectMapper();
        Optional<Paciente> paciente = Optional.ofNullable(pacienteRepository.findByRg(rg));
        if(paciente.isEmpty()) {
            logger.error("Paciente com o rg " + rg + " não localizado.");
            throw new ResourceNotFoundException("Paciente com o rg " + rg + " não localizado.");
        }
        PacienteDTO pacienteDTO = mapper.convertValue(paciente.get(), PacienteDTO.class);
        logger.info("Paciente com o rg " + rg + " localizado com sucesso.");
        return pacienteDTO;
    }

    public PacienteDTO alterarParcialmente(PacienteDTO pacienteDTO) throws Exceptions {
        ObjectMapper mapper = new ObjectMapper();
        logger.info("Iniciando operação para alterar parcialmente o paciente.");
        Optional<Paciente> pacienteOp = Optional.ofNullable(pacienteRepository.findByRg(pacienteDTO.getRg()));
        if(pacienteOp.isEmpty()) {
            logger.error("Paciente com o rg " + pacienteDTO.getRg() + " não encontrado.");
            throw new Exceptions("Paciente com o rg " + pacienteDTO.getRg() + " não localizado, abortando operação.");
        }
        Paciente paciente = pacienteOp.get();
        if(pacienteDTO.getNome() != null)
            paciente.setNome(pacienteDTO.getNome());
         else
            paciente.getNome();
        if(pacienteDTO.getSobrenome() != null)
            paciente.setSobrenome(pacienteDTO.getSobrenome());
        else
            paciente.getSobrenome();
        if(pacienteDTO.getEndereco().getRua() != null)
            paciente.getEndereco().setRua(pacienteDTO.getEndereco().getRua());
        else
            paciente.getEndereco().getRua();
        if(pacienteDTO.getEndereco().getNumero() != 0)
            paciente.getEndereco().setNumero(pacienteDTO.getEndereco().getNumero());
        else
            paciente.getEndereco().getNumero();
        if(pacienteDTO.getEndereco().getCidade() != null)
            paciente.getEndereco().setCidade(pacienteDTO.getEndereco().getCidade());
        else
            paciente.getEndereco().getCidade();
        if(pacienteDTO.getEndereco().getEstado() != null)
            paciente.getEndereco().setEstado(pacienteDTO.getEndereco().getEstado());
        else
            paciente.getEndereco().getEstado();

        PacienteDTO pacienteAlt = mapper.convertValue(pacienteRepository.save(paciente), PacienteDTO.class);
        logger.info("Paciente alterado com sucesso!");
        return pacienteAlt;
    }

    public PacienteDTO alterarTudo(PacienteDTO pacienteDTO) throws Exceptions {
        ObjectMapper mapper = new ObjectMapper();
        logger.info("Iniciando operação para alterar parcialmente o paciente.");
        Optional<Paciente> pacienteOp = Optional.ofNullable(pacienteRepository.findByRg(pacienteDTO.getRg()));
        if(pacienteOp.isEmpty()) {
            logger.error("Não existe o paciente com o rg " + pacienteDTO.getRg());
            throw new Exceptions("Paciente com o rg " + pacienteDTO.getRg() + " não localizado");
        }
        Paciente paciente = pacienteOp.get();
        paciente.setNome(pacienteDTO.getNome());
        paciente.setSobrenome(pacienteDTO.getSobrenome());
        paciente.getEndereco().setRua(pacienteDTO.getEndereco().getRua());
        paciente.getEndereco().setNumero(pacienteDTO.getEndereco().getNumero());
        paciente.getEndereco().setCidade(pacienteDTO.getEndereco().getCidade());
        paciente.getEndereco().setEstado(pacienteDTO.getEndereco().getEstado());

        PacienteDTO pacienteAlt = mapper.convertValue(pacienteRepository.save(paciente), PacienteDTO.class);
        logger.info("Paciente alterado com sucesso!");
        return pacienteAlt;
    }
}