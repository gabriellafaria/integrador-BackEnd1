package com.dh.consultorioOdontologico.controller;

import com.dh.consultorioOdontologico.entity.Paciente;
import com.dh.consultorioOdontologico.entity.dto.PacienteDTO;
import com.dh.consultorioOdontologico.exception.Exceptions;
import com.dh.consultorioOdontologico.exception.ResourceNotFoundException;
import com.dh.consultorioOdontologico.service.EnderecoService;
import com.dh.consultorioOdontologico.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
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
    public ResponseEntity buscarPorRg (@PathVariable String rg) throws ResourceNotFoundException {
        PacienteDTO paciente = pacienteService.buscarPorRg(rg);
        if(paciente == null)
            throw new ResourceNotFoundException("Paciente com o rg " + rg + " não foi encontrado.");
            //return new ResponseEntity("Paciente com o rg " + rg + " não foi encontrado.", HttpStatus.NOT_FOUND);
        return new ResponseEntity(paciente, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity salvar(@RequestBody @Valid PacienteDTO pacienteDTO) throws Exceptions {
        try{
            Paciente pacienteSalvo = pacienteService.salvar(pacienteDTO);
            return new ResponseEntity("Paciente " + pacienteSalvo.getNome() + " criado com sucesso!", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exceptions("Erro ao cadastrar o paciente");
            //return new ResponseEntity("Erro ao cadastrar o paciente", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping()
    public ResponseEntity deletar(@RequestParam("rg") String rg) throws ResourceNotFoundException {
        Optional<Paciente> paciente = pacienteService.deletar(rg);
        if (paciente == null)
            throw new ResourceNotFoundException("Paciente não localizado.");
            //return new ResponseEntity("Paciente não localizado.", HttpStatus.NOT_FOUND);
        return new ResponseEntity("Paciente delatado com sucesso!", HttpStatus.OK);
    }

    @PatchMapping()
    public ResponseEntity alterarParcialmente(@RequestBody @Valid PacienteDTO pacienteDTO) throws Exceptions {
        PacienteDTO pacienteAlt = pacienteService.alterarParcialmente(pacienteDTO);
        if(pacienteAlt == null)
            //return new ResponseEntity("Não existe o paciente com o rg " + pacienteDTO.getRg(), HttpStatus.NOT_FOUND);
            throw new Exceptions("Não existe o paciente com o rg " + pacienteDTO.getRg() + ", abortando a operação.");
        return new ResponseEntity(pacienteAlt, HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity alterarTudo(@RequestBody @Valid PacienteDTO pacienteDTO) throws Exceptions {
        PacienteDTO pacienteAlt = pacienteService.alterarTudo(pacienteDTO);
        if(pacienteAlt == null)
            throw new Exceptions("Não existe o paciente com o rg " + pacienteDTO.getRg() + ", abortando a operação.");
            //return new ResponseEntity("Não existe o paciente com o rg " + pacienteDTO.getRg(), HttpStatus.NOT_FOUND);
        return new ResponseEntity(pacienteAlt, HttpStatus.CREATED);
    }
}