package com.dh.consultorioOdontologico.service;

import com.dh.consultorioOdontologico.dao.IDao;
import com.dh.consultorioOdontologico.dao.impl.EnderecoDao;
import com.dh.consultorioOdontologico.model.Endereco;

import java.sql.SQLException;

public class EnderecoService {
    IDao<Endereco> enderecoIDao = new EnderecoDao();

    public Endereco cadastrar(Endereco endereco) throws SQLException {
        return enderecoIDao.cadastrar(endereco);
    }

    public Endereco modificar(Endereco endereco) throws SQLException {
        return enderecoIDao.modificar(endereco);
    }

    public void excluir(Endereco endereco) throws SQLException {
        enderecoIDao.excluir(endereco);
    }

    
}
