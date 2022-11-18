package com.dh.consultorioOdontologico.controller;

import com.dh.consultorioOdontologico.model.Endereco;
import com.dh.consultorioOdontologico.service.EnderecoService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {
    EnderecoService enderecoService = new EnderecoService();

    @DeleteMapping()
    public void excluirEndereco(@RequestBody Endereco endereco) throws SQLException {
        enderecoService.excluir(endereco);
    }
}
