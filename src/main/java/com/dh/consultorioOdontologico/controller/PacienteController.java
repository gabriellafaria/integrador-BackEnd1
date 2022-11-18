package com.dh.consultorioOdontologico.controller;

import com.dh.consultorioOdontologico.model.Endereco;
import com.dh.consultorioOdontologico.model.Paciente;
import com.dh.consultorioOdontologico.service.PacienteService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/paciente")
public class PacienteController {
    PacienteService pacienteService = new PacienteService();

    @PostMapping()
    public Paciente post(@RequestBody Paciente paciente) throws SQLException {
        return pacienteService.cadastrar(paciente);
    }
}
