package com.dh.consultorioOdontologico.dao.impl;

import com.dh.consultorioOdontologico.dao.IDao;
import com.dh.consultorioOdontologico.dao.configuracaoJDBC.ConfiguracaoJDBC;
import com.dh.consultorioOdontologico.model.Paciente;
import org.apache.log4j.Logger;

import java.sql.*;

public class PacienteDao implements IDao<Paciente> {

    private ConfiguracaoJDBC configuracaoJDBC;

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        configuracaoJDBC = new ConfiguracaoJDBC("org.h2.Driver", "jdbc:h2:~/CONSULTORIO_ODONTOLOGICO;AUTO_SERVER=TRUE;INIT=RUNSCRIPT FROM 'create.sql'", "sa","");
        return configuracaoJDBC.getConnection();
    }

    static final Logger logger = Logger.getLogger(PacienteDao.class);

    @Override
    public Paciente cadastrar(Paciente paciente) throws SQLException {
        String SQLINSERT = String.format("INSERT INTO Paciente(nome, sobrenome, rg, data_registro VALUES ('%s', '%s', '%s', '%s')", paciente.getNome(), paciente.getSobrenome(),
                paciente.getRg(), paciente.getDataRegistro());
        Connection connection = null;
        try{
            logger.info("Conexão com o banco de dados aberta.");
            connection = getConnection();
            Statement statement = connection.createStatement();
            statement.execute(SQLINSERT, Statement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = statement.getGeneratedKeys();
            if(resultSet.next())
                paciente.setId(resultSet.getInt(1));
            logger.info("Paciente " + paciente.getNome() + " salvo no banco de dados.");
        } catch (Exception e){
            logger.info("Erro no cadastro do paciente.");
            e.printStackTrace();
        } finally {
            logger.info("Conexão com o banco de dados encerrada.");
            connection.close();
        }
        return null;
    }

    @Override
    public Paciente modificar(Paciente paciente) throws SQLException {
        String SQLUPDATE = String.format("UPDATE paciente SET name = '%s' WHERE id = '%s'", paciente.getNome(), paciente.getId());
        Connection connection = null;
        try {
            logger.info("Conexão aberta, atualizando o nome do paciente: " + paciente.getNome());
            connection = getConnection();
            Statement statement = connection.createStatement();
            statement.execute(SQLUPDATE);
            logger.info("Atualizado o nome do paciente para: " + paciente.getNome());
        } catch (Exception e){
            logger.error("Erro ao modificar o nome do paciente.");
            e.printStackTrace();
        } finally {
            logger.info("Fechando a conexão.");
            connection.close();
        }
        return null;
    }

    @Override
    public void excluir(Paciente paciente) throws SQLException {
        String SQLDELETE = String.format("DELETE FROM paciente WHERE id = '%d'", paciente.getId());
        Connection connection = null;
        try{
            logger.info("Conexão com o banco de dados aberta.");
            connection = getConnection();
            Statement statement = connection.createStatement();
            statement.execute(SQLDELETE);
            logger.info("Paciente deletado do banco");
        } catch (Exception e) {
            logger.error("Erro ao excluir o paciente");
            e.printStackTrace();
        } finally {
            logger.info("Conexão com o banco de dados encerrada.");
            connection.close();
        }
    }
}
