package com.dh.consultorioOdontologico.service;

import com.dh.consultorioOdontologico.dao.IDao;
import com.dh.consultorioOdontologico.dao.impl.DentistaDao;
import com.dh.consultorioOdontologico.model.Dentista;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class DentistaService {
    IDao<Dentista> dentistaIDao = new DentistaDao();
    DentistaDao dentistaDao = new DentistaDao();

    public Dentista cadastrar(Dentista dentista) throws SQLException{
        return dentistaIDao.cadastrar(dentista);
    }

    public Dentista modificar(Dentista dentista) throws SQLException{
        return dentistaIDao.modificar(dentista);
    }

    public List<Dentista> buscarDentistas() throws SQLException{
        return dentistaDao.buscarTodos();
    }

    public List<Dentista> buscarDentistaPorMatricula(Integer matricula) throws SQLException {
        return dentistaDao.buscarPorMatricula(matricula);
    }

    public Optional<Dentista> buscarPorId(int id) throws SQLException {
        DentistaDao dentistaDao = new DentistaDao();
        return dentistaDao.buscarPorId(id);
    }
    public void excluirPorId(int id) throws SQLException {
        dentistaDao.excluirPorID(id);

    }
}
