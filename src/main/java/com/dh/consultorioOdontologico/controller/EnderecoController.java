package com.dh.consultorioOdontologico.controller;

import com.dh.consultorioOdontologico.entity.Endereco;
import com.dh.consultorioOdontologico.service.EnderecoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {
    EnderecoService enderecoService = new EnderecoService();

    @PostMapping()
    public Endereco post(@RequestBody Endereco endereco) throws SQLException {
        return enderecoService.cadastrar(endereco);
    }

}
