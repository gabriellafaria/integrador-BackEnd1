package com.dh.consultorioOdontologico.controller;

import com.dh.consultorioOdontologico.entity.dto.EnderecoDTO;
import com.dh.consultorioOdontologico.entity.Endereco;
import com.dh.consultorioOdontologico.service.EnderecoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/endereco")
public class EnderecoController {
    @Autowired
    EnderecoService enderecoService;

    @PostMapping()
    public ResponseEntity salvar(@RequestBody Endereco endereco) {
        return enderecoService.salvarEndereco(endereco);
    }

    @GetMapping("/buscar")
    public List<EnderecoDTO> buscarEnderecos(){
        return enderecoService.buscarTodosEnderecos();
    }

    @GetMapping()
    public ResponseEntity buscarPorId(@RequestParam("id") Long id){
        return enderecoService.buscarEnderecoPorId(id);
    }

    @PatchMapping()
    public ResponseEntity alterarParcial(@RequestBody Endereco endereco){
        return enderecoService.alterarParcial(endereco);
    }


    @DeleteMapping()
    public ResponseEntity deletarEndereco(@RequestParam("id") Long id){
        return enderecoService.deletarEndereco(id);
    }

}
