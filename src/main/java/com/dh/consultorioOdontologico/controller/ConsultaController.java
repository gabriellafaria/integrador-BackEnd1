package com.dh.consultorioOdontologico.controller;

import com.dh.consultorioOdontologico.model.Consulta;
import com.dh.consultorioOdontologico.model.Endereco;
import org.springframework.web.bind.annotation.DeleteMapping;
import com.dh.consultorioOdontologico.service.ConsultaService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/consulta")
public class ConsultaController {
    ConsultaService consultaService = new ConsultaService();

    @GetMapping()
    public List<Consulta> buscarTodos() throws SQLException {
        return consultaService.buscarTodos();
    }

    @GetMapping("/{id}")
    public Optional<Consulta> buscarConsultaPorId(@PathVariable("id") int id) throws SQLException {
        return consultaService.buscarPorId(id);
    }

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

    @PatchMapping()
    public Consulta alterarConsultaParcial(@RequestBody Consulta consulta) throws SQLException {
        return consultaService.modificar(consulta);
    }
}
