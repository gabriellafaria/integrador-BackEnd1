package com.dh.consultorioOdontologico.service;


import com.dh.consultorioOdontologico.entity.dto.EnderecoDTO;
import com.dh.consultorioOdontologico.entity.Endereco;
import com.dh.consultorioOdontologico.repository.EnderecoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {

    @Autowired
    EnderecoRepository repository;

    static final Logger logger = Logger.getLogger(EnderecoService.class);
    public ResponseEntity salvarEndereco(Endereco endereco) {
        logger.info("Cadastrando novo endereço.");
        try {
            Endereco enderecoSalvo = repository.save(endereco);
            logger.info("Endereço cadastrado com sucesso.");
            return new ResponseEntity("Endereco com id " + enderecoSalvo.getId() + " foi cadastrado com sucesso.", HttpStatus.CREATED);
        }catch (Exception e) {
            logger.error("O endereço não foi cadastrado.");
            return new ResponseEntity("Erro ao cadastrar endereço, tente novamente.", HttpStatus.BAD_REQUEST);
        }
    }

    public List<EnderecoDTO> buscarTodosEnderecos(){
        logger.info("Buscando todos os endereços.");
        List<Endereco> listEndereco = repository.findAll();
        List<EnderecoDTO> enderecoDTOList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        for (Endereco endereco : listEndereco) {
            EnderecoDTO enderecoDTO = mapper.convertValue(endereco, EnderecoDTO.class);
            enderecoDTOList.add(enderecoDTO);
        }
        logger.info("Exibindo todos os endereços.");
        return enderecoDTOList;
    }

    public ResponseEntity buscarEnderecoPorId(Long id){
        Optional<Endereco> endereco = repository.findById((id));
        logger.info("Buscando endereço.");
        if (endereco.isEmpty()) {
        logger.error("O endereço informado não existe.");
            return new ResponseEntity("O endereço informado não existe.", HttpStatus.BAD_REQUEST);
        }
        repository.findById(id);
        logger.info("Endereço encontrado com sucesso.");
        return new ResponseEntity("Endereço encontrado com sucesso", HttpStatus.OK);
    }

    public ResponseEntity alterarTotal(EnderecoDTO enderecoDTO) {
        ObjectMapper mapper = new ObjectMapper();
        logger.info("Atualizando totalmente um endereço.");
        Optional<Endereco> enderecoOptional = repository.findById(enderecoDTO.getId());
        if (enderecoOptional.isEmpty()) {
            logger.error("O endereço informado não existe.");
            return new ResponseEntity("O endereço com o id " + enderecoDTO.getId() + " não existe", HttpStatus.NOT_FOUND);
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
        return new ResponseEntity(enderecoAlterado, HttpStatus.CREATED);
    }

    public ResponseEntity alterarParcial(EnderecoDTO enderecoDTO) {
        ObjectMapper mapper = new ObjectMapper();
        logger.info("Atualizando parcialmente um endereço.");
        Optional<Endereco> enderecoOptional = repository.findById(enderecoDTO.getId());
        if (enderecoOptional.isEmpty()) {
            logger.error("O endereço informado não existe.");
            return new ResponseEntity("O endereço informado não existe",HttpStatus.NOT_FOUND);
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

        Endereco enderecoAlterado = mapper.convertValue(repository.save(atualizandoDados), Endereco.class);
        logger.info("O endereço foi atualizado com sucesso.");
        return new ResponseEntity(enderecoAlterado, HttpStatus.CREATED);
    }

    public ResponseEntity deletarEndereco(Long id) {
        Optional<Endereco> endereco = repository.findById(id);
        logger.info("Excluíndo um endereço pelo id.");
        if(endereco.isEmpty()) {
            logger.error("O endereço informado não existe.");
            return new ResponseEntity("O endereço informado não existe.", HttpStatus.BAD_REQUEST);
        }
        repository.deleteById(id);
        logger.info("Endereço excluído com sucesso.");
        return new ResponseEntity("Endereço excluído com sucesso.", HttpStatus.OK);
    }
}
