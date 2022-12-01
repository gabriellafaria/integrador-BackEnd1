package com.dh.consultorioOdontologico.controller;

import com.dh.consultorioOdontologico.entity.dto.ConsultaDTO;
import com.dh.consultorioOdontologico.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/consulta")
public class ConsultaController {
    @Autowired
    ConsultaService consultaService;

    @PostMapping()
    public ResponseEntity salvarConsulta(@RequestBody @Valid ConsultaDTO consultaDTO){
        return consultaService.salvar(consultaDTO);
    }

    @GetMapping("/buscar_todas_consultas")
    public List<ConsultaDTO> buscarTodasConsultas(){
        return consultaService.buscarTodasConsultas();
    }

    @DeleteMapping
    public ResponseEntity deletar(@RequestBody @Valid ConsultaDTO consultaDTO) { return consultaService.deletar(consultaDTO); }

}
