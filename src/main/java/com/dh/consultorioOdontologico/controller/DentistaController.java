package com.dh.consultorioOdontologico.controller;

import com.dh.consultorioOdontologico.model.Consulta;
import com.dh.consultorioOdontologico.model.Dentista;
import com.dh.consultorioOdontologico.model.Paciente;
import com.dh.consultorioOdontologico.service.DentistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/dentista")

public class DentistaController {
    @Autowired
    DentistaService dentistaService;

    @GetMapping()
    public List<Dentista> buscar() throws SQLException {
        return dentistaService.buscarDentistas();
    }

    @GetMapping("/{id}")
    public Optional<Dentista> buscarDentistaPorId(@PathVariable("id") int id) throws SQLException {
        return dentistaService.buscarPorId(id);
    }

    @PutMapping()
    public Dentista alterarDentista(@RequestBody Dentista dentista) throws SQLException {
        return dentistaService.modificar(dentista);
    }

    @PostMapping()
    public Dentista post(@RequestBody Dentista dentista) throws SQLException {
        return dentistaService.cadastrar(dentista);
    }

    @PatchMapping()
    public Dentista alterarDentistaParcial(@RequestBody Dentista dentista) throws SQLException {
        return dentistaService.modificar(dentista);
    }
}
