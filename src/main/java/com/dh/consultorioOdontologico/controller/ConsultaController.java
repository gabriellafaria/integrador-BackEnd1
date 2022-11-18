package com.dh.consultorioOdontologico.controller;

import com.dh.consultorioOdontologico.model.Consulta;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @DeleteMapping()
    public void excluirConsulta(@RequestBody Consulta consulta) throws SQLException {
        consultaService.excluir(consulta);
    }
    
    @PutMapping()
    public Consulta alterarConsulta(@RequestBody Consulta consulta) throws SQLException {
        return consultaService.modificar(consulta);
    }
    
    @PostMapping()
    public Consulta post(@RequestBody Consulta consulta) throws SQLException {
        return consultaService.cadastrar(consulta);
    }
}
