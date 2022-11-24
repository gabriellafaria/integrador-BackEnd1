package com.dh.consultorioOdontologico.service;

import com.dh.consultorioOdontologico.dao.IDao;
import com.dh.consultorioOdontologico.dao.impl.PacienteDao;
import com.dh.consultorioOdontologico.entity.Paciente;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {
    private IDao<Paciente> pacienteIDao = new PacienteDao();

    public Paciente cadastrar(Paciente paciente) throws SQLException {
        return pacienteIDao.cadastrar(paciente);
    }

    public Optional<Paciente> buscarPorId(int id) throws SQLException {
        return pacienteIDao.buscarPorId(id);
    }

    public Paciente modificar(Paciente paciente) throws SQLException {
        System.out.println();
        return pacienteIDao.modificar(paciente);
    }

    public void excluir(Paciente paciente) throws SQLException {
        pacienteIDao.excluir(paciente);
    }

    public List<Paciente> buscarTodos() throws SQLException{
        PacienteDao pacienteDao = new PacienteDao();
        return pacienteDao.buscarTodos();
    }

    public void excluirPorId(int id) throws SQLException {
        PacienteDao pacienteDao = new PacienteDao();
        pacienteDao.excluirPorID(id);
    }

}
