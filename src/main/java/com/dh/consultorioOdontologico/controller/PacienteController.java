package com.dh.consultorioOdontologico.controller;


import com.dh.consultorioOdontologico.dao.impl.PacienteDao;
import com.dh.consultorioOdontologico.service.ConsultaService;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;
import com.dh.consultorioOdontologico.model.Endereco;
import com.dh.consultorioOdontologico.model.Paciente;
import com.dh.consultorioOdontologico.service.PacienteService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/paciente")
public class PacienteController {
    PacienteService pacienteService = new PacienteService();

    @PutMapping("/{id}")
    public Paciente alterarPaciente(@PathVariable("id")  int id, @RequestBody Paciente paciente) throws SQLException {
        System.out.println();
        return pacienteService.modificar(paciente);
    }

    @PostMapping()
    public Paciente post(@RequestBody Paciente paciente) throws SQLException {
        return pacienteService.cadastrar(paciente);
    }
}
