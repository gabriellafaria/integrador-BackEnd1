//package com.dh.consultorioOdontologico.service;
//
//import com.dh.consultorioOdontologico.dao.IDao;
//import com.dh.consultorioOdontologico.dao.impl.DentistaDao;
//import com.dh.consultorioOdontologico.entity.Dentista;
//
//import java.sql.SQLException;
//import java.util.List;
//
//public class DentistaService {
//
//    public Dentista cadastrar(Dentista dentista) throws SQLException{
//        IDao<Dentista> dentistaIDao = new DentistaDao();
//        return dentistaIDao.cadastrar(dentista);
//    }
//
//    public List<Dentista> buscarDentistas() throws SQLException{
//        DentistaDao dentistaDao = new DentistaDao();
//        return dentistaDao.buscarTodos();
//    }
//
//    public List<Dentista> buscarDentistaPorMatricula(Integer matricula) throws SQLException {
//        DentistaDao dentistaDao = new DentistaDao();
//        return dentistaDao.buscarPorMatricula(matricula);
//    }
//}
