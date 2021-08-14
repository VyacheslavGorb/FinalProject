package edu.gorb.musicstudio.model.service;

import edu.gorb.musicstudio.entity.*;
import edu.gorb.musicstudio.exception.ServiceException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {

    /**
     * Finds all users
     *
     * @return list of {@link User} or empty list if no entities found
     */
    List<User> findAllUsers() throws ServiceException;

    /**
     * Finds active registered users
     *
     * @param login    user login
     * @param password user password hash
     * @return optional of {@link User}
     */
    Optional<User> findActiveRegisteredUser(String login, String password) throws ServiceException;

    /**
     * Finds all active teachers
     *
     * @return list of teachers or empty list if no entities found
     */
    List<User> findAllActiveTeachers() throws ServiceException;

    /**
     * Registers user
     *
     * @param userRole   user role
     * @param login      user login
     * @param password   user password hash
     * @param name       user name
     * @param surname    user surname
     * @param patronymic user patronymic
     * @param email      user email
     * @param userStatus user status
     * @return returns created entity
     */
    User registerUser(UserRole userRole, String login, String password,
                      String name, String surname, String patronymic,
                      String email, UserStatus userStatus) throws ServiceException;

    /**
     * Updates user status
     *
     * @param userId user id
     * @param status user status
     */
    void updateUserStatus(long userId, UserStatus status) throws ServiceException;

    /**
     * Checks if login is available for new user
     *
     * @param login login
     * @return if login is available
     */
    boolean isLoginAvailableForNewUser(String login) throws ServiceException;

    /**
     * Checks if email is available for new user
     *
     * @param email email
     * @return if email is available for new user
     */
    boolean isEmailAvailableForNewUser(String email) throws ServiceException;

    /**
     * Creates user token
     *
     * @param user user entity
     * @return Created token
     */
    String createUserToken(User user) throws ServiceException;

    /**
     * Finds valid token
     *
     * @param token  token value
     * @param userId user id
     * @return optional of {@link UserToken}
     */
    Optional<UserToken> findValidToken(String token, long userId) throws ServiceException;

    /**
     * Confirms email
     *
     * @param userId user id
     */
    void confirmEmail(long userId) throws ServiceException;

    /**
     * Finds user by login
     *
     * @param login user login
     * @return optional of {@link User} entity
     */
    Optional<User> findUserByLogin(String login) throws ServiceException;

    /**
     * Finds user by id
     *
     * @param id user id
     * @return optional of {@link User} entity
     */
    Optional<User> findUserById(Long id) throws ServiceException;

    /**
     * Finds teachers for request
     *
     * @param pageNumber number of page
     * @param searchLine search line
     * @return list of {@link Teacher} or empty list if no entities found
     */
    List<Teacher> findTeachersForRequest(int pageNumber, String searchLine) throws ServiceException;

    /**
     * Calculates page count
     *
     * @param teacherCount teacher count
     * @return pages count
     */
    int calcPagesCount(int teacherCount);

    /**
     * Counts teachers for request
     *
     * @param searchLine search line
     * @return teacher count for request
     */
    int countTeachersForRequest(String searchLine) throws ServiceException;

    /**
     * Trims teacher descriptions for preview
     *
     * @param teachers list of teachers
     * @return list of teachers with trimmed descriptions
     */
    List<Teacher> trimTeachersDescriptionForPreview(List<Teacher> teachers);

    /**
     * Finds teacher by id
     *
     * @param teacherId teacher id
     * @return optional of {@link Teacher} entity
     */
    Optional<Teacher> findTeacherById(long teacherId) throws ServiceException;

    /**
     * Finds teacher free slots for date
     *
     * @param teacherId teacher id
     * @param date      date
     * @return list of free slots
     */
    List<LocalTime> findTeacherFreeSlotsForDate(long teacherId, LocalDate date) throws ServiceException;

    /**
     * Finds Teacher free slot count for next month
     *
     * @param teacherId teacher id
     * @return list of free slots
     */
    int findTeacherFreeSlotCountForNextMonth(long teacherId) throws ServiceException;

    /**
     * Finds teacher free slot count for future period
     *
     * @param start     start date
     * @param end       end date
     * @param teacherId teacher id
     * @return slot count
     */
    int findTeacherFreeSlotCountForFuturePeriod(LocalDate start, LocalDate end, long teacherId) throws ServiceException;

    /**
     * Find teachers for course
     *
     * @param courseId course id
     * @return list of {@link Teacher} entity or empty list if no entities found
     */
    List<User> findTeachersForCourse(long courseId) throws ServiceException;

    /**
     * Find all available dates for teachers for period
     *
     * @param teachers  list of teachers
     * @param startDate start date
     * @param endDate   end date
     * @return list of available dates
     */
    List<LocalDate> findAllAvailableDatesForTeachersForPeriod(List<User> teachers, LocalDate startDate, LocalDate endDate)
            throws ServiceException;

    /**
     * Finds free slots for teacher for date
     *
     * @param teachers list of teachers
     * @param date     date
     * @return free slots for each teacher
     */
    Map<User, List<LocalTime>> findFreeSlotsForTeachersForDate(List<User> teachers, LocalDate date)
            throws ServiceException;
}
