package com.dh.consultorioOdontologico.dao.impl;

import com.dh.consultorioOdontologico.dao.IDao;
import com.dh.consultorioOdontologico.dao.configuracaoJDBC.ConfiguracaoJDBC;
import com.dh.consultorioOdontologico.model.Consulta;
import com.dh.consultorioOdontologico.model.Dentista;
import com.dh.consultorioOdontologico.model.Endereco;
import com.dh.consultorioOdontologico.model.Paciente;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConsultaDao implements IDao<Consulta> {
    private ConfiguracaoJDBC configuracaoJDBC = new ConfiguracaoJDBC();
    private List<Consulta> consultas = new ArrayList<>();

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
        return consulta;
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

    @Override
    public Optional<Consulta> buscarPorId(int id) throws SQLException {
        Connection connection = null;

        String SQLBUSCARPORID = "SELECT id, id_paciente, id_dentista, data_consulta FROM consulta WHERE id = ?";
        Consulta consulta = null;

        try {
            logger.info("Conexão com o banco de dados aberta para buscar a consulta pelo Id.");
            connection = configuracaoJDBC.getConnectionH2();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLBUSCARPORID);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int idPkey = resultSet.getInt("id");
                int idPaciente = resultSet.getInt("id_paciente");
                int idDentista = resultSet.getInt("id_dentista");
                LocalDateTime dataConsulta = resultSet.getTimestamp("data_consulta").toLocalDateTime();

                consulta = new Consulta(idPkey, idPaciente, idDentista, dataConsulta);
                logger.info("A consuklta com o id " + consulta.getId() + " foi encontrada");
            }
        }catch (Exception e) {
            logger.error("Erro ao buscar a consulta do Id informado.");
            e.printStackTrace();
        }finally {
            logger.info("Encerrando conexão com o banco de dados.");
            connection.close();
        }
        return Optional.ofNullable(consulta);
    }

    public List<Consulta> buscarTodos() throws SQLException {
        String SQLSELECT = "SELECT Consulta.id, Consulta.data_consulta, Consulta.id_paciente, Consulta.id_dentista, Paciente.nome, Dentista.nome FROM Consulta " +
                "INNER JOIN Paciente ON Paciente.id = Consulta.id_paciente " +
                "INNER JOIN Dentista ON Dentista.id = Consulta.id_dentista";
        Connection connection = null;
        try{
            logger.info("Conexão com o bando de dados aberta para buscar as consultas.");
            connection = configuracaoJDBC.getConnectionH2();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQLSELECT);
            while (resultSet.next()) {
                System.out.println("Consulta id: " + resultSet.getInt(1) + ", no horário: " + resultSet.getTimestamp(2) + ", do paciente: " + resultSet.getString(5) + ", com o dentista: " + resultSet.getString(6));
                consultas.add(new Consulta(resultSet.getInt(1), resultSet.getInt(3), resultSet.getInt(4), resultSet.getTimestamp(2).toLocalDateTime()));
            }
        } catch (Exception e){
            logger.error("Erro ao buscar todas as consultas.");
            e.printStackTrace();
        } finally {
            logger.info("Conexão com o bando de dados encerrada.");
            connection.close();
        }
        return consultas;
    }
}
