package com.dh.consultorioOdontologico.controller;

import com.dh.consultorioOdontologico.entity.dto.ConsultaDTO;
import com.dh.consultorioOdontologico.exception.ResourceNotFoundException;
import com.dh.consultorioOdontologico.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/consulta")
public class ConsultaController {
    @Autowired
    ConsultaService consultaService;

    @PostMapping()
    public ResponseEntity salvarConsulta(@RequestBody @Valid ConsultaDTO consultaDTO){return consultaService.salvar(consultaDTO);}

    @GetMapping("/buscar_todas_consultas")
    public List<ConsultaDTO> buscarTodasConsultas(){return consultaService.buscarTodasConsultas();}

    @GetMapping("/rg_paciente/{rgPaciente}")
    public List<ConsultaDTO> buscarConsultasPaciente(@PathVariable String rgPaciente) throws ResourceNotFoundException { return consultaService.buscarConsultasPaciente(rgPaciente);}

    @GetMapping("/matricula_dentista/{matriculaDentista}")
    public List<ConsultaDTO> buscarConsultasDentista(@PathVariable int matriculaDentista) throws ResourceNotFoundException  { return consultaService.buscarConsultasDentista(matriculaDentista);}

    @DeleteMapping
    public ResponseEntity deletarConsulta(@RequestBody @Valid ConsultaDTO consultaDTO) { return consultaService.deletarConsulta(consultaDTO); }

    @PutMapping
    public ResponseEntity alterarConsulta(@RequestBody @Valid ConsultaDTO consultaDTO) { return  consultaService.alterarConsulta(consultaDTO);}
}
