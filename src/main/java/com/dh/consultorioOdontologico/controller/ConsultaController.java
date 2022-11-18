package com.dh.consultorioOdontologico.controller;

import com.dh.consultorioOdontologico.model.Consulta;
import com.dh.consultorioOdontologico.model.Endereco;
import com.dh.consultorioOdontologico.service.ConsultaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/consulta")
public class ConsultaController {
    ConsultaService consultaService = new ConsultaService();

    @PostMapping()
    public Consulta post(@RequestBody Consulta consulta) throws SQLException {
        return consultaService.cadastrar(consulta);
    }

}
