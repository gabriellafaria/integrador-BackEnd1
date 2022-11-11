package com.dh.consultorioOdontologico.dao.impl;

import com.dh.consultorioOdontologico.dao.IDao;
import com.dh.consultorioOdontologico.model.Dentista;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DentistaDao implements IDao<Dentista> {

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:~/dentista;AUTO_SERVER=TRUE;INIT=RUNSCRIPT FROM 'create.sql'", "sa", "");
    }

    static final Logger logger = Logger.getLogger(DentistaDao.class);

    @Override
    public Dentista cadastrar(Dentista dentista) {
        String DENTISTA_INSERT = String.format("INSERT INTO Dentista(nome, sobrenome, matricula) VALUES('%s', '%s', '%s')", dentista.getNome(), dentista.getSobrenome(), dentista.getMatricula());
        return null;
    }

    @Override
    public Dentista modificar(Dentista dentista) {
        return null;
    }

    @Override
    public Dentista excluir(Dentista dentista) {
        return null;
    }
}
