package edu.gorb.musicstudio.model.dao.impl;

import edu.gorb.musicstudio.entity.TeacherDescription;
import edu.gorb.musicstudio.exception.DaoException;
import edu.gorb.musicstudio.model.dao.JdbcHelper;
import edu.gorb.musicstudio.model.dao.TeacherDescriptionDao;
import edu.gorb.musicstudio.model.dao.mapper.impl.TeacherDescriptionRowMapper;
import edu.gorb.musicstudio.model.pool.ConnectionPool;

import java.util.List;
import java.util.Optional;

public class TeacherDescriptionDaoImpl implements TeacherDescriptionDao {

    private static final String SELECT_DESCRIPTION_BY_ID =
            "SELECT id_teacher, self_description, experience, picture_path\n" +
                    "FROM teacher_descriptions\n" +
                    "WHERE id_teacher=?";

    private static final String INSERT_DESCRIPTION =
            "INSERT INTO teacher_descriptions (id_teacher, self_description, experience, picture_path) VALUE (?, ?, ?, ?)";

    private static final String UPDATE_DESCRIPTIONS =
            "UPDATE teacher_descriptions\n" +
                    "SET self_description =?,\n" +
                    "    experience       =?,\n" +
                    "    picture_path     =?\n" +
                    "WHERE id_teacher = ?";

    private final JdbcHelper<TeacherDescription> jdbcHelper;

    public TeacherDescriptionDaoImpl() {
        jdbcHelper = new JdbcHelper<>(ConnectionPool.getInstance(), new TeacherDescriptionRowMapper());
    }

    @Override
    public Optional<TeacherDescription> findEntityByTeacherId(long id) throws DaoException {
        return jdbcHelper.executeQueryForSingleResult(SELECT_DESCRIPTION_BY_ID, id);
    }

    @Override
    public long insert(TeacherDescription teacherDescription) throws DaoException {
        jdbcHelper.executeUpdate(INSERT_DESCRIPTION,
                teacherDescription.getId(),
                teacherDescription.getDescription(),
                teacherDescription.getExperience(),
                teacherDescription.getPicturePath());
        return -1;
    }

    @Override
    public void update(TeacherDescription teacherDescription) throws DaoException {
        jdbcHelper.executeUpdate(UPDATE_DESCRIPTIONS,
                teacherDescription.getDescription(),
                teacherDescription.getExperience(),
                teacherDescription.getPicturePath(),
                teacherDescription.getId());
    }

    @Override
    public List<TeacherDescription> findAll() throws DaoException {
        throw new UnsupportedOperationException("findAll method is not implemented");
    }

    @Override
    public Optional<TeacherDescription> findEntityById(long id) throws DaoException {
        throw new UnsupportedOperationException("findEntityById method is not implemented");
    }
}
