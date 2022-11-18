package com.dh.consultorioOdontologico.service;

import com.dh.consultorioOdontologico.dao.IDao;
import com.dh.consultorioOdontologico.dao.impl.DentistaDao;
import com.dh.consultorioOdontologico.model.Dentista;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DentistaService {

    public Dentista cadastrar(Dentista dentista) throws SQLException{
        IDao<Dentista> dentistaIDao = new DentistaDao();
        return dentistaIDao.cadastrar(dentista);
    }

    public Dentista modificar(Dentista dentista) throws SQLException{
        IDao<Dentista> dentistaIDao = new DentistaDao();
        return dentistaIDao.modificar(dentista);
    }

    public List<Dentista> buscarDentistas() throws SQLException{
        DentistaDao dentistaDao = new DentistaDao();
        return dentistaDao.buscarTodos();
    }

    public List<Dentista> buscarDentistaPorMatricula(Integer matricula) throws SQLException {
        DentistaDao dentistaDao = new DentistaDao();
        return dentistaDao.buscarPorMatricula(matricula);
    }
}
