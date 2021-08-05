package edu.gorb.musicstudio.model.dao;

import edu.gorb.musicstudio.entity.AbstractEntity;
import edu.gorb.musicstudio.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface BaseDao<T extends AbstractEntity> {
    List<T> findAll() throws DaoException;

    Optional<T> findEntityById(long id) throws DaoException;

    int insert(T t) throws DaoException;

    void update(T t) throws DaoException;
}
