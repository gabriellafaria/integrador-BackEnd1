package com.dh.consultorioOdontologico.service;

import com.dh.consultorioOdontologico.entity.Dentista;
import com.dh.consultorioOdontologico.entity.dto.DentistaDTO;
import com.dh.consultorioOdontologico.repository.DentistaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
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

    static final Logger log = Logger.getLogger(DentistaService.class);

    public List<DentistaDTO> buscar(){
        log.info("Buscando dentistas...");
        List<Dentista> dentistaList = dentistaRepository.findAll();
        List<DentistaDTO> dentistaDTOList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        for(Dentista dentista: dentistaList){
            dentistaDTOList.add(mapper.convertValue(dentista, DentistaDTO.class));
        }
        log.info("Exibindo todos os registros de Dentistas");
        return dentistaDTOList;
    }

    public ResponseEntity salvar(Dentista dentista){
        try{
            log.info("Cadastrando novo Dentista...");
            Dentista dentistaSalvo = dentistaRepository.save(dentista);
            log.info("Novo Dentista cadastrado");
            return new ResponseEntity("Dentista " + dentistaSalvo.getNome() + " cadastrado com sucesso!", HttpStatus.CREATED);
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

        log.info("Informações do Dentista com matrícula: " + dentista.getMatricula() + ", alteradas com sucesso.");
        return new ResponseEntity(dentistaModificado, HttpStatus.OK);
    }

    public ResponseEntity putDentista(DentistaDTO dentistaDTO){
        ObjectMapper mapper = new ObjectMapper();
        log.info("Localizando Dentista com matrícula " + dentistaDTO.getMatricula() + "...");
        Optional<Dentista> dentistaOptional = Optional.ofNullable(dentistaRepository.findByMatricula(dentistaDTO.getMatricula()));
        if(dentistaOptional.isEmpty())
            return new ResponseEntity("Matrícula inexistente", HttpStatus.NOT_FOUND);
        Dentista dentista = dentistaOptional.get();
        if (dentistaDTO.getNome() != null)
            dentista.setNome(dentistaDTO.getNome());
        if (dentistaDTO.getSobrenome() != null)
            dentista.setSobrenome(dentistaDTO.getSobrenome());

        DentistaDTO dentistaEditado = mapper.convertValue(dentistaRepository.save(dentista), DentistaDTO.class);
        log.info("Cadastro do Dentista com matrícula: " + dentista.getMatricula() + ", alterado com sucesso.");
        return new ResponseEntity(dentistaEditado, HttpStatus.OK);
    }

   public ResponseEntity buscarPorMatricula(int matricula){
        log.info("Buscando Dentista com matrícula " + matricula + "...");
        ObjectMapper mapper = new ObjectMapper();
        Optional<Dentista> dentista = Optional.ofNullable(dentistaRepository.findByMatricula(matricula));
        if(dentista.isEmpty())
            return new ResponseEntity("Dentista com matrícula " + matricula + " não encontrado.", HttpStatus.NOT_FOUND);

        DentistaDTO dentistaDTO = mapper.convertValue(dentista.get(), DentistaDTO.class);
        log.info("Dentista localizado com sucesso.");
        return new ResponseEntity(dentistaDTO, HttpStatus.CREATED);
   }

    public ResponseEntity deletar(int matricula){
        log.info("Localizando Dentista com matrícula " + matricula + "...");
        Optional<Dentista> dentista = Optional.ofNullable(dentistaRepository.findByMatricula(matricula));
        if(dentista.isEmpty())
            return new ResponseEntity<>("Matrícula inexistente.", HttpStatus.BAD_REQUEST);

        dentistaRepository.deleteById(dentista.get().getId());
        return new ResponseEntity("Dentista deletado com sucesso!", HttpStatus.OK);
    }
}
