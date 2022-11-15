package com.dh.consultorioOdontologico.dao.impl;

import com.dh.consultorioOdontologico.dao.IDao;
import com.dh.consultorioOdontologico.dao.configuracaoJDBC.ConfiguracaoJDBC;
import com.dh.consultorioOdontologico.model.Endereco;
import com.dh.consultorioOdontologico.model.Paciente;
import org.apache.log4j.Logger;

import java.sql.*;

public class PacienteDao implements IDao<Paciente> {
    private ConfiguracaoJDBC configuracaoJDBC = new ConfiguracaoJDBC();

    static final Logger logger = Logger.getLogger(PacienteDao.class);

    @Override
    public Paciente cadastrar(Paciente paciente) throws SQLException {
        String SQLINSERT = String.format("INSERT INTO Paciente(nome, sobrenome, rg, data_registro, id_endereco) VALUES ('%s', '%s', '%s', '%s', '%d')", paciente.getNome(), paciente.getSobrenome(),
                paciente.getRg(), paciente.getDataRegistro(), paciente.getIdEndereco());
        Connection connection = null;
        try{
            logger.info("Conexão com o banco de dados aberta, para inserir o paciente.");
            connection = configuracaoJDBC.getConnectionH2();
            Statement statement = connection.createStatement();
            statement.execute(SQLINSERT, Statement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = statement.getGeneratedKeys();
            if(resultSet.next())
                paciente.setId(resultSet.getInt(1));
            logger.info("Paciente " + paciente.getNome() + " salvo no banco de dados, com o id = " + paciente.getId() + ".");
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
        String SQLUPDATE = String.format("UPDATE paciente SET nome = '%s' WHERE id = '%s'", paciente.getNome(), paciente.getId());
        Connection connection = null;
        try {
            logger.info("Conexão aberta, atualizando o nome do paciente: " + paciente.getNome());
            connection = configuracaoJDBC.getConnectionH2();
            Statement statement = connection.createStatement();
            statement.execute(SQLUPDATE);
            logger.info("Atualizado o nome do paciente.");
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
        String SQLDELETE = String.format("DELETE FROM paciente WHERE id = '%d'; DELETE FROM endereco WHERE id = '%d'", paciente.getId(), paciente.getIdEndereco());
        Connection connection = null;
        try{
            logger.info("Conexão com o banco de dados aberta.");
            connection = configuracaoJDBC.getConnectionH2();
            Statement statement = connection.createStatement();
            logger.info("Deletando paciente com o id: "+ paciente.getId() + " e endereco de id: " + paciente.getIdEndereco());
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
