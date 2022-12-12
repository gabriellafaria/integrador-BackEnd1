package com.dh.consultorioOdontologico.service;

import com.dh.consultorioOdontologico.entity.Dentista;
import com.dh.consultorioOdontologico.entity.Paciente;
import com.dh.consultorioOdontologico.entity.dto.ConsultaDTO;
import com.dh.consultorioOdontologico.entity.dto.EnderecoDTO;
import com.dh.consultorioOdontologico.entity.dto.PacienteDTO;
import com.dh.consultorioOdontologico.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class ConsultaServiceTest {

    @Autowired
    ConsultaService consultaService;
    @Autowired
    PacienteService pacienteService;
    @Autowired
    DentistaService dentistaService;
    ObjectMapper mapper = new ObjectMapper();
    @BeforeEach
    public void incluir(){
        Dentista dentista1 = new Dentista(1L, "Marlene", "Santos", 123);
        Dentista dentista2 = new Dentista(2L, "Mariana", "Nunes", 456);
        EnderecoDTO enderecoDTO1 = new EnderecoDTO();
        enderecoDTO1.setRua("Rua das flores");
        enderecoDTO1.setNumero(83);
        enderecoDTO1.setCidade("Ubatuba");
        enderecoDTO1.setEstado("SP");
        EnderecoDTO enderecoDTO2 = new EnderecoDTO();
        enderecoDTO2.setRua("Rua dos cravos");
        enderecoDTO2.setNumero(56);
        enderecoDTO2.setCidade("Rio de Janeiro");
        enderecoDTO2.setEstado("RJ");
        //EnderecoDTO enderecoDTO1 = new EnderecoDTO(1L, "Rua das flores", 83, "Ubatuba", "SP");
        //EnderecoDTO enderecoDTO2 = new EnderecoDTO(2L, "Rua dos cravos", 56, "Rio de Janeiro", "RJ");
        //PacienteDTO pacienteDTO1 = new PacienteDTO("Felipe", "Stefani", "123456", enderecoDTO1, Timestamp.valueOf(LocalDateTime.now()));
        //PacienteDTO pacienteDTO2 = new PacienteDTO("Joana", "Stefani", "789101", enderecoDTO2, Timestamp.valueOf(LocalDateTime.now()));
        PacienteDTO pacienteDTO1 = new PacienteDTO("Felipe", "Stefani", "123456", enderecoDTO1, Timestamp.valueOf(LocalDateTime.now()));
        PacienteDTO pacienteDTO2 = new PacienteDTO("Joana", "Stefani", "789101", enderecoDTO2, Timestamp.valueOf(LocalDateTime.now()));
        Paciente pacienteSalvo1 = pacienteService.salvar(pacienteDTO1);
        Paciente pacienteSalvo2 = pacienteService.salvar(pacienteDTO2);
        Dentista dentistaSalvo1 = dentistaService.salvar(dentista1);
        Dentista dentistaSalvo2 = dentistaService.salvar(dentista2);
        ConsultaDTO consultaDTO = new ConsultaDTO();
        consultaDTO.setRgPaciente(pacienteSalvo1.getRg());
        System.out.println(pacienteSalvo1.getId() + " " + pacienteSalvo1.getNome());
        consultaDTO.setMatriculaDentista(dentistaSalvo1.getMatricula());
        System.out.println(dentistaSalvo1.getId() + " " + dentistaSalvo1.getNome());
        consultaDTO.setDataConsulta(Timestamp.valueOf("2022-12-21 00:00:00"));
        consultaDTO.setChave();
        consultaService.salvar(consultaDTO);
    }

    @Test
    public void buscarTodasConsultas(){
        List<ConsultaDTO> consultaDTOList = consultaService.buscarTodasConsultas();
        System.out.println(consultaDTOList);
        Assertions.assertTrue(consultaDTOList.size() >= 1);
    }

    @Test
    public void salvarConsulta(){
        ConsultaDTO consultaDTO = new ConsultaDTO();
        consultaDTO.setRgPaciente("789101");
        consultaDTO.setMatriculaDentista(456);
        consultaDTO.setDataConsulta(Timestamp.valueOf("2022-12-21 00:00:00"));
        consultaDTO.setChave();
        ResponseEntity resultado = consultaService.salvar(consultaDTO);
        Assertions.assertEquals("Created",resultado.getStatusCode().getReasonPhrase());
    }

    @Test
    public void deletarConsulta(){
        ConsultaDTO consultaDTO = new ConsultaDTO("123456", 123, Timestamp.valueOf("2022-12-21 00:00:00"), "1234561232022-12-21 00:00:00.0");
        ResponseEntity resultado = consultaService.deletarConsulta(consultaDTO);
        Assertions.assertEquals("OK", resultado.getStatusCode().getReasonPhrase());
    }

    @Test
    public void deletarConsultaInexistente(){
        ConsultaDTO consultaDTO = new ConsultaDTO("123456", 123, Timestamp.valueOf("2022-12-21 00:00:00"), "1234561232022-12-22 00:00:00.0");
        ResponseEntity resultado = consultaService.deletarConsulta(consultaDTO);
        Assertions.assertEquals("Not Found", resultado.getStatusCode().getReasonPhrase());
    }

    @Test
    public void marcarConsultaComMedicoIndisponivel(){
        ConsultaDTO consultaDTO = new ConsultaDTO("789101", 123, Timestamp.valueOf("2022-12-21 00:00:00"), "1234561232022-12-22 00:00:00.0");
        ResponseEntity resultado = consultaService.salvar(consultaDTO);
        Assertions.assertEquals("Not Acceptable", resultado.getStatusCode().getReasonPhrase());
    }

    @Test
    public void marcarConsultaComPacienteNaoCadastrado(){
        ConsultaDTO consultaDTO = new ConsultaDTO("1357911", 123, Timestamp.valueOf("2022-12-22 00:00:00"), "1234561232022-12-22 00:00:00.0");
        ResponseEntity resultado = consultaService.salvar(consultaDTO);
        Assertions.assertEquals("Not Found", resultado.getStatusCode().getReasonPhrase());
    }

    @Test void marcarConsultaAnteriorADataAtual(){
        ConsultaDTO consultaDTO = new ConsultaDTO("789101", 123, Timestamp.valueOf("2022-12-11 00:00:00"), "1234561232022-12-22 00:00:00.0");
        ResponseEntity resultado = consultaService.salvar(consultaDTO);
        Assertions.assertEquals("Forbidden", resultado.getStatusCode().getReasonPhrase());
    }

    @AfterEach
    public void deletarDados() throws ResourceNotFoundException {
        Optional<Paciente> pacienteDTO1 = pacienteService.deletar("123456");
        Optional<Paciente> pacienteDTO2 = pacienteService.deletar("789101");
        ResponseEntity dentista1 = dentistaService.deletar(123);
        ResponseEntity dentista2 = dentistaService.deletar(456);
        ConsultaDTO consultaDTO = new ConsultaDTO("123456", 123, Timestamp.valueOf("2022-12-21 00:00:00"), "1234561232022-12-21 00:00:00.0");
        ResponseEntity consulta = consultaService.deletarConsulta(consultaDTO);
    }
}
