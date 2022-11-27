package com.dh.consultorioOdontologico.controller;

import com.dh.consultorioOdontologico.entity.Dentista;
import com.dh.consultorioOdontologico.entity.dto.DentistaDTO;
import com.dh.consultorioOdontologico.service.DentistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity buscarPorMatricula (@PathVariable int matricula){
        return dentistaService.buscarPorMatricula(matricula);
    }

    @PostMapping
    public ResponseEntity salvar (@RequestBody Dentista dentista){
        return dentistaService.salvar(dentista);
    }

    @DeleteMapping
    public ResponseEntity deletar(@RequestParam("matricula") int matricula){
        return dentistaService.deletar(matricula);
    }

}