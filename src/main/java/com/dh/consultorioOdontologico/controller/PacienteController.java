package com.dh.consultorioOdontologico.controller;

import com.dh.consultorioOdontologico.entity.Paciente;
import com.dh.consultorioOdontologico.entity.dto.PacienteDTO;
import com.dh.consultorioOdontologico.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/paciente")
public class PacienteController {
    @Autowired
    PacienteService pacienteService;

    @GetMapping()
    public List<PacienteDTO> buscar(){
        return pacienteService.buscar();
    }

    @GetMapping("/buscarRg/{rg}")
    public ResponseEntity buscarPorRg (@PathVariable String rg){
        return pacienteService.buscarPorRg(rg);
    }

    @PostMapping
    public ResponseEntity salvar(@RequestBody PacienteDTO paciente){
        return pacienteService.salvar(paciente);
    }

    @DeleteMapping()
    public ResponseEntity deletar(@RequestParam("rg") String rg){
        return pacienteService.deletar(rg);
    }

    @PatchMapping()
    public ResponseEntity alterarParcialmente(@RequestBody @Valid PacienteDTO pacienteDTO){
        return pacienteService.alterarParcialmente(pacienteDTO);
    }

    @PutMapping()
    public ResponseEntity alterarTudo(@RequestBody @Valid PacienteDTO pacienteDTO){
        return pacienteService.alterarTudo(pacienteDTO);
    }
}