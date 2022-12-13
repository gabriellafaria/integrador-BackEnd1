package com.dh.consultorioOdontologico.service;

import com.dh.consultorioOdontologico.entity.Dentista;
import com.dh.consultorioOdontologico.entity.Paciente;
import com.dh.consultorioOdontologico.entity.Perfil;
import com.dh.consultorioOdontologico.entity.Usuario;
import com.dh.consultorioOdontologico.entity.dto.ConsultaDTO;
import com.dh.consultorioOdontologico.entity.dto.EnderecoDTO;
import com.dh.consultorioOdontologico.entity.dto.PacienteDTO;
import com.dh.consultorioOdontologico.entity.dto.UsuarioDTO;
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
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
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
    @BeforeEach public void incluir(){
        Dentista dentista1 = new Dentista(1L, "Marlene", "Santos", 123,null);
        Dentista dentista2 = new Dentista(2L, "Mariana", "Nunes", 456, null);
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
        PacienteDTO pacienteDTO1 = new PacienteDTO("Felipe", "Stefani", "123456", enderecoDTO1, Timestamp.valueOf(LocalDateTime.now()), null);
        PacienteDTO pacienteDTO2 = new PacienteDTO("Joana", "Stefani", "789101", enderecoDTO2, Timestamp.valueOf(LocalDateTime.now()), null);
        Paciente pacienteSalvo1 = pacienteService.salvar(pacienteDTO1);
        Paciente pacienteSalvo2 = pacienteService.salvar(pacienteDTO2);
        Dentista dentistaSalvo1 = dentistaService.salvar(dentista1);
        Dentista dentistaSalvo2 = dentistaService.salvar(dentista2);
        ConsultaDTO consultaDTO = new ConsultaDTO();
        consultaDTO.setRgPaciente(pacienteSalvo1.getRg());
        consultaDTO.setMatriculaDentista(dentistaSalvo1.getMatricula());
        consultaDTO.setDataConsulta(Timestamp.from(Timestamp.valueOf("2022-12-21 00:00:00").toInstant().plus(-3, ChronoUnit.HOURS)));
        consultaDTO.setChave();
        consultaService.salvar(consultaDTO);
        consultaDTO.setDataConsulta(Timestamp.valueOf("2022-12-21 00:00:00"));
    }

    @Test public void buscarTodasConsultas(){
        List<ConsultaDTO> consultaDTOList = consultaService.buscarTodasConsultas();
        System.out.println(consultaDTOList);
        Assertions.assertTrue(consultaDTOList.size() >= 1);
    }

    @Test public void salvarConsulta(){
        ConsultaDTO consultaDTO = new ConsultaDTO();
        consultaDTO.setRgPaciente("789101");
        consultaDTO.setMatriculaDentista(456);
        consultaDTO.setDataConsulta(Timestamp.from(Timestamp.valueOf("2022-12-21 00:00:00").toInstant().plus(-3, ChronoUnit.HOURS)));
        consultaDTO.setChave();
        consultaDTO.setDataConsulta(Timestamp.valueOf("2022-12-21 00:00:00"));
        ResponseEntity resultado = consultaService.salvar(consultaDTO);
        Assertions.assertEquals("Created",resultado.getStatusCode().getReasonPhrase());
    }

    @Test public void deletarConsulta(){
        ConsultaDTO consultaDTO = consultaDeTeste();
        ResponseEntity resultado = consultaService.deletarConsulta(consultaDTO);
        Assertions.assertEquals("OK", resultado.getStatusCode().getReasonPhrase());
    }

    @Test public void deletarConsultaInexistente(){
        ConsultaDTO consultaDTO = new ConsultaDTO();
        consultaDTO.setRgPaciente("123456");
        consultaDTO.setMatriculaDentista(123);
        consultaDTO.setDataConsulta(Timestamp.from(Timestamp.valueOf("2022-12-20 00:00:00").toInstant().plus(-3, ChronoUnit.HOURS)));
        consultaDTO.setChave();
        consultaDTO.setDataConsulta(Timestamp.valueOf("2022-12-20 00:00:00"));
        ResponseEntity resultado = consultaService.deletarConsulta(consultaDTO);
        Assertions.assertEquals("Not Found", resultado.getStatusCode().getReasonPhrase());
    }

    @Test public void marcarConsultaComMedicoIndisponivel(){
        ConsultaDTO consultaDTO = consultaDeTeste();
        consultaDTO.setRgPaciente("789101");
        ResponseEntity resultado = consultaService.salvar(consultaDTO);
        Assertions.assertEquals("Not Acceptable", resultado.getStatusCode().getReasonPhrase());
    }

    @Test public void marcarConsultaComPacienteNaoCadastrado(){
        ConsultaDTO consultaDTO = consultaDeTeste();
        consultaDTO.setRgPaciente("1357911");
        ResponseEntity resultado = consultaService.salvar(consultaDTO);
        Assertions.assertEquals("Not Found", resultado.getStatusCode().getReasonPhrase());
    }

    @Test public void marcarConsultaAnteriorADataAtual(){
        ConsultaDTO consultaDTO = consultaDeTeste();
        consultaDTO.setDataConsulta(Timestamp.from(Timestamp.valueOf("2022-12-11 00:00:00").toInstant().plus(-3, ChronoUnit.HOURS)));
        ResponseEntity resultado = consultaService.salvar(consultaDTO);
        Assertions.assertEquals("Forbidden", resultado.getStatusCode().getReasonPhrase());
    }

    @Test public void buscarConsultaPorPaciente() throws ResourceNotFoundException {
        ConsultaDTO consultaDTO = consultaDeTeste();
        List<ConsultaDTO> consultaDTOList = consultaService.buscarConsultasPaciente(consultaDTO.getRgPaciente());
        System.out.println(consultaDTOList);
        Assertions.assertTrue(consultaDTOList.size() >= 1);
    }

    @Test public void buscarConsultaPorDentista() throws ResourceNotFoundException {
        ConsultaDTO consultaDTO = consultaDeTeste();
        List<ConsultaDTO> consultaDTOList = consultaService.buscarConsultasDentista(consultaDTO.getMatriculaDentista());
        System.out.println(consultaDTOList);
        Assertions.assertTrue(consultaDTOList.size() >= 1);
    }

    @AfterEach public void deletarDados() throws ResourceNotFoundException {
        Optional<Paciente> pacienteDTO1 = pacienteService.deletar("123456");
        Optional<Paciente> pacienteDTO2 = pacienteService.deletar("789101");
        ResponseEntity dentista1 = dentistaService.deletar(123);
        ResponseEntity dentista2 = dentistaService.deletar(456);
        ConsultaDTO consultaDTO = consultaDeTeste();
        ResponseEntity consulta = consultaService.deletarConsulta(consultaDTO);
    }

    public ConsultaDTO consultaDeTeste(){
        ConsultaDTO consultaDTO = new ConsultaDTO();
        consultaDTO.setRgPaciente("123456");
        consultaDTO.setMatriculaDentista(123);
        consultaDTO.setDataConsulta(Timestamp.from(Timestamp.valueOf("2022-12-21 00:00:00").toInstant().plus(-3, ChronoUnit.HOURS)));
        consultaDTO.setChave();
        consultaDTO.setDataConsulta(Timestamp.valueOf("2022-12-21 00:00:00"));
        return consultaDTO;
    }
}
