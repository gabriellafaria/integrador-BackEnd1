package com.dh.consultorioOdontologico.service;

import com.dh.consultorioOdontologico.entity.Dentista;
import com.dh.consultorioOdontologico.entity.dto.DentistaDTO;
import com.dh.consultorioOdontologico.exception.ResourceNotFoundException;
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

    public Dentista salvar(Dentista dentista){
            log.info("Cadastrando novo Dentista...");
            Dentista dentistaSalvo = dentistaRepository.save(dentista);
            log.info("Novo Dentista cadastrado");
            return dentistaSalvo;
    }

    public DentistaDTO patchDentista(DentistaDTO dentistaDTO){
        ObjectMapper mapper = new ObjectMapper();
        Optional<Dentista> dentistaOptional = Optional.ofNullable(dentistaRepository.findByMatricula(dentistaDTO.getMatricula()));
        DentistaDTO dentistaModificado = null;
        if(dentistaOptional.isEmpty()){
            return dentistaModificado;
        }
        Dentista dentista = dentistaOptional.get();
        if (dentistaDTO.getNome() != null){
            dentista.setNome(dentistaDTO.getNome());
        }
        if (dentistaDTO.getSobrenome() != null) {
            dentista.setSobrenome(dentistaDTO.getSobrenome());
        }

         dentistaModificado = mapper.convertValue(dentistaRepository.save(dentista), DentistaDTO.class);

        log.info("Informações do Dentista com matrícula: " + dentista.getMatricula() + ", alteradas com sucesso.");
        return dentistaModificado;
    }

    public ResponseEntity putDentista(DentistaDTO dentistaDTO) throws ResourceNotFoundException {
        ObjectMapper mapper = new ObjectMapper();
        log.info("Localizando Dentista com matrícula " + dentistaDTO.getMatricula() + "...");
        Optional<Dentista> dentistaOptional = Optional.ofNullable(dentistaRepository.findByMatricula(dentistaDTO.getMatricula()));
        if(dentistaOptional.isEmpty())
            throw new ResourceNotFoundException("Matrícula inexistente.");

        Dentista dentista = dentistaOptional.get();
        if (dentistaDTO.getNome() != null)
            dentista.setNome(dentistaDTO.getNome());
        if (dentistaDTO.getSobrenome() != null)
            dentista.setSobrenome(dentistaDTO.getSobrenome());

        DentistaDTO dentistaEditado = mapper.convertValue(dentistaRepository.save(dentista), DentistaDTO.class);
        log.info("Cadastro do Dentista com matrícula: " + dentista.getMatricula() + ", alterado com sucesso.");
        return new ResponseEntity(dentistaEditado, HttpStatus.OK);
    }

   public ResponseEntity buscarPorMatricula(int matricula) throws ResourceNotFoundException {
        log.info("Buscando Dentista com matrícula " + matricula + "...");
        ObjectMapper mapper = new ObjectMapper();
        Optional<Dentista> dentista = Optional.ofNullable(dentistaRepository.findByMatricula(matricula));
             if(dentista.isEmpty())
                 throw new ResourceNotFoundException("Matrícula inexistente");

        DentistaDTO dentistaDTO = mapper.convertValue(dentista.get(), DentistaDTO.class);
        log.info("Dentista localizado com sucesso.");
        return new ResponseEntity(dentistaDTO, HttpStatus.CREATED);
   }

    public ResponseEntity deletar(int matricula) throws ResourceNotFoundException {
        log.info("Localizando Dentista com matrícula " + matricula + "...");
        Optional<Dentista> dentista = Optional.ofNullable(dentistaRepository.findByMatricula(matricula));
        if(dentista.isEmpty())
            throw new ResourceNotFoundException("Matrícula inexistente.");

        dentistaRepository.deleteById(dentista.get().getId());
        return new ResponseEntity("Dentista deletado com sucesso!", HttpStatus.OK);
    }
}
