package com.dh.consultorioOdontologico.service;

import com.dh.consultorioOdontologico.model.Dentista;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DentistaServiceTest {

    //Dentista felipe = new Dentista("Felipe", "Stefani", 123);
    //Dentista gabriella = new Dentista("Gabriella", "Faria", 456);
   // Dentista evertin = new Dentista("Evertin", "Silva", 71);
    DentistaService dentistaService = new DentistaService();

    //@Test
    /*public void cadastrarDentista() throws SQLException {
        dentistaService.cadastrar(felipe);
        dentistaService.cadastrar(gabriella);
        dentistaService.cadastrar(evertin);
    }*/

    @Test
    public void buscarDentistas() throws SQLException {
        dentistaService.buscarDentistas();
    }

    @Test
    public void buscarDentistaPorMatricula() throws SQLException {
        dentistaService.buscarDentistaPorMatricula(71);
    }

    @Test
    public void excluirPorId() throws SQLException {
        dentistaService.excluirPorId(26);
    }
}