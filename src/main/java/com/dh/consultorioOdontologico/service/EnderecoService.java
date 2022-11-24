package com.dh.consultorioOdontologico.service;

import com.dh.consultorioOdontologico.dao.IDao;
import com.dh.consultorioOdontologico.dao.impl.EnderecoDao;
import com.dh.consultorioOdontologico.entity.Endereco;
import com.dh.consultorioOdontologico.dao.impl.PacienteDao;
import com.dh.consultorioOdontologico.model.Consulta;
import com.dh.consultorioOdontologico.model.Endereco;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
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

    public List<Endereco> buscarTodos() throws SQLException{
        EnderecoDao enderecoDao = new EnderecoDao();
        return enderecoDao.buscarTodos();
    }
    
}
