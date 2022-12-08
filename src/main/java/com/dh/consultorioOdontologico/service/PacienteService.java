package com.dh.consultorioOdontologico.service;

import com.dh.consultorioOdontologico.entity.Endereco;
import com.dh.consultorioOdontologico.entity.Paciente;
import com.dh.consultorioOdontologico.entity.dto.PacienteDTO;
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

    public PacienteDTO salvar(PacienteDTO pacienteDTO){
        ObjectMapper mapper = new ObjectMapper();
        logger.info("Iniciando operação para salvar o paciente.");
        Endereco endereco = mapper.convertValue(pacienteDTO.getEndereco(), Endereco.class);
        Paciente paciente = mapper.convertValue(pacienteDTO, Paciente.class);
        paciente.setDataRegistro(Timestamp.from(Instant.now()));
        paciente.setEndereco(endereco);
        Paciente pacienteSalvo = pacienteRepository.save(paciente);
        logger.info("Paciente " + pacienteSalvo.getNome() + " salvo com sucesso.");
        return pacienteDTO;
    }

    public Optional<Paciente> deletar(String  rg){
        logger.info("Iniciada operação para deletar o paciente com o Rg " + rg);
       Optional<Paciente> paciente = Optional.ofNullable(pacienteRepository.findByRg(rg));
       if(paciente.isEmpty())
           logger.error("Erro ao excluir o paciente. Não foi localizado nenhum cadastro com o rg " + rg);
       pacienteRepository.deleteById(paciente.get().getId());
        logger.info("Paciente deletado!");
        return paciente;
    }

    public PacienteDTO buscarPorRg(String rg) {
        logger.info("iniciando operação para buscar o paciente com o rg " + rg);
        ObjectMapper mapper = new ObjectMapper();
        Optional<Paciente> paciente = Optional.ofNullable(pacienteRepository.findByRg(rg));
        if(paciente.isEmpty()) {
            logger.error("Paciente com o rg " + rg + " não localizado.");
        }
        PacienteDTO pacienteDTO = mapper.convertValue(paciente.get(), PacienteDTO.class);
        logger.info("Paciente com o rg " + rg + " localizado com sucesso.");
        return pacienteDTO;
    }

    public PacienteDTO alterarParcialmente(PacienteDTO pacienteDTO){
        ObjectMapper mapper = new ObjectMapper();
        logger.info("Iniciando operação para alterar parcialmente o paciente.");
        Optional<Paciente> pacienteOp = Optional.ofNullable(pacienteRepository.findByRg(pacienteDTO.getRg()));
        if(pacienteOp.isEmpty())
            logger.error("Paciente com o rg " + pacienteDTO.getRg() + " não encontrado.");
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

    public PacienteDTO alterarTudo(PacienteDTO pacienteDTO){
        ObjectMapper mapper = new ObjectMapper();
        logger.info("Iniciando operação para alterar parcialmente o paciente.");
        Optional<Paciente> pacienteOp = Optional.ofNullable(pacienteRepository.findByRg(pacienteDTO.getRg()));
        if(pacienteOp.isEmpty())
            logger.error("Não existe o paciente com o rg " + pacienteDTO.getRg());
        Paciente paciente = pacienteOp.get();
        if(pacienteDTO.getNome() != null)
            paciente.setNome(pacienteDTO.getNome());
        if(pacienteDTO.getSobrenome() != null)
            paciente.setSobrenome(pacienteDTO.getSobrenome());
        if(pacienteDTO.getEndereco() != null)
            paciente.getEndereco().setRua(pacienteDTO.getEndereco().getRua());
        if(pacienteDTO.getEndereco().getNumero() != 0)
            paciente.getEndereco().setNumero(pacienteDTO.getEndereco().getNumero());
        if(pacienteDTO.getEndereco().getCidade() != null)
            paciente.getEndereco().setCidade(pacienteDTO.getEndereco().getCidade());
        if(pacienteDTO.getEndereco().getEstado() != null)
            paciente.getEndereco().setEstado(pacienteDTO.getEndereco().getEstado());

        PacienteDTO pacienteAlt = mapper.convertValue(pacienteRepository.save(paciente), PacienteDTO.class);
        logger.info("Paciente alterado com sucesso!");
        return pacienteAlt;
    }
}