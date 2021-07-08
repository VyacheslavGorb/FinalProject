package edu.gorb.musicstudio.dao;

import edu.gorb.musicstudio.entity.AbstractEntity;
import edu.gorb.musicstudio.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface BaseDao<K, T extends AbstractEntity> {
    List<T> findAll() throws DaoException;

    Optional<T> findEntityById(K id) throws DaoException;

    void insert(T t) throws DaoException;

    void update(T t) throws DaoException;
}
