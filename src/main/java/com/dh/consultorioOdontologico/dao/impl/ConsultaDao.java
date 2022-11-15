package com.dh.consultorioOdontologico.dao.impl;

import com.dh.consultorioOdontologico.dao.IDao;
import com.dh.consultorioOdontologico.dao.configuracaoJDBC.ConfiguracaoJDBC;
import com.dh.consultorioOdontologico.model.Consulta;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConsultaDao implements IDao<Consulta> {
    private ConfiguracaoJDBC configuracaoJDBC = new ConfiguracaoJDBC();

    static final Logger logger = Logger.getLogger(ConsultaDao.class);

    @Override
    public Consulta cadastrar(Consulta consulta) throws SQLException {
        String SQLINSERT = String.format("INSERT INTO Consulta(id_paciente, id_dentista, data_consulta) VALUES ('%d', '%d', '%s')", consulta.getIdPaciente(), consulta.getIdDentista(), consulta.getDataConsulta());
        Connection connection = null;
        try{
            logger.info("Conexão com o banco de dados aberta para cadastro da consulta.");
            connection = configuracaoJDBC.getConnectionH2();
            Statement statement = connection.createStatement();
            statement.execute(SQLINSERT, Statement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = statement.getGeneratedKeys();
            if(resultSet.next())
                consulta.setId(resultSet.getInt(1));
            logger.info("Consulta cadastrada com o id: " + consulta.getId());
        } catch (Exception e){
            logger.error("Erro ao cadastrar a consulta.");
            e.printStackTrace();
        } finally {
            logger.info("Conexão com o banco de dados encerrada.");
            connection.close();
        }
        return null;
    }

    @Override
    public Consulta modificar(Consulta consulta) throws SQLException {
        String SQLUPDATE = String.format("UPDATE consulta SET data_consulta = '%s' WHERE id = '%s'", consulta.getDataConsulta(), consulta.getId());
        Connection connection = null;
        try{
            logger.info("Conexão com o banco de dados aberta para atualização da consulta");
            connection = configuracaoJDBC.getConnectionH2();
            Statement statement = connection.createStatement();
            statement.execute(SQLUPDATE);
            logger.info("Atualizada a data da consulta para: " + consulta.getDataConsulta());
        } catch (Exception e){
            logger.info("Erro ao atualizar a consulta");
            e.printStackTrace();
        } finally {
            logger.info("Conexão com o banco de dados encerrada.");
            connection.close();
        }
        return null;
    }

    @Override
    public void excluir(Consulta consulta) throws SQLException {
        String SQLDELETE = String.format("DELETE FROM Consulta WHERE id = '%d'", consulta.getId());
        Connection connection = null;
        try{
            logger.info("Conexão com o bando de dados aberta para exlusão da consulta.");
            connection = configuracaoJDBC.getConnectionH2();
            Statement statement = connection.createStatement();
            logger.info("Deletando consulta com o id: " + consulta.getId());
            statement.execute(SQLDELETE);
            logger.info("Consulta delatada com sucesso.");
        } catch (Exception e){
            logger.error("Erro ao exluir a consulta");
            e.printStackTrace();
        } finally {
            logger.info("Conexão com o bando de dados encerrada.");
            connection.close();
        }
    }
}
