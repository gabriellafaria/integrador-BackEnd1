package com.dh.consultorioOdontologico.controller;

import com.dh.consultorioOdontologico.entity.dto.EnderecoDTO;
import com.dh.consultorioOdontologico.entity.Endereco;
import com.dh.consultorioOdontologico.exception.Exceptions;
import com.dh.consultorioOdontologico.service.EnderecoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/endereco")
public class EnderecoController {
    @Autowired
    EnderecoService enderecoService;

    @PostMapping()
    public ResponseEntity salvar(@RequestBody @Valid EnderecoDTO endereco) throws Exceptions {
        try {
            Endereco enderecoSalvo = enderecoService.salvar(endereco);
            return new ResponseEntity("Endereco " + enderecoSalvo.getId() + " cadastrado com sucesso.", HttpStatus.CREATED);
        }catch (Exception e) {
            throw new Exceptions("Não foi possível cadastrar o endereço.");
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity buscarEnderecos(){
        try {
            List<EnderecoDTO> buscarTodosEnderecos = enderecoService.buscarTodosEnderecos();
            return new ResponseEntity(buscarTodosEnderecos, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity("Não foi possível localizar os endereços.", HttpStatus.NOT_FOUND);

        }
    }

    @GetMapping()
    public ResponseEntity buscarPorId(@RequestParam("id") Long id){
        try {
            Optional<Endereco> pesquisarPorId = enderecoService.buscarEnderecoPorId(id);
            return new ResponseEntity(pesquisarPorId, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity("O endereço informado não existe.", HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping()
    public ResponseEntity alterarParcial(@RequestBody @Valid EnderecoDTO enderecoDTO){
        try {
        EnderecoDTO enderecoParc = enderecoService.alterarParcial(enderecoDTO);
        return new ResponseEntity(enderecoParc, HttpStatus.CREATED);
        //return new ResponseEntity("Endereço com o id " + enderecoParc.getId() + "alterado parcialmente com sucesso.", HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity("Não foi possível alterar parcialmente o endereço ", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping()
    public ResponseEntity alterarTotal(@RequestBody @Valid EnderecoDTO enderecoDTO){
        try{
        EnderecoDTO enderecoAlterado = enderecoService.alterarTotal(enderecoDTO);
        return new ResponseEntity(enderecoAlterado, HttpStatus.OK);
        //return new ResponseEntity("Endereço atualizado com sucesso | Rua: " + enderecoAlterado.getRua() + " - Numero: " + enderecoAlterado.getNumero() + " - Cidade: " + enderecoAlterado.getCidade() + " - Estado: " + enderecoAlterado.getEstado(), HttpStatus.CREATED);
        }catch (Exception e) {
            return new ResponseEntity("Não foi possível atualizar o endereço.", HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping()
    public ResponseEntity deletarEndereco(@RequestParam("id") Long id){
        try {
            Optional<Endereco> endereco = enderecoService.deletarEndereco(id);
            return new ResponseEntity("Endereço deletado com sucesso.", HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity("Não foi possível deletar o endereço.", HttpStatus.NOT_FOUND);
        }
    }

}
