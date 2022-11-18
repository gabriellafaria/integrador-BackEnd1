package com.dh.consultorioOdontologico.controller;

import com.dh.consultorioOdontologico.dao.impl.PacienteDao;
import com.dh.consultorioOdontologico.model.Paciente;
import com.dh.consultorioOdontologico.service.ConsultaService;
import com.dh.consultorioOdontologico.service.PacienteService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/paciente")
public class PacienteController {
    PacienteService pacienteService = new PacienteService();

    @PutMapping("/{id}")
    public Paciente alterarPaciente(@PathVariable("id")  int id, @RequestBody Paciente paciente) throws SQLException {
        System.out.println();
        return pacienteService.modificar(paciente);
    }

}
