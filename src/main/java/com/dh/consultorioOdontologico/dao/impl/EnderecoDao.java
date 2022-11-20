package com.dh.consultorioOdontologico.dao.impl;

import com.dh.consultorioOdontologico.dao.IDao;
import com.dh.consultorioOdontologico.dao.configuracaoJDBC.ConfiguracaoJDBC;
import com.dh.consultorioOdontologico.model.Consulta;
import com.dh.consultorioOdontologico.model.Endereco;
import com.dh.consultorioOdontologico.model.Paciente;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class EnderecoDao implements IDao<Endereco> {
    private ConfiguracaoJDBC configuracaoJDBC = new ConfiguracaoJDBC();
    private List<Endereco> enderecos = new ArrayList<>();
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
            logger.info("Endereço cadastrado, id " + endereco.getId() + ", " + endereco.getRua() + " numero " + endereco.getNumero() + " cidade " + endereco.getCidade() + " estado " + endereco.getSiglaEstado() );
        } catch (Exception e){
            logger.info("Erro no cadastro de endereço.");
            e.printStackTrace();
        } finally {
            logger.info("Conexão com o banco de dados encerrada.");
            connection.close();
        }
        return endereco;
    }

    @Override
    public Endereco modificar(Endereco endereco) throws SQLException {
        String SQLUPDATE = String.format("UPDATE endereco SET (rua, numero, cidade, sigla_estado) = ('%s','%s', '%s','%s') WHERE id = '%s'", endereco.getRua(), endereco.getNumero(), endereco.getCidade(), endereco.getSiglaEstado(), endereco.getId());
        Connection connection = null;
        try{
            logger.info("Abrindo conexão com o banco de dados para atualizar o endereço do id: " + endereco.getId());
            connection = configuracaoJDBC.getConnectionH2();
            Statement statement = connection.createStatement();
            statement.execute(SQLUPDATE);
            logger.info("Endereço atualizado, rua " + endereco.getRua() + " numero " + endereco.getNumero() + " cidade " + endereco.getCidade() + " estado " + endereco.getSiglaEstado() );
        } catch (Exception e){
            logger.error("Erro ao atualizar o endereço");
            e.printStackTrace();
        } finally {
            logger.info("Encerrando conexão com o banco de dados.");
            connection.close();
        }
        return endereco;
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
            logger.info("Endereço do id " + endereco.getId() + " deletado do banco de dados.");
        } catch (Exception e) {
            logger.error("Erro ao excluir o endereco");
            e.printStackTrace();
        } finally {
            logger.info("Conexão com o banco de dados encerrada.");
            connection.close();
        }
    }

    @Override
    public Optional<Endereco> buscarPorId(int id) throws SQLException {
        Connection connection = null;

        String SQLBUSCARPORID = "SELECT id, rua, numero, cidade, sigla_estado FROM endereco WHERE id = ?";
        Endereco endereco = null;

        try {
            logger.info("Conexão com o banco de dados aberta para buscar o endereço pelo Id.");
            connection = configuracaoJDBC.getConnectionH2();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLBUSCARPORID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int idPkey = resultSet.getInt("id");
                String rua = resultSet.getString("rua");
                int numero = resultSet.getInt("numero");
                String cidade = resultSet.getString("cidade");
                String siglaEstado = resultSet.getString("sigla_estado");

                endereco = new Endereco(idPkey, rua, numero, cidade, siglaEstado);
                logger.info("O endereço com o id " + endereco.getId() + " foi encontrado!");
            }
        }catch (Exception e){
            logger.error("Erro ao buscar o endereço do id informado.");
            e.printStackTrace();
        }finally {
            logger.info("Encerrando a conexão com o banco de dados.");
            connection.close();
        }
        return Optional.ofNullable(endereco);
    }

    //public static
    public List<Endereco> buscarTodos() throws SQLException {
        String SQLSELECT = "SELECT * FROM Endereco";
        Connection connection = null;
        try{
            logger.info("Conexão com o bando de dados aberta para buscar as consultas.");
            connection = configuracaoJDBC.getConnectionH2();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQLSELECT);
            while (resultSet.next()){
                System.out.println("Endereço: id " + resultSet.getInt(1) + ", na rua  " + resultSet.getString(2) + ", número " + resultSet.getInt(3) + ", da cidade  " + resultSet.getString(4) + " em " + resultSet.getString(5));
                enderecos.add(new Endereco(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getString(4), resultSet.getString(5)));
            }
        } catch (Exception e){
            logger.error("Erro ao buscar todas as consultas.");
            e.printStackTrace();
        } finally {
            logger.info("Conexão com o bando de dados encerrada.");
            connection.close();
        }
        return enderecos;
    }
}
