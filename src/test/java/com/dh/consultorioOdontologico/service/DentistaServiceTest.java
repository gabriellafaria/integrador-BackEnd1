package com.dh.consultorioOdontologico.service;

import com.dh.consultorioOdontologico.entity.Dentista;
import com.dh.consultorioOdontologico.entity.dto.DentistaDTO;
import com.dh.consultorioOdontologico.exception.ResourceNotFoundException;
import com.dh.consultorioOdontologico.repository.DentistaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class DentistaServiceTest {

    @Autowired
    DentistaService service;
    DentistaRepository repository;
    ObjectMapper mapper = new ObjectMapper();

    @Test
    void postDentista(){
        Dentista dentista = new Dentista();
        dentista.setNome("Harvey");
        dentista.setSobrenome("Dent");
        dentista.setMatricula(987654);
        Dentista dentistaSalvo = service.salvar(dentista);
        Assertions.assertNotNull(dentistaSalvo.getId());

        //System.out.println("Dentista " + dentistaSalvo.getNome() + " " + dentistaSalvo.getSobrenome() + " cadastrado com sucesso!");
    }

    @Test
    void patchDentista(){
        Dentista dentista = new Dentista();
        dentista.setNome("Tira");
        dentista.setSobrenome("Dentes");
        dentista.setMatricula(123456);
        service.salvar(dentista);

        String nomeDentista = dentista.getNome();
        dentista.setNome("Arranca");

        DentistaDTO dentistaSalvoDTO = mapper.convertValue(dentista, DentistaDTO.class);
        DentistaDTO dentistaDTOModificado = service.patchDentista(dentistaSalvoDTO);
        Assertions.assertFalse(dentistaDTOModificado.getNome().equals(nomeDentista));

        //System.out.println("Informação modificada com sucesso: " + dentistaDTOModificado.getNome());
    }

    @Test
    void getAllDentistas(){
        Dentista dentista = new Dentista();
        dentista.setNome("Tira");
        dentista.setSobrenome("Dentes");
        dentista.setMatricula(123456);
        service.salvar(dentista);

        Dentista dentista2 = new Dentista();
        dentista2.setNome("Harvey");
        dentista2.setSobrenome("Dent");
        dentista2.setMatricula(987654);
        service.salvar(dentista2);

        System.out.println(service.buscar());
    }

    @Test
    void getMatriculaDentista() throws ResourceNotFoundException {
        Dentista dentista = new Dentista();
        dentista.setNome("Harvey");
        dentista.setSobrenome("Dent");
        dentista.setMatricula(987654);
        service.salvar(dentista);

        service.buscarPorMatricula(dentista.getMatricula());

        //System.out.println(service.buscarPorMatricula(dentista.getMatricula()));
    }

    @Test
    void PutDentista() throws ResourceNotFoundException{
        Dentista dentista = new Dentista();
        dentista.setNome("Tira");
        dentista.setSobrenome("Dentes");
        dentista.setMatricula(123456);
        service.salvar(dentista);

        String nomeDentista = dentista.getNome();
        dentista.setNome("Arranca");
        String sobrenomeDentista = dentista.getSobrenome();
        dentista.setSobrenome("Sisos");

        DentistaDTO dentistaSalvoDTO = mapper.convertValue(dentista, DentistaDTO.class);
        ResponseEntity dentistaDTOModificado = service.putDentista(dentistaSalvoDTO);

        Assertions.assertFalse(dentistaDTOModificado.equals(nomeDentista));
        Assertions.assertFalse(dentistaDTOModificado.equals(sobrenomeDentista));

        System.out.println("Informações do dentista " + dentista.getNome() + " " + dentista.getSobrenome() + " modificadas com sucesso.");
    }

    @Test
    void DeleteDentista() throws ResourceNotFoundException {
        Dentista dentista = new Dentista();
        dentista.setNome("Harvey");
        dentista.setSobrenome("Dent");
        dentista.setMatricula(987654);
        service.salvar(dentista);

        service.deletar(dentista.getMatricula());

        Assertions.assertTrue(service.buscar().isEmpty());

       System.out.println("Dentista " + dentista.getNome() + " deletado com sucesso.");
    }
}
