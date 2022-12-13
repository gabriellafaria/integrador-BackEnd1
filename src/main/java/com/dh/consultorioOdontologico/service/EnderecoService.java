package com.dh.consultorioOdontologico.service;


import com.dh.consultorioOdontologico.entity.dto.EnderecoDTO;
import com.dh.consultorioOdontologico.entity.Endereco;
import com.dh.consultorioOdontologico.exception.ResourceNotFoundException;
import com.dh.consultorioOdontologico.repository.EnderecoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {

    @Autowired
    EnderecoRepository repository;
    ObjectMapper mapper = new ObjectMapper();
    static final Logger logger = Logger.getLogger(EnderecoService.class);
    public Endereco salvar(EnderecoDTO enderecoDTO) {
        logger.info("Cadastrando novo endereço.");
        Endereco endereco = mapper.convertValue(enderecoDTO, Endereco.class);
        Endereco enderecoSalvo = repository.save(endereco);
        logger.info("Endereço cadastrado com sucesso.");
        return  enderecoSalvo;
    }

    public List<EnderecoDTO> buscarTodosEnderecos(){
        logger.info("Buscando todos os endereços.");
        List<Endereco> listEndereco = repository.findAll();
        List<EnderecoDTO> enderecoDTOList = new ArrayList<>();
        for (Endereco endereco : listEndereco) {
            EnderecoDTO enderecoDTO = mapper.convertValue(endereco, EnderecoDTO.class);
            enderecoDTOList.add(enderecoDTO);
        }
        logger.info("Exibindo todos os endereços.");
        return enderecoDTOList;
    }

    public Optional<Endereco> buscarEnderecoPorId(Long id) throws ResourceNotFoundException {
        Optional<Endereco> endereco = repository.findById((id));
        logger.info("Buscando endereço.");
        if (endereco.isEmpty()) {
        logger.error("O endereço informado não existe.");
        throw new ResourceNotFoundException("O endereço não existe.");
        }
        repository.findById(id);
        logger.info("Endereço encontrado com sucesso.");
        return endereco;
    }

    public EnderecoDTO alterarTotal(EnderecoDTO enderecoDTO) {
        logger.info("Atualizando totalmente um endereço.");
        Optional<Endereco> enderecoOptional = repository.findById(enderecoDTO.getId());
        if (enderecoOptional.isEmpty()) {
            logger.error("O endereço informado não existe.");
        }
        Endereco atualizandoDados = enderecoOptional.get();
        if(enderecoDTO.getRua() != null)
            atualizandoDados.setRua(enderecoDTO.getRua());
        if (enderecoDTO.getNumero() != 0)
            atualizandoDados.setNumero(enderecoDTO.getNumero());
        if (enderecoDTO.getCidade() != null)
            atualizandoDados.setCidade(enderecoDTO.getCidade());
        if (enderecoDTO.getEstado() != null)
            atualizandoDados.setEstado(enderecoDTO.getEstado());

        EnderecoDTO enderecoAlterado = mapper.convertValue(repository.save(atualizandoDados), EnderecoDTO.class);
        logger.info("Endereço atualizado com sucesso.");
        return enderecoAlterado;
    }

    public EnderecoDTO alterarParcial(EnderecoDTO enderecoDTO) {
        logger.info("Atualizando parcialmente um endereço.");
        Optional<Endereco> enderecoOptional = repository.findById(enderecoDTO.getId());
        if (enderecoOptional.isEmpty()) {
            logger.error("O endereço informado não existe.");
        }
        Endereco atualizandoDados = enderecoOptional.get();
        if (enderecoDTO.getRua() != null)
            atualizandoDados.setRua((enderecoDTO.getRua()));
        if (enderecoDTO.getNumero() != 0 )
            atualizandoDados.setNumero(enderecoDTO.getNumero());
        if (enderecoDTO.getCidade() != null)
            atualizandoDados.setCidade(enderecoDTO.getCidade());
        if (enderecoDTO.getEstado() != null)
            atualizandoDados.setEstado(enderecoDTO.getEstado());

        EnderecoDTO enderecoParc = mapper.convertValue(repository.save(atualizandoDados), EnderecoDTO.class);
        logger.info("O endereço foi atualizado com sucesso.");
        return enderecoParc;
    }

    public Optional<Endereco> deletarEndereco(Long id) {

        Optional<Endereco> endereco = repository.findById(id);
        logger.info("Excluíndo um endereço pelo id.");
        if(endereco.isEmpty()) {
            logger.error("O endereço informado não existe.");
        }
        repository.deleteById(id);
        logger.info("Endereço excluído com sucesso.");
        return endereco;
    }
}
