package edu.gorb.musicstudio.model.dao;

import edu.gorb.musicstudio.entity.User;
import edu.gorb.musicstudio.entity.UserStatus;
import edu.gorb.musicstudio.exception.DaoException;

import java.util.List;
import java.util.Optional;

/**
 * Dao for users table
 */
public interface UserDao extends BaseDao<User> {
    /**
     * Finds user by login
     *
     * @param login user login
     * @return optional of {@link User}
     * @throws DaoException is thrown when error while query execution occurs
     */
    Optional<User> findUserByLogin(String login) throws DaoException;

    /**
     * Finds user by email
     *
     * @param email user email
     * @return optional of {@link User}
     * @throws DaoException is thrown when error while query execution occurs
     */
    Optional<User> findUserByEmail(String email) throws DaoException;

    /**
     * Updates user status
     *
     * @param userId     user id
     * @param userStatus new user status
     * @throws DaoException is thrown when error while query execution occurs
     */
    void updateUserStatus(long userId, UserStatus userStatus) throws DaoException;

    /**
     * Finds active teachers for page
     *
     * @param skipAmount            entities to skip amount
     * @param teachersPerPageAmount teacher count per page
     * @return list of {@link User} or empty list if no entities found
     * @throws DaoException is thrown when error while query execution occurs
     */
    List<User> selectActiveTeachersForPage(int skipAmount, int teachersPerPageAmount) throws DaoException;

    /**
     * Finds active teachers for page with search
     *
     * @param skipAmount           entities to skip amount
     * @param teacherPerPageAmount teacher count per page
     * @param search               search line
     * @return list of {@link User} or empty list if no entities found
     * @throws DaoException is thrown when error while query execution occurs
     */
    List<User> selectActiveTeachersWithSearchForPage(int skipAmount, int teacherPerPageAmount, String search)
            throws DaoException;

    /**
     * Counts active teachers
     *
     * @return active teacher count
     * @throws DaoException is thrown when error while query execution occurs
     */
    int countActiveTeachers() throws DaoException;

    /**
     * Counts active teachers with search
     *
     * @param searchParameter seach line
     * @return teacher count
     * @throws DaoException is thrown when error while query execution occurs
     */
    int countActiveTeachersWithSearch(String searchParameter) throws DaoException;

    /**
     * Find teachers by course id
     *
     * @param courseId course id
     * @return list of {@link User} or empty list if no entities found
     * @throws DaoException is thrown when error while query execution occurs
     */
    List<User> selectTeachersForCourse(long courseId) throws DaoException;

    /**
     * Find all active teachers
     *
     * @return list of {@link User} or empty list if no entities found
     * @throws DaoException is thrown when error while query execution occurs
     */
    List<User> findAllActiveTeachers() throws DaoException;
}
