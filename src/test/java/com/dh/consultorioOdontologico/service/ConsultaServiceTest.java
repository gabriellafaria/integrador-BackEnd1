package com.dh.consultorioOdontologico.service;

import com.dh.consultorioOdontologico.model.Consulta;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ConsultaServiceTest {
    ConsultaService consultaService = new ConsultaService();

    @Test
    public void modificar() throws SQLException {
        Consulta consulta = new Consulta(12, 12, LocalDateTime.of(2023, 03, 01, 10,0));
        consultaService.cadastrar(consulta);
        consulta.setIdPaciente(15);
        consulta.setDataConsulta(LocalDateTime.now());
        consultaService.modificar(consulta);


    }

    @Test
    public void busca() throws SQLException {
        consultaService.buscarTodos();
    }

}