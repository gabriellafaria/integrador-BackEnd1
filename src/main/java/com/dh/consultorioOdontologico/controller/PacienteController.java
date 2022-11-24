package com.dh.consultorioOdontologico.controller;

import com.dh.consultorioOdontologico.entity.Paciente;
import com.dh.consultorioOdontologico.service.PacienteService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import com.dh.consultorioOdontologico.dao.impl.PacienteDao;
import com.dh.consultorioOdontologico.service.ConsultaService;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;
import java.util.List;
import com.dh.consultorioOdontologico.model.Endereco;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;

@RestController
@RequestMapping("/paciente")
public class PacienteController {
    @Autowired
    PacienteService pacienteService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping()
    public List<Paciente> buscarTodos() throws SQLException {
        return pacienteService.buscarTodos();
    }

    @GetMapping("/{id}")
    public Optional<Paciente> buscarPacientePorId(@PathVariable("id") int id) throws SQLException {
        return pacienteService.buscarPorId(id);
    }

    @DeleteMapping()
    public void excluirPaciente(@RequestBody Paciente paciente) throws SQLException {
        pacienteService.excluir(paciente);

    }

    @PutMapping()
    public Paciente alterarPaciente(@RequestBody Paciente paciente) throws SQLException {  //Felipe comment: acho que nao precisa passar o id como PathVariable, pq podemos retirar o id do paciente direto
        return pacienteService.modificar(paciente);
    }

    @PostMapping()
    public Paciente post(@RequestBody Paciente paciente) throws SQLException {
        return pacienteService.cadastrar(paciente);
    }

}

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarPacienteParcial(@PathVariable("id") int id, @RequestBody Paciente paciente) throws SQLException {
            pacienteService.buscarPorId(id).map(pacienteBase -> {
            modelMapper.map(paciente, pacienteBase);
            return Void.TYPE;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado"));
    }
}
