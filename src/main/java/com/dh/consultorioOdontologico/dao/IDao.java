package com.dh.consultorioOdontologico.dao;

import java.sql.SQLException;
import java.util.Optional;

public interface IDao<T>{
    public T cadastrar(T t) throws SQLException;
    public T modificar(T t) throws SQLException;
    public void excluir(T t) throws  SQLException;

    Optional<T> buscarPorId(int T) throws SQLException;
}
