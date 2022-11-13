package com.dh.consultorioOdontologico.dao.impl;

import com.dh.consultorioOdontologico.dao.IDao;
import com.dh.consultorioOdontologico.dao.configuracaoJDBC.ConfiguracaoJDBC;
import com.dh.consultorioOdontologico.model.Paciente;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PacienteDao implements IDao<Paciente> {

    private ConfiguracaoJDBC configuracaoJDBC;

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        configuracaoJDBC = new ConfiguracaoJDBC("org.h2.Driver", "jdbc:h2:~/CONSULTORIO_ODONTOLOGICO;AUTO_SERVER=TRUE;INIT=RUNSCRIPT FROM 'create.sql'", "sa","");
        return configuracaoJDBC.getConnection();
    }
    /*public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:~/paciente;AUTO_SERVER=TRUE;INIT=RUNSCRIPT FROM 'create.sql'", "sa", "");
    }*/

    static final Logger logger = Logger.getLogger(PacienteDao.class);

    @Override
    public Paciente cadastrar(Paciente paciente) {
        String SQLINSERT = String.format("INSERT INTO Paciente(nome, sobrenome, rg, data_registro VALUES ('%s', '%s', '%s', '%s')", paciente.getNome(), paciente.getSobrenome(),
                paciente.getRg(), paciente.getDataRegistro());
        return null;
    }

    @Override
    public Paciente modificar(Paciente paciente) {
        return null;
    }

    @Override
    public void excluir(Paciente paciente) {

    }
}
