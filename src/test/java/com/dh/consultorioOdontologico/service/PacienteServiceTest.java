package com.dh.consultorioOdontologico.service;

import com.dh.consultorioOdontologico.entity.Paciente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

class PacienteServiceTest {
    PacienteService pacienteService = new PacienteService();

    @Test
    public void buscar() throws SQLException {
        List<Paciente> pacientes = pacienteService.buscarTodos();
        Assertions.assertEquals(21, pacientes.size());
    }
}