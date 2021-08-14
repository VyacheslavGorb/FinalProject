package edu.gorb.musicstudio.model.dao;

import edu.gorb.musicstudio.entity.TeacherDescription;
import edu.gorb.musicstudio.exception.DaoException;

import java.util.Optional;

/**
 * Dao for teacher_description table
 */
public interface TeacherDescriptionDao extends BaseDao<TeacherDescription> {
    /**
     * Finds teacher description by teacher id
     *
     * @param id teacher id
     * @return optional of {@link TeacherDescription}
     * @throws DaoException is thrown when error while query execution occurs
     */
    Optional<TeacherDescription> findEntityByTeacherId(long id) throws DaoException;
}
