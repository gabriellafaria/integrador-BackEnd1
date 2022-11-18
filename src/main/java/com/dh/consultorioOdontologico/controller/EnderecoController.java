package com.dh.consultorioOdontologico.controller;

import com.dh.consultorioOdontologico.model.Endereco;
import com.dh.consultorioOdontologico.service.EnderecoService;
import org.springframework.web.bind.annotation.PutMapping;
import com.dh.consultorioOdontologico.model.Dentista;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.sql.SQLException;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {
    EnderecoService enderecoService = new EnderecoService();

    @PutMapping()
    public Endereco alterarEndereco(@RequestBody Endereco endereco) throws SQLException {
        return enderecoService.modificar(endereco);
    }
    
    @PostMapping()
    public Endereco post(@RequestBody Endereco endereco) throws SQLException {
        return enderecoService.cadastrar(endereco);
    }
}
