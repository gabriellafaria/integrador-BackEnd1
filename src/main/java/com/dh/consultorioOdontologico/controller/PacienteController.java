package com.dh.consultorioOdontologico.controller;

import com.dh.consultorioOdontologico.model.Paciente;
import com.dh.consultorioOdontologico.service.PacienteService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/paciente")
public class PacienteController {
    PacienteService pacienteService = new PacienteService();

    @DeleteMapping()
    public void excluirPaciente(@RequestBody Paciente paciente) throws SQLException {
        pacienteService.excluir(paciente);
    }

}
