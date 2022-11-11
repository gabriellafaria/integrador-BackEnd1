package com.dh.consultorioOdontologico.dao;

public interface IDao<T>{
    public T cadastrar(T t);
    public T modificar(T t);
    public T excluir(T t);
}
