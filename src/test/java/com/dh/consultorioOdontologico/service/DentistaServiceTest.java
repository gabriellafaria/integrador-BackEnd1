package com.dh.consultorioOdontologico.service;

import com.dh.consultorioOdontologico.entity.Dentista;
import com.dh.consultorioOdontologico.entity.Usuario;
import com.dh.consultorioOdontologico.entity.dto.DentistaDTO;
import com.dh.consultorioOdontologico.exception.ResourceNotFoundException;
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
    ObjectMapper mapper = new ObjectMapper();

    @Test
    void postDentista(){
        Usuario usuario = new Usuario();
        usuario.setUsername("duascaras");
        usuario.setPassword("batma");
        Dentista dentista = new Dentista();
        dentista.setNome("Harvey");
        dentista.setSobrenome("Dent");
        dentista.setMatricula(987654);
        dentista.setUsuario(usuario);
        Dentista dentistaSalvo = service.salvar(dentista);
        Assertions.assertNotNull(dentistaSalvo.getId());
    }

    @Test
    void patchDentista(){
        Usuario usuario = new Usuario();
        usuario.setUsername("semcabeca");
        usuario.setPassword("feriadonacional");
        Dentista dentista = new Dentista();
        dentista.setNome("Tira");
        dentista.setSobrenome("Dentes");
        dentista.setMatricula(123456);
        dentista.setUsuario(usuario);
        service.salvar(dentista);

        String nomeDentista = dentista.getNome();
        dentista.setNome("Arranca");

        DentistaDTO dentistaSalvoDTO = mapper.convertValue(dentista, DentistaDTO.class);
        DentistaDTO dentistaDTOModificado = service.patchDentista(dentistaSalvoDTO);
        Assertions.assertFalse(dentistaDTOModificado.getNome().equals(nomeDentista));
    }

    @Test
    void getAllDentistas(){
        Usuario usuario = new Usuario();
        usuario.setUsername("semcabeca");
        usuario.setPassword("feriadonacional");
        Dentista dentista = new Dentista();
        dentista.setNome("Tira");
        dentista.setSobrenome("Dentes");
        dentista.setMatricula(123456);
        dentista.setUsuario(usuario);
        service.salvar(dentista);

        Usuario usuario2 = new Usuario();
        usuario.setUsername("duascaras");
        usuario.setPassword("batma");
        Dentista dentista2 = new Dentista();
        dentista2.setNome("Harvey");
        dentista2.setSobrenome("Dent");
        dentista2.setMatricula(987654);
        dentista.setUsuario(usuario2);
        service.salvar(dentista2);

        System.out.println(service.buscar());
    }

    @Test
    void getMatriculaDentista() throws ResourceNotFoundException {
        Usuario usuario = new Usuario();
        usuario.setUsername("duascaras");
        usuario.setPassword("batma");
        Dentista dentista = new Dentista();
        dentista.setNome("Harvey");
        dentista.setSobrenome("Dent");
        dentista.setMatricula(987654);
        dentista.setUsuario(usuario);
        service.salvar(dentista);

        service.buscarPorMatricula(dentista.getMatricula());
    }

    @Test
    void PutDentista() throws ResourceNotFoundException{
        Usuario usuario = new Usuario();
        usuario.setUsername("semcabeca");
        usuario.setPassword("feriadonacional");
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
    }

    @Test
    void DeleteDentista() throws ResourceNotFoundException {
        Usuario usuario = new Usuario();
        usuario.setUsername("duascaras");
        usuario.setPassword("batma");
        Dentista dentista = new Dentista();
        dentista.setNome("Harvey");
        dentista.setSobrenome("Dent");
        dentista.setMatricula(987654);
        service.salvar(dentista);

        service.deletar(dentista.getMatricula());

        Assertions.assertTrue(service.buscar().isEmpty());
    }
}
