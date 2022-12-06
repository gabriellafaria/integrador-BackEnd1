package com.dh.consultorioOdontologico.controller;

import com.dh.consultorioOdontologico.entity.Paciente;
import com.dh.consultorioOdontologico.entity.dto.EnderecoDTO;
import com.dh.consultorioOdontologico.entity.dto.PacienteDTO;
import com.dh.consultorioOdontologico.service.EnderecoService;
import com.dh.consultorioOdontologico.service.PacienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/paciente")
public class PacienteController {
    @Autowired
    PacienteService pacienteService;

    @Autowired
    EnderecoService enderecoService;

    @GetMapping()
    public List<PacienteDTO> buscar(){
        return pacienteService.buscar();
    }

    @GetMapping("/buscarRg/{rg}")
    public ResponseEntity buscarPorRg (@PathVariable String rg){
        return pacienteService.buscarPorRg(rg);
    }

    @PostMapping
    public ResponseEntity salvar(@RequestBody @Valid PacienteDTO pacienteDTO){
        ObjectMapper mapper = new ObjectMapper();
        try{
            Paciente pacienteSalvo = pacienteService.salvar(pacienteDTO);
            return new ResponseEntity("Paciente " + pacienteSalvo.getNome() + " criado com sucesso!", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("Erro ao cadastrar o paciente", HttpStatus.BAD_REQUEST);
        }
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