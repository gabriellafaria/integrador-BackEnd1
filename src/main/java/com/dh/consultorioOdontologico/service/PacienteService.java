package com.dh.consultorioOdontologico.service;

import com.dh.consultorioOdontologico.entity.Paciente;
import com.dh.consultorioOdontologico.entity.dto.PacienteDTO;
import com.dh.consultorioOdontologico.repository.PacienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {
    @Autowired
    PacienteRepository pacienteRepository;

    public List<PacienteDTO> buscar(){
        List<Paciente> pacienteList = pacienteRepository.findAll();
        List<PacienteDTO> pacienteDTOList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        for(Paciente paciente: pacienteList){
            pacienteDTOList.add(mapper.convertValue(paciente, PacienteDTO.class));
        }
        return pacienteDTOList;
    }

    public ResponseEntity salvar(Paciente paciente){
        try{
            paciente.setDataRegistro(Timestamp.from(Instant.now()));
            Paciente pacienteSalvo = pacienteRepository.save(paciente);
            return new ResponseEntity("Paciente " + pacienteSalvo.getNome() + " criado com sucesso!", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao cadastrar o paciente", HttpStatus.BAD_REQUEST);
        }
    }

    //e se já fizer direto pelo rg??? by Sa
    public ResponseEntity deletar(Long id){
       Optional<Paciente> paciente = pacienteRepository.findById(id);
       if(paciente.isEmpty())
           return new ResponseEntity<>("Id do paciente não existe!", HttpStatus.BAD_REQUEST);

       pacienteRepository.deleteById(id);
        return new ResponseEntity("Paciente delatado com sucesso!", HttpStatus.OK);
    }

    public ResponseEntity buscarPorRg(String rg) {
        ObjectMapper mapper = new ObjectMapper();
        Optional<Paciente> paciente = Optional.ofNullable(pacienteRepository.findByRg(rg));
        if(paciente.isEmpty())
            return new ResponseEntity("Paciente com o rg " + rg + " não encontrado", HttpStatus.BAD_REQUEST);

        //Acho que aqui o status é "OK" - by Sa
        PacienteDTO pacienteDTO = mapper.convertValue(paciente.get(), PacienteDTO.class);
        return new ResponseEntity(pacienteDTO, HttpStatus.CREATED);
    }

    /*private IDao<Paciente> pacienteIDao = new PacienteDao();

    public Paciente cadastrar(Paciente paciente) throws SQLException {
        return pacienteIDao.cadastrar(paciente);
    }

    public Optional<Paciente> buscarPorId(int id) throws SQLException {
        return pacienteIDao.buscarPorId(id);
    }

    public Paciente modificar(Paciente paciente) throws SQLException {
        System.out.println();
        return pacienteIDao.modificar(paciente);
    }

    public void excluir(Paciente paciente) throws SQLException {
        pacienteIDao.excluir(paciente);
    }

    public List<Paciente> buscarTodos() throws SQLException{
        PacienteDao pacienteDao = new PacienteDao();
        return pacienteDao.buscarTodos();
    }

    public void excluirPorId(int id) throws SQLException {
        PacienteDao pacienteDao = new PacienteDao();
        pacienteDao.excluirPorID(id);
    }*/

}