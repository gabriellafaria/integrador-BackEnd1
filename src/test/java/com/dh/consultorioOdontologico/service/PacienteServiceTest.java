package com.dh.consultorioOdontologico.service;

import com.dh.consultorioOdontologico.entity.Paciente;
import com.dh.consultorioOdontologico.entity.dto.EnderecoDTO;
import com.dh.consultorioOdontologico.entity.dto.PacienteDTO;
import com.dh.consultorioOdontologico.exception.Exceptions;
import com.dh.consultorioOdontologico.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class PacienteServiceTest {
    @Autowired
    PacienteService pacienteService;

    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void salvar(){
        EnderecoDTO endereco = new EnderecoDTO();
        endereco.setRua("uma");
        endereco.setNumero(12);
        endereco.setCidade("Ankf");
        endereco.setEstado("Gf");
        PacienteDTO paciente = new PacienteDTO();
        paciente.setNome("Gabriella");
        paciente.setSobrenome("Faria");
        paciente.setRg("123456");
        paciente.setEndereco(endereco);

        Paciente pacienteSalvo = pacienteService.salvar(paciente);

        Assertions.assertNotNull(pacienteSalvo.getId());
    }

    @AfterEach
    public void deletarDepois() throws ResourceNotFoundException {
        PacienteDTO paciente = pacienteService.buscarPorRg("123456");
        pacienteService.deletar(paciente.getRg());
    }

    @Test
    public void buscar(){
        List<PacienteDTO> pacienteDTOList = pacienteService.buscar();
        Assertions.assertEquals(1, pacienteDTOList.size());
    }

    @Test
    public void buscarPorRg() throws ResourceNotFoundException {
        PacienteDTO paciente = pacienteService.buscarPorRg("123456");
        Assertions.assertNotNull(paciente);
    }

    @Test
    public void alterarTudo() throws ResourceNotFoundException, Exceptions {
        PacienteDTO pacienteDTO = pacienteService.buscarPorRg("123456");
        String nomeAnterior = pacienteDTO.getNome();
        pacienteDTO.setNome("Sabrina");
        pacienteDTO.setSobrenome("Freiberger");
        EnderecoDTO enderecoDTO = pacienteDTO.getEndereco();
        enderecoDTO.setRua("Rua dos perdidos");
        enderecoDTO.setCidade("Perdidin");
        enderecoDTO.setNumero(1456);
        enderecoDTO.setEstado("PE");
        pacienteService.alterarTudo(pacienteDTO);
        Assertions.assertFalse(nomeAnterior.equals(pacienteDTO.getNome()));
    }

    @Test
    public void alterarParcialmente() throws ResourceNotFoundException, Exceptions {
        PacienteDTO paciente = pacienteService.buscarPorRg("123456");
        String nomeAnterior = paciente.getNome();
        paciente.setNome("Felipe");
        pacienteService.alterarParcialmente(paciente);

        Assertions.assertFalse(nomeAnterior.equals(paciente.getNome()));
    }

    @Test
    public void salvarComMesmoRG(){
        EnderecoDTO endereco = new EnderecoDTO();
        endereco.setRua("Rua dos esperançosos");
        endereco.setNumero(1212);
        endereco.setCidade("Lost");
        endereco.setEstado("Lo");
        PacienteDTO paciente = new PacienteDTO();
        paciente.setNome("Mariana");
        paciente.setSobrenome("Orsi");
        paciente.setRg("123456");
        paciente.setEndereco(endereco);

        Paciente pacienteSalvo = pacienteService.salvar(paciente);

        Assertions.assertNotNull(pacienteSalvo.getId());
    }

    @Test
    public void buscarComRgIncorreto() throws ResourceNotFoundException {
        PacienteDTO paciente = pacienteService.buscarPorRg("1234568");
        Assertions.assertNotNull(paciente);
    }

    @Test
    public void deleteAltAssert() throws ResourceNotFoundException {
        PacienteDTO paciente = pacienteService.buscarPorRg("123456");
        Paciente pacienteAlterado = mapper.convertValue(paciente, Paciente.class);
        pacienteService.deletar(paciente.getRg());
        Assertions.assertNotNull(pacienteAlterado.getId());
    }

    @Test
    public void alterarTudoIncorreto() throws ResourceNotFoundException, Exceptions {
        PacienteDTO paciente = pacienteService.buscarPorRg("123456");
        String nomeAnterior = paciente.getNome();
        paciente.setNome("Sabrina");
       // paciente.setSobrenome("Freiberger");
        EnderecoDTO enderecoDTO = paciente.getEndereco();
        enderecoDTO.setRua("Rua dos perdidos");
        enderecoDTO.setCidade("Perdidin");
        enderecoDTO.setNumero(1456);
        enderecoDTO.setEstado("PE");
        pacienteService.alterarTudo(paciente);
        Assertions.assertTrue(nomeAnterior.equals(paciente.getNome()));
    }
}