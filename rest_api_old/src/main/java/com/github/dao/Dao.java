package com.github.dao;

import java.util.Collection;

public interface Dao<T> {

    public boolean insert(T t);

    public Collection<T> findAll();

    public boolean update(T t);

    public boolean delete(T t);
}