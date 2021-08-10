package edu.gorb.musicstudio.model.service.impl;

import edu.gorb.musicstudio.dto.TeacherDto;
import edu.gorb.musicstudio.entity.*;
import edu.gorb.musicstudio.exception.DaoException;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.dao.DaoProvider;
import edu.gorb.musicstudio.model.dao.TeacherDescriptionDao;
import edu.gorb.musicstudio.model.dao.UserDao;
import edu.gorb.musicstudio.model.dao.UserTokenDao;
import edu.gorb.musicstudio.model.service.LessonScheduleService;
import edu.gorb.musicstudio.model.service.ServiceProvider;
import edu.gorb.musicstudio.model.service.TeacherScheduleService;
import edu.gorb.musicstudio.model.service.UserService;
import edu.gorb.musicstudio.util.DescriptionUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger();
    private static final int LINK_EXPIRE_TIMEOUT = 10; // minutes
    private static final int ITEMS_ON_PAGE_COUNT = 1;
    private static final int MIN_PAGE_COUNT = 1;
    private static final int DEFAULT_SUBSCRIPTION_LENGTH_DAYS = 30;


    public Optional<User> findRegisteredUser(String login, String password) throws ServiceException {
        if (login == null || password == null) {
            return Optional.empty();
        }

        try {
            DaoProvider daoProvider = DaoProvider.getInstance();
            UserDao userDao = daoProvider.getUserDao();
            Optional<User> user = userDao.findUserByLogin(login);
            if (user.isEmpty()) {
                return Optional.empty();
            }

            if (hashString(password).equals(user.get().getPassword())) {
                return user;
            } else {
                return Optional.empty();
            }
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while searching for registered user login={}. {}", login, e.getMessage());
            throw new ServiceException("Error while searching for registered user login=" + login, e);
        }
    }

    @Override
    public User registerUser(UserRole userRole, String login, String password,
                             String name, String surname, String patronymic,
                             String email, UserStatus userStatus) throws ServiceException {
        UserDao userDao = DaoProvider.getInstance().getUserDao();
        User user = new User.Builder()
                .setRole(userRole)
                .setLogin(login)
                .setPassword(hashString(password))
                .setName(name)
                .setSurname(surname)
                .setPatronymic(patronymic)
                .setEmail(email)
                .setStatus(userStatus)
                .build();
        try {
            int id = userDao.insert(user);
            user.setId(id);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error during user registration, login={}. {}", login, e.getMessage());
            throw new ServiceException("Error during user registration, login=" + login, e);
        }
        return user;
    }

    @Override
    public boolean isLoginAvailableForNewUser(String login) throws ServiceException {
        try {
            UserDao userDao = DaoProvider.getInstance().getUserDao();
            Optional<User> user = userDao.findUserByLogin(login);
            return user.isEmpty();
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while searching for user by login={}: {}", login, e.getMessage());
            throw new ServiceException("Error while searching for user by login=" + login, e);
        }
    }

    @Override
    public boolean isEmailAvailableForNewUser(String email) throws ServiceException {
        try {
            UserDao userDao = DaoProvider.getInstance().getUserDao();
            Optional<User> user = userDao.findUserByEmail(email);
            return user.isEmpty();
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while searching for user by email={}. {}", email, e.getMessage());
            throw new ServiceException("Error while searching for user by email=" + email, e);
        }
    }

    @Override
    public String createUserToken(User user) throws ServiceException {
        String token = hashString(user.toString() + LocalDateTime.now());
        UserTokenDao userTokenDao = DaoProvider.getInstance().getUserTokenDao();
        try {
            userTokenDao.insetUserToken(user.getId(), token, LocalDateTime.now());
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while generating user token. {}", e.getMessage());
            throw new ServiceException("Error while generating user token.", e);
        }
        return token;
    }

    @Override
    public Optional<UserToken> findValidToken(String token, long userId) throws ServiceException {
        try {
            UserTokenDao userTokenDao = DaoProvider.getInstance().getUserTokenDao();
            Optional<UserToken> userToken = userTokenDao.findTokenByValue(token, userId);
            if (userToken.isEmpty()) {
                return Optional.empty();
            }
            String tokenValue = userToken.get().getToken();
            LocalDateTime timestamp = userToken.get().getCreationTimestamp();
            if (!tokenValue.equals(token) || timestamp.plusMinutes(LINK_EXPIRE_TIMEOUT).isBefore(LocalDateTime.now())) {
                return Optional.empty();
            }
            return userToken;
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while checking user token={}. {}", token, e.getMessage());
            throw new ServiceException("Error while checking user token=" + token, e);
        }
    }

    @Override
    public void confirmEmail(long userId) throws ServiceException {
        UserDao userDao = DaoProvider.getInstance().getUserDao();
        try {
            Optional<User> user = userDao.findEntityById(userId);
            if (user.isEmpty()) {
                logger.log(Level.ERROR, "User with not found, id={}", userId);
                throw new ServiceException("User with not found, id=" + userId);
            }
            UserStatus updatedStatus =
                    user.get().getRole() == UserRole.STUDENT ? UserStatus.ACTIVE : UserStatus.WAITING_FOR_APPROVEMENT;
            userDao.updateUserStatus(userId, updatedStatus);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while updating user status, id={}. {}", userId, e.getMessage());
            throw new ServiceException("Error while updating user status, id=" + userId, e);
        }
    }

    @Override
    public Optional<User> findUserByLogin(String login) throws ServiceException {
        UserDao userDao = DaoProvider.getInstance().getUserDao();
        try {
            return userDao.findUserByLogin(login);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while searching for user with login={}. {}", login, e.getMessage());
            throw new ServiceException("Error while searching for user with login=" + login, e);
        }
    }

    @Override
    public Optional<User> findUserById(Long id) throws ServiceException {
        UserDao userDao = DaoProvider.getInstance().getUserDao();
        try {
            return userDao.findEntityById(id);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while searching for user with id={}. {}", id, e.getMessage());
            throw new ServiceException("Error while searching for user with id=" + id, e);
        }
    }

    @Override
    public List<TeacherDto> findTeachersForRequest(int pageNumber, String searchLine) throws ServiceException {
        List<User> users;
        UserDao userDao = DaoProvider.getInstance().getUserDao();
        int skipAmount = (pageNumber - 1) * ITEMS_ON_PAGE_COUNT;
        try {
            if (searchLine == null) {
                users = userDao.selectTeachersForPage(skipAmount, ITEMS_ON_PAGE_COUNT);
            } else {
                users = userDao.selectTeachersWithSearchForPage(skipAmount, ITEMS_ON_PAGE_COUNT, searchLine);
            }
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while selecting teachers for page. {}", e.getMessage());
            throw new ServiceException("Error while selecting teachers for page", e);
        }

        List<TeacherDto> teacherDtos = new ArrayList<>(users.size());
        for (User user : users) {
            Optional<TeacherDescription> optionalDescription = findTeacherDescriptionByTeacherId(user.getId());
            TeacherDto teacherDto = createTeacherDto(user, optionalDescription.orElse(null));
            teacherDtos.add(teacherDto);
        }
        return teacherDtos;
    }

    @Override
    public int calcPagesCount(int teacherCount) {
        int result = (int) Math.ceil((double) teacherCount / ITEMS_ON_PAGE_COUNT);
        return result != 0 ? result : MIN_PAGE_COUNT;
    }

    @Override
    public int countTeachersForRequest(String searchLine) throws ServiceException {
        int teacherAmount;
        UserDao userDao = DaoProvider.getInstance().getUserDao();
        try {
            if (searchLine == null) {
                teacherAmount = userDao.countTeachers();
            } else {
                teacherAmount = userDao.countTeachersWithSearch(searchLine);
            }
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while counting courses. {}", e.getMessage());
            throw new ServiceException("Error while counting courses", e);
        }
        return teacherAmount;
    }

    @Override
    public List<TeacherDto> trimTeachersDescriptionForPreview(List<TeacherDto> teachers) {
        return teachers.stream()
                .map(teacherDto -> {
                    if (teacherDto.isDescriptionProvided()) {
                        String selfDescription = teacherDto.getSelfDescription();
                        String shortSelfDescription = DescriptionUtil.trimDescriptionForPreview(selfDescription);
                        teacherDto.setSelfDescription(shortSelfDescription);
                    }
                    return teacherDto;
                }).collect(Collectors.toList());
    }

    @Override
    public Optional<TeacherDto> findTeacherById(long teacherId) throws ServiceException {

        UserDao userDao = DaoProvider.getInstance().getUserDao();
        try {
            Optional<User> optionalUser = userDao.findEntityById(teacherId);
            if (optionalUser.isEmpty()) {
                return Optional.empty();
            }
            User user = optionalUser.get();
            Optional<TeacherDescription> optionalDescription = findTeacherDescriptionByTeacherId(user.getId());
            return Optional.of(createTeacherDto(user, optionalDescription.orElse(null)));
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while searching for teacher id={}. {}", teacherId, e.getMessage());
            throw new ServiceException("Error while searching for teacher id=" + teacherId, e);
        }
    }

    @Override
    public List<LocalTime> findTeacherFreeSlotsForDate(long teacherId, LocalDate date) throws ServiceException {
        TeacherScheduleService scheduleService = ServiceProvider.getInstance().getTeacherScheduleService();
        UserService userService = ServiceProvider.getInstance().getUserService();
        Optional<User> teacher = userService.findUserById(teacherId);
        if (teacher.isEmpty() || teacher.get().getRole() != UserRole.TEACHER) {
            logger.log(Level.ERROR, "Invalid teacher id={}", teacherId);
            throw new ServiceException("Invalid teacher id=" + teacherId);
        }
        int dayOfWeek = date.getDayOfWeek().getValue();
        Optional<TeacherSchedule> scheduleOptional = scheduleService.findScheduleForDay(teacherId, dayOfWeek);
        if (scheduleOptional.isEmpty()) {
            return new ArrayList<>();
        }
        TeacherSchedule teacherSchedule = scheduleOptional.get();
        List<LocalTime> freeSlots = new ArrayList<>();
        LocalTime startTime = teacherSchedule.getStartTime();
        LocalTime endTime = teacherSchedule.getEndTime();
        while (startTime.isBefore(endTime)) {
            freeSlots.add(startTime);
            startTime = startTime.plusHours(1);
        }
        LessonScheduleService lessonScheduleService = ServiceProvider.getInstance().getLessonScheduleService();
        List<LessonSchedule> teacherScheduleForDate = lessonScheduleService.findTeacherLessonsForDate(teacherId, date);
        List<LocalTime> busySlots = teacherScheduleForDate.stream()
                .map(lessonSchedule -> lessonSchedule.getStartDateTime().toLocalTime())
                .collect(Collectors.toList());
        freeSlots.removeAll(busySlots);
        return freeSlots;
    }

    @Override
    public int findTeacherFreeSlotCountForNextMonth(long teacherId) throws ServiceException {
        LocalDate date = LocalDate.now().plusDays(1);
        int slotCount = 0;
        for (int i = 0; i < DEFAULT_SUBSCRIPTION_LENGTH_DAYS; i++) {
            slotCount += findTeacherFreeSlotsForDate(teacherId, date).size();
            date = date.plusDays(1);
        }
        return slotCount;
    }

    @Override
    public List<User> findTeachersForCourse(long courseId) throws ServiceException {
        UserDao userDao = DaoProvider.getInstance().getUserDao();
        try {
            return userDao.selectTeachersForCourse(courseId);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while searching for teacher for course id={}. {}", courseId, e.getMessage());
            throw new ServiceException("Error while searching for teacher for course id=" + courseId, e);
        }
    }

    private TeacherDto createTeacherDto(User user, TeacherDescription teacherDescription) {
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setTeacherId(user.getId());
        teacherDto.setName(user.getName());
        teacherDto.setSurname(user.getSurname());
        teacherDto.setPatronymic(user.getPatronymic());
        teacherDto.setStatus(user.getStatus());
        boolean isDescriptionProvided = teacherDescription != null;
        teacherDto.setDescriptionProvided(isDescriptionProvided);
        if (isDescriptionProvided) {
            teacherDto.setExperienceYears(teacherDescription.getExperience());
            teacherDto.setPicturePath(teacherDescription.getPicturePath());
            teacherDto.setSelfDescription(teacherDescription.getDescription());
        }
        return teacherDto;
    }

    private Optional<TeacherDescription> findTeacherDescriptionByTeacherId(long id) throws ServiceException {
        TeacherDescriptionDao descriptionDao = DaoProvider.getInstance().getTeacherDescriptionDao();
        Optional<TeacherDescription> description;
        try {
            description = descriptionDao.findEntityById(id);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while searching for teacher description, id: {}. {}", id, e.getMessage());
            throw new ServiceException("Error while searching for teacher description, id=" + id, e);
        }
        return description;
    }

    private String hashString(String s) {
        return DigestUtils.sha3_256Hex(s);
    }
}
