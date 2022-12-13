package com.dh.consultorioOdontologico.service;

import com.dh.consultorioOdontologico.entity.Endereco;
import com.dh.consultorioOdontologico.entity.dto.EnderecoDTO;
import com.dh.consultorioOdontologico.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@SpringBootTest
class EnderecoServiceTest {

    @Autowired
    EnderecoService enderecoService;

    static final Logger logger = Logger.getLogger(EnderecoServiceTest.class);
    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void salvar(){
        EnderecoDTO endereco = new EnderecoDTO();
        logger.info("Testando: Salvar endereço");
        endereco.setId(1L);
        endereco.setRua("Rua do Evertinho");
        endereco.setNumero(16);
        endereco.setCidade("Evertinho City");
        endereco.setEstado("ES");
        Endereco enderecoSalvo = enderecoService.salvar(endereco);
        Assertions.assertNotNull(enderecoSalvo.getId());
    }

    @Test
    void buscarTodos(){
        List<EnderecoDTO> enderecoDTOList = enderecoService.buscarTodosEnderecos();
        Assertions.assertEquals(1, enderecoDTOList.size());
    }


    @Test
    void buscarPorId() throws ResourceNotFoundException {
        Optional<Endereco> endereco = enderecoService.buscarEnderecoPorId(1L);
        Assertions.assertNotNull(endereco);
    }

    @Test
    void deletar(){
        Optional<Endereco> endereco = enderecoService.deletarEndereco(1L);
        Assertions.assertFalse(endereco.isEmpty());

    }




}