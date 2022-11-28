package com.dh.consultorioOdontologico.controller;

import com.dh.consultorioOdontologico.entity.Consulta;
import com.dh.consultorioOdontologico.entity.dto.ConsultaDTO;
import com.dh.consultorioOdontologico.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consulta")
public class ConsultaController {
    @Autowired
    ConsultaService service;

    @PostMapping()
    public ResponseEntity salvar(@RequestBody Consulta consulta){
        return service.salvar(consulta);
    }

    @GetMapping("/buscar_todas_consultas")
    public List<ConsultaDTO> buscarTodasConsultas(){
        return service.buscarTodasConsultas();
    }

//    @GetMapping("/buscar_por_rg/{rgPaciente}")
//    public List<ConsultaDTO> buscarConsultaPorRg(@PathVariable String rgPaciente){
//        return service.findConsultByRg(rgPaciente);
//    }

}
