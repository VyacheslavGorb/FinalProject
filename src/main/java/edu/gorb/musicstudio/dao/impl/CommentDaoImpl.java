package edu.gorb.musicstudio.dao.impl;

import edu.gorb.musicstudio.dao.CommentDao;
import edu.gorb.musicstudio.dao.JdbcHelper;
import edu.gorb.musicstudio.entity.Comment;
import edu.gorb.musicstudio.exception.DaoException;
import edu.gorb.musicstudio.mapper.impl.CommentRowMapperImpl;
import edu.gorb.musicstudio.pool.ConnectionPool;

import java.util.List;
import java.util.Optional;

public class CommentDaoImpl implements CommentDao {

    private static final String SELECT_ALL_COMMENTS =
            "SELECT id_comment, id_student, id_teacher, content, date_time, rating\n" +
                    "FROM comments";

    private static final String SELECT_COMMENT_BY_ID =
            "SELECT id_comment, id_student, id_teacher, content, date_time, rating\n" +
                    "FROM comments\n" +
                    "WHERE id_comment=?";

    private static final String SELECT_COMMENTS_BY_TEACHER_ID =
            "SELECT id_comment, id_student, id_teacher, content, date_time, rating\n" +
                    "FROM comments\n" +
                    "WHERE id_teacher=?";

    private static final String SELECT_COMMENTS_BY_STUDENT_ID =
            "SELECT id_comment, id_student, id_teacher, content, date_time, rating\n" +
                    "FROM comments\n" +
                    "WHERE id_student=?";

    public static final String INSERT_NEW_COMMENT =
            "INSERT INTO comments (id_student, id_teacher, content, date_time) VALUE (?, ?, ?, ?)";

    public static final String UPDATE_COMMENT = "UPDATE comments\n" +
            "SET id_student=?,\n" +
            "    id_teacher=?,\n" +
            "    content=?,\n" +
            "    date_time=?\n" +
            "WHERE id_comment = ?";

    private JdbcHelper<Comment> jdbcHelper;

    public CommentDaoImpl() {
        jdbcHelper = new JdbcHelper<>(ConnectionPool.getInstance(), new CommentRowMapperImpl());
    }

    @Override
    public List<Comment> findAll() throws DaoException {
        return jdbcHelper.executeQuery(SELECT_ALL_COMMENTS);
    }

    @Override
    public Optional<Comment> findEntityById(Long id) throws DaoException {
        return jdbcHelper.executeQueryForSingleResult(SELECT_COMMENT_BY_ID, id);
    }

    @Override
    public int insert(Comment comment) throws DaoException {
        return jdbcHelper.executeInsert(INSERT_NEW_COMMENT,
                comment.getStudentId(),
                comment.getTeacherId(),
                comment.getContent(),
                comment.getDateTime());
    }

    @Override
    public void update(Comment comment) throws DaoException {
        jdbcHelper.executeUpdate(INSERT_NEW_COMMENT,
                comment.getStudentId(),
                comment.getTeacherId(),
                comment.getContent(),
                comment.getDateTime(),
                comment.getId());
    }

    @Override
    public List<Comment> findCommentsByTeacherId(long teacherId) throws DaoException {
        return jdbcHelper.executeQuery(SELECT_COMMENTS_BY_TEACHER_ID, teacherId);
    }

    @Override
    public List<Comment> findCommentsByStudentId(long studentId) throws DaoException {
        return jdbcHelper.executeQuery(SELECT_COMMENTS_BY_STUDENT_ID, studentId);
    }
}
