package com.dh.consultorioOdontologico.service;

import com.dh.consultorioOdontologico.entity.Dentista;
import com.dh.consultorioOdontologico.entity.dto.DentistaDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DentistaServiceTest {

    @Autowired
    DentistaService service;
    ObjectMapper mapper = new ObjectMapper();

    @Test
    void salvar(){
        Dentista dentista = new Dentista();
        dentista.setNome("Tira");
        dentista.setSobrenome("Dentes");
        dentista.setMatricula(123456);

        Dentista dentistaSalvo = service.salvar(dentista);

        System.out.println(dentista.getId());
        Assertions.assertNotNull(dentistaSalvo.getId());
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
    }


}
