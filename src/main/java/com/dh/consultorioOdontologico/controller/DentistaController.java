package com.dh.consultorioOdontologico.controller;

import com.dh.consultorioOdontologico.model.Consulta;
import com.dh.consultorioOdontologico.model.Dentista;
import com.dh.consultorioOdontologico.service.DentistaService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/dentista")

public class DentistaController {
    DentistaService dentistaService = new DentistaService();

    @GetMapping()
    public List<Dentista> buscar() throws SQLException {
        return dentistaService.buscarDentistas();
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
