package com.dh.consultorioOdontologico.controller;

import com.dh.consultorioOdontologico.model.Consulta;
import com.dh.consultorioOdontologico.service.ConsultaService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/consulta")
public class ConsultaController {
    ConsultaService consultaService = new ConsultaService();

    public Consulta alterarConsulta(@RequestBody Consulta consulta) throws SQLException {
        return consultaService.modificar(consulta);
    }

}
