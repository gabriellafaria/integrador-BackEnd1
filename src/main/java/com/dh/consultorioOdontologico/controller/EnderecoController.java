package com.dh.consultorioOdontologico.controller;

import com.dh.consultorioOdontologico.model.Consulta;
import com.dh.consultorioOdontologico.model.Endereco;
import com.dh.consultorioOdontologico.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import com.dh.consultorioOdontologico.model.Dentista;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {
    @Autowired
    EnderecoService enderecoService;

    @GetMapping()
    public List<Endereco> buscarTodos() throws SQLException {
        return enderecoService.buscarTodos();
    }

    @GetMapping("/{id}")
    public Optional<Endereco> buscarEnderecoPorId(@PathVariable("id") int id) throws SQLException {
        return enderecoService.buscarPorId(id);
    }

    @DeleteMapping()
    public void excluirEndereco(@RequestBody Endereco endereco) throws SQLException {
        enderecoService.excluir(endereco);
    }    

    @PutMapping()
    public Endereco alterarEndereco(@RequestBody Endereco endereco) throws SQLException {
        return enderecoService.modificar(endereco);
    }
    
    @PostMapping()
    public Endereco post(@RequestBody Endereco endereco) throws SQLException {
        return enderecoService.cadastrar(endereco);
    }

    @PatchMapping()
    public Endereco alterarEnderecoParcial(@RequestBody Endereco endereco) throws SQLException {
        return enderecoService.modificar(endereco);
    }
}
