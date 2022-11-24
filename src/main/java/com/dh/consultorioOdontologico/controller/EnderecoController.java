package com.dh.consultorioOdontologico.controller;

import com.dh.consultorioOdontologico.entity.Endereco;
import com.dh.consultorioOdontologico.service.EnderecoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import com.dh.consultorioOdontologico.model.Dentista;

import java.sql.SQLException;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {
    @Autowired
    EnderecoService enderecoService;

    @PostMapping()
    public Endereco post(@RequestBody Endereco endereco) throws SQLException {
        return enderecoService.cadastrar(endereco);
    }

}
