//package com.dh.consultorioOdontologico.dao.impl;
//
//import com.dh.consultorioOdontologico.dao.IDao;
//import com.dh.consultorioOdontologico.dao.configuracaoJDBC.ConfiguracaoJDBC;
//import com.dh.consultorioOdontologico.entity.Dentista;
//import org.apache.log4j.Logger;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//public class DentistaDao implements IDao<Dentista> {
//
//    static final Logger logger = Logger.getLogger(DentistaDao.class);
//    private ConfiguracaoJDBC configuracaoJDBC = new ConfiguracaoJDBC();
//    private List<Dentista> listaDentistas = new ArrayList<>();
//
//    @Override
//    public Dentista cadastrar(Dentista dentista) throws SQLException {
//        String DENTISTA_INSERT = String.format("INSERT INTO Dentista(nome, sobrenome, matricula) VALUES('%s', '%s', '%s')", dentista.getNome(), dentista.getSobrenome(), dentista.getMatricula());
//        Connection connection = null;
//        try{
//            logger.info("Abrindo conexão com o banco.");
//            connection = configuracaoJDBC.getConnectionH2();
//            Statement statement = connection.createStatement();
//            statement.execute(DENTISTA_INSERT, Statement.RETURN_GENERATED_KEYS);
//            ResultSet resultSet = statement.getGeneratedKeys();
//            if(resultSet.next()){
//                dentista.setId(resultSet.getInt("id"));
//            }
//            logger.info("Dentista " + dentista.getNome() + " salvo no banco.");
//        } catch (Exception e){
//            logger.error("Erro no cadastro do dentista.");
//            e.printStackTrace();
//
//        } finally {
//            logger.info("Fechando conexão com o banco de dados.");
//            if(connection!=null) connection.close();
//        }
//        return dentista;
//    }
//
//    @Override
//    public Dentista modificar(Dentista dentista) throws SQLException {
//        String SQLUPDATE = String.format("UPDATE dentista SET (nome, sobrenome, matricula) = ('%s', '%s', '%s') WHERE id = '%s'", dentista.getNome(), dentista.getSobrenome(), dentista.getMatricula(),dentista.getId());
//        Connection connection = null;
//        try {
//            logger.info("Conexão aberta, atualizando o dentista: " + dentista.getNome());
//            connection = configuracaoJDBC.getConnectionH2();
//            Statement statement = connection.createStatement();
//            statement.execute(SQLUPDATE);
//            logger.info("Atualizado o dentista " + dentista.getNome() + dentista.getSobrenome());
//        } catch (Exception e){
//            logger.error("Erro ao modificar o nome do dentista.");
//            e.printStackTrace();
//        } finally {
//            logger.info("Fechando a conexão.");
//            connection.close();
//        }
//        return dentista;
//    }
//
//    @Override
//    public void excluir(Dentista dentista) throws SQLException {
//
//    }
//
//    public void excluirPorID(int id) throws SQLException {
//        String SQLDELETE = String.format("DELETE FROM dentista WHERE id = '%d'", id);
//        Connection connection = null;
//        try{
//            logger.info("Conexão com o banco de dados aberta.");
//            connection = configuracaoJDBC.getConnectionH2();
//            Statement statement = connection.createStatement();
//            logger.info("Deletando dentista com o id: " + id);
//            statement.execute(SQLDELETE);
//            logger.info("Dentista deletado do banco");
//        } catch (Exception e) {
//            logger.error("Erro ao excluir o dentista");
//            e.printStackTrace();
//        } finally {
//            logger.info("Conexão com o banco de dados encerrada.");
//            connection.close();
//        }
//    }
//
//    @Override
//    public Optional<Dentista> buscarPorId(int id) throws SQLException {
//        Connection connection = null;
//
//        String SQLBUSCARPORID = "SELECT id, nome, sobrenome, matricula FROM dentista WHERE id = ?";
//        Dentista dentista = null;
//
//        try{
//            logger.info("Conexão com o banco de dados aberta para buscar o dentista pelo Id.");
//            connection = configuracaoJDBC.getConnectionH2();
//            PreparedStatement preparedStatement = connection.prepareStatement(SQLBUSCARPORID);
//            preparedStatement.setInt(1,id);
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            while (resultSet.next()) {
//                int idPkey = resultSet.getInt("id");
//                String nome = resultSet.getString("nome");
//                String sobrenome = resultSet.getString("sobrenome");
//                int matricula = resultSet.getInt("matricula");
//
//                dentista = new Dentista(idPkey, nome, sobrenome, matricula);
//                logger.info("O dentista com o id " + dentista.getId() + " foi encontrado!");
//            }
//
//        }catch (Exception e){
//            logger.error("Erro ao buscar o dentista do Id informado.");
//            e.printStackTrace();
//        }finally {
//            logger.info("Encerrando a conexão com o banco de dados.");
//            connection.close();
//        }
//
//        return Optional.ofNullable(dentista);
//    }
//
//    public List<Dentista> buscarTodos() throws SQLException{
//        String BUSCAR_DENTISTAS = String.format("SELECT * FROM DENTISTA;");
//        Connection connection = null;
//        try{
//            logger.info("Abrindo conexão com o banco para trazer lista de dentistas cadastrados.");
//            connection = configuracaoJDBC.getConnectionH2();
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery(BUSCAR_DENTISTAS);
//            while (resultSet.next()){
//                listaDentistas.add(new Dentista(
//                        resultSet.getInt("id"),
//                        resultSet.getString("nome"),
//                        resultSet.getString("sobrenome"),
//                        resultSet.getInt("matricula")
//                ));
//            }
//        } catch (Exception e){
//            logger.warn("Erro de conexão com o banco ao executar listagem de dentistas.");
//            e.printStackTrace();
//        } finally {
//            logger.info("Fechando conexão com o banco de dados.");
//            connection.close();
//            System.out.println(listaDentistas);
//        }
//        return listaDentistas;
//    }
//
//    public List<Dentista> buscarPorMatricula(Integer matricula) throws SQLException{
//        List dentista = new ArrayList<Dentista>();
//        String BUSCAR_DENTISTA = String.format("SELECT * FROM DENTISTA WHERE MATRICULA = '%s'", matricula);
//        Connection connection = null;
//        try{
//            logger.info("Abrindo conexão com o banco de dados para trazer um dentista.");
//            connection = configuracaoJDBC.getConnectionH2();
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery(BUSCAR_DENTISTA);
//            if(resultSet.next()){
//                dentista.add(new Dentista(
//                        resultSet.getInt("id"),
//                        resultSet.getString("nome"),
//                        resultSet.getString("sobrenome"),
//                        resultSet.getInt("matricula")
//                ));
//                logger.info("Dentista encontrado com sucesso");
//                System.out.println(dentista);
//            }
//        } catch (Exception e){
//            logger.warn("Algo de errado ocorreu com a conexão ao tentar trazer o dentista.");
//            e.printStackTrace();
//        }finally {
//            logger.info("Fechando conexão com o banco de dados!");
//            connection.close();
//        }
//        return dentista;
//    }
//}
