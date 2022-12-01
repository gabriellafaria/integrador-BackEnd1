package com.dh.consultorioOdontologico.service;

import com.dh.consultorioOdontologico.entity.Dentista;
import com.dh.consultorioOdontologico.entity.dto.DentistaDTO;
import com.dh.consultorioOdontologico.repository.DentistaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Validated
@Service
public class DentistaService {
    @Autowired
    DentistaRepository dentistaRepository;

    public List<DentistaDTO> buscar(){
        List<Dentista> dentistaList = dentistaRepository.findAll();
        List<DentistaDTO> dentistaDTOList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        for(Dentista dentista: dentistaList){
            dentistaDTOList.add(mapper.convertValue(dentista, DentistaDTO.class));
        }
        return dentistaDTOList;
    }

    public ResponseEntity salvar(Dentista dentista){
        try{
            Dentista dentistaSalvo = dentistaRepository.save(dentista);
            return new ResponseEntity("Dentista " + dentistaSalvo.getNome() + " criado com sucesso!", HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity("Erro ao cadastrar Dentista", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity patchDentista(DentistaDTO dentistaDTO){
        ObjectMapper mapper = new ObjectMapper();
        Optional<Dentista> dentistaOptional = Optional.ofNullable(dentistaRepository.findByMatricula(dentistaDTO.getMatricula()));
        if(dentistaOptional.isEmpty()){
            return new ResponseEntity("Matrícula inexistente.", HttpStatus.NOT_FOUND);
        }
        Dentista dentista = dentistaOptional.get();
        if (dentistaDTO.getNome() != null){
            dentista.setNome(dentistaDTO.getNome());
        }
        if (dentistaDTO.getSobrenome() != null) {
            dentista.setSobrenome(dentistaDTO.getSobrenome());
        }

        DentistaDTO dentistaModificado = mapper.convertValue(dentistaRepository.save(dentista), DentistaDTO.class);

        return new ResponseEntity(dentistaModificado, HttpStatus.OK);
    }

   public ResponseEntity buscarPorMatricula(int matricula){
        ObjectMapper mapper = new ObjectMapper();
        Optional<Dentista> dentista = Optional.ofNullable(dentistaRepository.findByMatricula(matricula));
        if(dentista.isEmpty())
            return new ResponseEntity("Dentista com matrícula " + matricula + " não encontrado.", HttpStatus.NOT_FOUND);

        DentistaDTO dentistaDTO = mapper.convertValue(dentista.get(), DentistaDTO.class);
        return new ResponseEntity(dentistaDTO, HttpStatus.CREATED);
   }

    public ResponseEntity deletar(int matricula){
        Optional<Dentista> dentista = Optional.ofNullable(dentistaRepository.findByMatricula(matricula));
        if(dentista.isEmpty())
            return new ResponseEntity<>("Matrícula inexistente.", HttpStatus.BAD_REQUEST);

        dentistaRepository.deleteById(dentista.get().getId());
        return new ResponseEntity("Dentista deletado com sucesso!", HttpStatus.OK);
    }
}
