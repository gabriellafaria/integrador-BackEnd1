package com.dh.consultorioOdontologico.controller;

import com.dh.consultorioOdontologico.model.Dentista;
import com.dh.consultorioOdontologico.service.DentistaService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/dentista")
public class DentistaController {
    DentistaService dentistaService = new DentistaService();

    @PutMapping()
    public Dentista alterarDentista(@RequestBody Dentista dentista) throws SQLException {
        return dentistaService.modificar(dentista);
    }

}
