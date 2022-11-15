package com.dh.consultorioOdontologico.dao.impl;

import com.dh.consultorioOdontologico.dao.IDao;
import com.dh.consultorioOdontologico.dao.configuracaoJDBC.ConfiguracaoJDBC;
import com.dh.consultorioOdontologico.model.Endereco;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class EnderecoDao implements IDao<Endereco> {
    private ConfiguracaoJDBC configuracaoJDBC = new ConfiguracaoJDBC();
    static final Logger logger = Logger.getLogger(EnderecoDao.class);

    @Override
    public Endereco cadastrar(Endereco endereco) throws SQLException {
        String SQLINSERT = String.format("INSERT INTO Endereco(rua, numero, cidade, sigla_estado) VALUES ('%s', '%s', '%s', '%s')", endereco.getRua(), endereco.getNumero(), endereco.getCidade(), endereco.getSiglaEstado());
        Connection connection = null;
        try {
            logger.info("Conexão com o banco de dados aberta, para inserir o endereço.");
            connection = configuracaoJDBC.getConnectionH2();
            Statement statement = connection.createStatement();
            statement.execute(SQLINSERT, Statement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = statement.getGeneratedKeys();
            if(resultSet.next())
                endereco.setId(resultSet.getInt(1));
        } catch (Exception e){
            logger.info("Erro no cadastro de endereço.");
            e.printStackTrace();
        } finally {
            logger.info("Conexão com o banco de dados encerrada.");
            connection.close();
        }
        return null;
    }

    @Override
    public Endereco modificar(Endereco endereco) throws SQLException {
        return null;
    }

    @Override
    public void excluir(Endereco endereco) throws SQLException {
        String SQLDELETE = String.format("DELETE FROM endereco WHERE id = '%d'", endereco.getId());
        Connection connection = null;
        try{
            logger.info("Conexão com o banco de dados aberta.");
            connection = configuracaoJDBC.getConnectionH2();
            Statement statement = connection.createStatement();
            statement.execute(SQLDELETE);
            logger.info("Endereço do id: " + endereco.getId() + "deletado do banco de dados.");
        } catch (Exception e) {
            logger.error("Erro ao excluir o endereco");
            e.printStackTrace();
        } finally {
            logger.info("Conexão com o banco de dados encerrada.");
            connection.close();
        }
    }
}
