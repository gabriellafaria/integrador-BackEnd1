package com.dh.consultorioOdontologico.service;

import com.dh.consultorioOdontologico.dao.IDao;
import com.dh.consultorioOdontologico.dao.impl.ConsultaDao;
import com.dh.consultorioOdontologico.dao.impl.EnderecoDao;
import com.dh.consultorioOdontologico.dao.impl.PacienteDao;
import com.dh.consultorioOdontologico.model.Consulta;
import com.dh.consultorioOdontologico.model.Endereco;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class EnderecoService {
    IDao<Endereco> enderecoIDao = new EnderecoDao();
    EnderecoDao enderecoDao = new EnderecoDao();

    public Endereco cadastrar(Endereco endereco) throws SQLException {
        return enderecoIDao.cadastrar(endereco);
    }

    public Endereco modificar(Endereco endereco) throws SQLException {
        return enderecoIDao.modificar(endereco);
    }

    public void excluir(Endereco endereco) throws SQLException {
        enderecoIDao.excluir(endereco);
    }

    public List<Endereco> buscarTodos() throws SQLException{
        return enderecoDao.buscarTodos();
    }


    public Optional<Endereco> buscarPorId(int id) throws SQLException {
        EnderecoDao enderecoDao = new EnderecoDao();
        return enderecoDao.buscarPorId(id);
    }
    public void excluirPorId(int id) throws SQLException {
        enderecoDao.excluirPorID(id);
    }
    
}
