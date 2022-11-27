package com.dh.consultorioOdontologico.controller;

import com.dh.consultorioOdontologico.entity.Paciente;
import com.dh.consultorioOdontologico.entity.dto.PacienteDTO;
import com.dh.consultorioOdontologico.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity salvar(@RequestBody Paciente paciente){
        return pacienteService.salvar(paciente);
    }

    //e se já fizer direto pelo rg??? by Sa
    @DeleteMapping()
    public ResponseEntity deletar(@RequestParam("id") Long id){
        return pacienteService.deletar(id);
    }

/*    @Autowired
    private ModelMapper modelMapper;

    @GetMapping()
    public List<Paciente> buscarTodos() throws SQLException {
        return pacienteService.buscarTodos();
    }

    @GetMapping("/{id}")
    public Optional<Paciente> buscarPacientePorId(@PathVariable("id") int id) throws SQLException {
        return pacienteService.buscarPorId(id);
    }

    @DeleteMapping()
    public void excluirPaciente(@RequestBody Paciente paciente) throws SQLException {
        pacienteService.excluir(paciente);

    }

    @PutMapping()
    public Paciente alterarPaciente(@RequestBody Paciente paciente) throws SQLException {  //Felipe comment: acho que nao precisa passar o id como PathVariable, pq podemos retirar o id do paciente direto
        return pacienteService.modificar(paciente);
    }

    @PostMapping()
    public Paciente post(@RequestBody Paciente paciente) throws SQLException {
        return pacienteService.cadastrar(paciente);
    }


    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarPacienteParcial(@PathVariable("id") int id, @RequestBody Paciente paciente) throws SQLException {
            pacienteService.buscarPorId(id).map(pacienteBase -> {
            modelMapper.map(paciente, pacienteBase);
            return Void.TYPE;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado"));
    }*/
}