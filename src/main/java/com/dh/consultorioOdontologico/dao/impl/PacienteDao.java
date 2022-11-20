package com.dh.consultorioOdontologico.dao.impl;

import com.dh.consultorioOdontologico.dao.IDao;
import com.dh.consultorioOdontologico.dao.configuracaoJDBC.ConfiguracaoJDBC;
import com.dh.consultorioOdontologico.model.Paciente;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PacienteDao implements IDao<Paciente> {
    private ConfiguracaoJDBC configuracaoJDBC = new ConfiguracaoJDBC();
    private List<Paciente> pacientes = new ArrayList<>();

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
        Connection connection = null;
        String SQLUPDATE = ("UPDATE paciente SET nome = ?, sobrenome = ?, id_endereco = ?, rg = ?, data_registro = ? WHERE id = ?");

        try {
            logger.info("Conexão aberta, atualizando o paciente: " + paciente.getNome() + " " + paciente.getSobrenome());
            connection = configuracaoJDBC.getConnectionH2();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLUPDATE);
            preparedStatement.setString(1, paciente.getNome());
            preparedStatement.setString(2, paciente.getSobrenome());
            preparedStatement.setInt(3, paciente.getIdEndereco());
            preparedStatement.setString(4,paciente.getRg());
            preparedStatement.setDate(5, java.sql.Date.valueOf(paciente.getDataRegistro()));
            preparedStatement.setInt(6,paciente.getId());

            preparedStatement.executeUpdate();
            logger.info("O paciente " + paciente.getNome() + " " + paciente.getSobrenome() + " foi atualizado!");

        }catch (Exception e){
            logger.error("Erro ao atualizar o paciente.");
            e.printStackTrace();
        }finally {
            logger.info("Conexão com o banco de dados encerrada.");
            connection.close();
        }
        return paciente;
    }

//    @Override
//    public Paciente modificar(Paciente paciente) throws SQLException {
//        String SQLUPDATE = String.format("UPDATE paciente SET (nome, sobrenome, rg, data_registro, id_endereco) = ('%s', '%s', '%s', '%s', '%s') WHERE id = '%s'", paciente.getNome(), paciente.getSobrenome(), paciente.getRg(), paciente.getDataRegistro(), paciente.getIdEndereco() ,paciente.getId());
//        Connection connection = null;
//        try {
//            logger.info("Conexão aberta, atualizando o paciente: " + paciente.getNome());
//            connection = configuracaoJDBC.getConnectionH2();
//            Statement statement = connection.createStatement();
//            statement.execute(SQLUPDATE);
//            logger.info("Atualizado o paciente " + paciente.getNome() + paciente.getSobrenome());
//        } catch (Exception e){
//            logger.error("Erro ao modificar o nome do paciente.");
//            e.printStackTrace();
//        } finally {
//            logger.info("Fechando a conexão.");
//            connection.close();
//        }
//        return paciente;
//    }


    @Override
    public void excluir(Paciente paciente) throws SQLException {
        String SQLDELETE = String.format("DELETE FROM paciente WHERE id = '%d'", paciente.getId());
        Connection connection = null;
        try{
            logger.info("Conexão com o banco de dados aberta.");
            connection = configuracaoJDBC.getConnectionH2();
            Statement statement = connection.createStatement();
            logger.info("Deletando paciente com o id: "+ paciente.getId());
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

    @Override
    public Optional<Paciente> buscarPorId(int id) throws SQLException {
        Connection connection = null;

        String SQLBUSCARPORID = "SELECT id, nome, sobrenome, id_endereco, rg, data_registro FROM paciente WHERE id = ?";
        Paciente paciente = null;

        try {
            logger.info("Conexão com o banco de dados aberta para buscar o paciente pelo Id.");
            connection = configuracaoJDBC.getConnectionH2();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLBUSCARPORID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int idPkey = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                String sobrenome = resultSet.getString("sobrenome");
                LocalDate dataRegistro = resultSet.getDate("data_registro").toLocalDate();
                String rg = resultSet.getString("rg");
                int idEndereco = resultSet.getInt("id_endereco");

                paciente = new Paciente(idPkey, nome, sobrenome, idEndereco, rg, dataRegistro);

                logger.info("O paciente com o id " + paciente.getId() + " foi encontrado!");
            }
        }catch (Exception e){
            logger.error("Erro ao buscar o paciente do id informado.");
            e.printStackTrace();
        }finally {
            logger.info("Encerrando a conexão com o banco de dados.");
            connection.close();
        }

        return Optional.ofNullable(paciente);

    }

    public List<Paciente> buscarTodos() throws SQLException{
        String SQLSELECT = "SELECT paciente.id, paciente.nome, paciente.sobrenome, paciente.rg, paciente.data_registro, paciente.id_endereco, endereco.rua, endereco.numero, endereco.cidade, endereco.sigla_estado FROM paciente" +
                " INNER JOIN endereco ON endereco.id = paciente.id_endereco";
        Connection connection = null;
        try {
            logger.info("Conexão com o banco de dados aberta para consulta dos pacientes.");
            connection = configuracaoJDBC.getConnectionH2();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQLSELECT);
            while (resultSet.next()){
                System.out.println("Paciente id: " + resultSet.getInt(1) + ", nome " + resultSet.getString(2) + resultSet.getString(3) + " cadastrado em " + resultSet.getTimestamp(5) + " rg " + resultSet.getString(4) + " com o endereco na " + resultSet.getString(7) + " numero " + resultSet.getInt(8) + ".");
                pacientes.add(new Paciente(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(6), resultSet.getString(4), resultSet.getTimestamp(5).toLocalDateTime().toLocalDate()));
            }
        }catch (Exception e){
            logger.error("Erro na buisca dos pancientes.");
            e.printStackTrace();
        } finally {
            logger.info("Encerrando a conexão com o banco de dados.");
            connection.close();
        }
        return pacientes;
    }

    public void excluirPorID(int id) throws SQLException {
        String SQLDELETE = String.format("DELETE FROM paciente WHERE id = '%d'", id);
        Connection connection = null;
        try{
            logger.info("Conexão com o banco de dados aberta.");
            connection = configuracaoJDBC.getConnectionH2();
            Statement statement = connection.createStatement();
            logger.info("Paciente dentista com o id: " + id);
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
