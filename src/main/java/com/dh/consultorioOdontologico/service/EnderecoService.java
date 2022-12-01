package com.dh.consultorioOdontologico.service;


import com.dh.consultorioOdontologico.entity.dto.EnderecoDTO;
import com.dh.consultorioOdontologico.entity.Endereco;
import com.dh.consultorioOdontologico.repository.EnderecoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    public ResponseEntity salvarEndereco(Endereco endereco) {
        try {
            Endereco enderecoSalvo = repository.save(endereco);
            return new ResponseEntity("Endereco com id " + enderecoSalvo.getId() + " salvo com sucesso.", HttpStatus.CREATED);
        }catch (Exception e) {
            return new ResponseEntity("Erro ao cadastrar endereço, tente novamente.", HttpStatus.BAD_REQUEST);
        }
    }

    public List<EnderecoDTO> buscarTodosEnderecos(){
        List<Endereco> listEndereco = repository.findAll();
        List<EnderecoDTO> enderecoDTOList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        for (Endereco endereco : listEndereco) {
            EnderecoDTO enderecoDTO = mapper.convertValue(endereco, EnderecoDTO.class);
            enderecoDTOList.add(enderecoDTO);
        }
        return enderecoDTOList;
    }

    public ResponseEntity buscarEnderecoPorId(Long id){
        Optional<Endereco> endereco = repository.findById((id));

        if (endereco.isEmpty())
            return new ResponseEntity("O endereço não existe",HttpStatus.BAD_REQUEST);

        repository.findById(id);
        return new ResponseEntity("Endereço encontrado com sucesso", HttpStatus.OK);
    }

    public ResponseEntity alterarParcial(Endereco endereco) {
        ObjectMapper mapper = new ObjectMapper();
        Optional<Endereco> enderecoOptional = repository.findById(endereco.getId());
        if (enderecoOptional.isEmpty()) {
            return new ResponseEntity("O endereco informado não existe",HttpStatus.NOT_FOUND);
        }
        Endereco atualizandoDados = enderecoOptional.get();
        if (endereco.getRua() != null) {
            atualizandoDados.setRua((endereco.getRua()));
        }
        if (endereco.getNumero() != 0 ){
            atualizandoDados.setNumero(endereco.getNumero());
        }
        if (endereco.getSiglaEstado() != null) {
            atualizandoDados.setSiglaEstado(endereco.getSiglaEstado());
        }
        if (endereco.getCidade() != null) {
            atualizandoDados.setCidade(endereco.getCidade());
        }

        Endereco enderecoAlterado = mapper.convertValue(repository.save(atualizandoDados), Endereco.class);

        return new ResponseEntity(enderecoAlterado, HttpStatus.CREATED);
    }

    public ResponseEntity deletarEndereco(Long id) {
        Optional<Endereco> endereco = repository.findById(id);

        if(endereco.isEmpty()) {
            return new ResponseEntity("O enderço não existe.", HttpStatus.BAD_REQUEST);
        }
        repository.deleteById(id);
        return new ResponseEntity("Endereço excluído com sucesso.", HttpStatus.OK);
    }
}
