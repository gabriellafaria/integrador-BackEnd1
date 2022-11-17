package com.dh.consultorioOdontologico.service;

import com.dh.consultorioOdontologico.dao.IDao;
import com.dh.consultorioOdontologico.dao.impl.PacienteDao;
import com.dh.consultorioOdontologico.model.Paciente;

import java.sql.SQLException;
import java.util.List;

public class PacienteService {
    private IDao<Paciente> pacienteIDao = new PacienteDao();

    public Paciente cadastrar(Paciente paciente) throws SQLException {
        return pacienteIDao.cadastrar(paciente);
    }

    public Paciente modificar(Paciente paciente) throws SQLException {
        return pacienteIDao.modificar(paciente);
    }

    public void excluir(Paciente paciente) throws SQLException {
        pacienteIDao.excluir(paciente);
    }

    public List<Paciente> buscarTodos() throws SQLException{
        PacienteDao pacienteDao = new PacienteDao();
        return pacienteDao.buscarTodos();
    }

}
