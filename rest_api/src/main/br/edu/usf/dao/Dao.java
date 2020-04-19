package br.edu.usf.dao;

import java.util.Collection;

public interface Dao<T> {

    boolean insert(T t);

    Collection<T> findAll();

    boolean update(T t);

    boolean delete(T t);
}