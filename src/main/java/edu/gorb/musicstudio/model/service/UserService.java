package edu.gorb.musicstudio.model.service;

import edu.gorb.musicstudio.dto.TeacherDto;
import edu.gorb.musicstudio.entity.User;
import edu.gorb.musicstudio.entity.UserRole;
import edu.gorb.musicstudio.entity.UserStatus;
import edu.gorb.musicstudio.entity.UserToken;
import edu.gorb.musicstudio.exception.ServiceException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {

    List<User> findAllUsers() throws ServiceException;

    Optional<User> findActiveRegisteredUser(String login, String password) throws ServiceException;

    User registerUser(UserRole userRole, String login, String password,
                      String name, String surname, String patronymic,
                      String email, UserStatus userStatus) throws ServiceException;

    void updateUserStatus(long userId, UserStatus status) throws ServiceException;

    boolean isLoginAvailableForNewUser(String login) throws ServiceException;

    boolean isEmailAvailableForNewUser(String email) throws ServiceException;

    String createUserToken(User user) throws ServiceException;

    Optional<UserToken> findValidToken(String token, long userId) throws ServiceException;

    void confirmEmail(long userId) throws ServiceException;

    Optional<User> findUserByLogin(String login) throws ServiceException;

    Optional<User> findUserById(Long id) throws ServiceException;

    List<TeacherDto> findTeachersForRequest(int pageNumber, String searchLine) throws ServiceException;

    int calcPagesCount(int teacherCount);

    int countTeachersForRequest(String searchLine) throws ServiceException;

    List<TeacherDto> trimTeachersDescriptionForPreview(List<TeacherDto> teachers);

    Optional<TeacherDto> findTeacherById(long teacherId) throws ServiceException;

    List<LocalTime> findTeacherFreeSlotsForDate(long teacherId, LocalDate date) throws ServiceException;

    int findTeacherFreeSlotCountForNextMonth(long teacherId) throws ServiceException;

    int findTeacherFreeSlotCountForFuturePeriod(LocalDate start, LocalDate end, long teacherId) throws ServiceException;

    List<User> findTeachersForCourse(long courseId) throws ServiceException;

    List<LocalDate> findAllAvailableDatesForTeachers(List<User> teachers, LocalDate startDate, LocalDate endDate)
            throws ServiceException;

    Map<User, List<LocalTime>> findFreeSlotsForTeachersForDate(List<User> teachers, LocalDate date)
            throws ServiceException;
}
