package com.dh.consultorioOdontologico.controller;

import com.dh.consultorioOdontologico.entity.Dentista;
import com.dh.consultorioOdontologico.entity.dto.DentistaDTO;
import com.dh.consultorioOdontologico.exception.ResourceNotFoundException;
import com.dh.consultorioOdontologico.service.DentistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/dentista")
public class DentistaController {
    @Autowired
    DentistaService dentistaService;

    @GetMapping()
    public List<DentistaDTO> buscar(){
        return dentistaService.buscar();
    }

    @GetMapping("/buscarMatricula/{matricula}")
    public ResponseEntity buscarPorMatricula (@PathVariable int matricula) throws ResourceNotFoundException {
        return dentistaService.buscarPorMatricula(matricula);
    }

    @PostMapping
    public ResponseEntity salvar(@RequestBody @Valid Dentista dentista){
        try{
            Dentista dentistaSalvo = dentistaService.salvar(dentista);
            return new ResponseEntity("Dentista " + dentistaSalvo.getNome() + " cadastrado com sucesso.", HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity("Erro ao cadastrar dentista.", HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping()
    public ResponseEntity patchDentista(@RequestBody @Valid DentistaDTO dentistaDTO) {
        DentistaDTO dentistaDTOModificado = dentistaService.patchDentista((dentistaDTO));
        if (dentistaDTOModificado == null) {
            return new ResponseEntity("Erro ao modificar informações de Dentista.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(dentistaDTOModificado, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity putDentista(@RequestBody @Valid DentistaDTO dentistaDTO) throws ResourceNotFoundException{
        return dentistaService.putDentista(dentistaDTO);
    }

    @DeleteMapping
    public ResponseEntity deletar(@RequestParam("matricula") int matricula) throws ResourceNotFoundException {
        return dentistaService.deletar(matricula);
    }
}