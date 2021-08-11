package edu.gorb.musicstudio.model.service.impl;

import edu.gorb.musicstudio.dto.LessonScheduleDto;
import edu.gorb.musicstudio.entity.Course;
import edu.gorb.musicstudio.entity.LessonSchedule;
import edu.gorb.musicstudio.entity.User;
import edu.gorb.musicstudio.exception.DaoException;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.dao.CourseDao;
import edu.gorb.musicstudio.model.dao.DaoProvider;
import edu.gorb.musicstudio.model.dao.LessonScheduleDao;
import edu.gorb.musicstudio.model.dao.UserDao;
import edu.gorb.musicstudio.model.service.LessonScheduleService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class LessonScheduleServiceImpl implements LessonScheduleService {
    private static final Logger logger = LogManager.getLogger();
    private static final String DATE_FORMAT = "dd.MM.yyyy";
    private static final int DEFAULT_LESSON_DURATION_MINUTES = 60;
    private final DateTimeFormatter formatter;

    public LessonScheduleServiceImpl() {
        formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    }

    @Override
    public Optional<LessonSchedule> findEntityById(long lessonId) throws ServiceException {
        LessonScheduleDao lessonScheduleDao = DaoProvider.getInstance().getLessonScheduleDao();
        try {
            return lessonScheduleDao.findEntityById(lessonId);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while searching for lesson schedule by id={}. {}",
                    lessonId, e.getMessage());
            throw new ServiceException("Error while searching for lesson schedule by id=" + lessonId, e);
        }
    }

    @Override
    public void updateStatus(long lessonId, LessonSchedule.LessonStatus status) throws ServiceException {
        LessonScheduleDao lessonScheduleDao = DaoProvider.getInstance().getLessonScheduleDao();
        try {
            lessonScheduleDao.updateStatus(lessonId, status);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while updating status, schedule id={}. {}",
                    lessonId, e.getMessage());
            throw new ServiceException("Error while updating status, schedule id=" + lessonId, e);
        }
    }

    @Override
    public List<LessonScheduleDto> findFutureSchedulesByTeacherId(long teacherId) throws ServiceException {
        LessonScheduleDao lessonScheduleDao = DaoProvider.getInstance().getLessonScheduleDao();
        List<LessonSchedule> lessons;
        try {
            lessons = lessonScheduleDao.findFutureSchedulesForTeacher(teacherId);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while selecting lessons for teacher id={}. {}", teacherId,
                    e.getMessage());
            throw new ServiceException("Error while selecting lessons for teacher id=" + teacherId, e);
        }

        List<LessonScheduleDto> lessonScheduleDtos = new ArrayList<>();
        UserDao userDao = DaoProvider.getInstance().getUserDao();
        CourseDao courseDao = DaoProvider.getInstance().getCourseDao();

        try {
            Optional<User> optionalTeacher = userDao.findEntityById(teacherId);
            if (optionalTeacher.isEmpty()) {
                logger.log(Level.ERROR, "Teacher not found, id={}", teacherId);
                throw new ServiceException("Teacher not found, id=" + teacherId);
            }
            User teacher = optionalTeacher.get();

            for (LessonSchedule lessonSchedule : lessons) {
                Optional<User> optionalStudent = userDao.findEntityById(lessonSchedule.getStudentId());
                Optional<Course> optionalCourse = courseDao.findEntityById(lessonSchedule.getCourseId());
                if (optionalCourse.isEmpty() || optionalStudent.isEmpty()) {
                    logger.log(Level.ERROR, "Lesson schedule contains invalid data, schedule id={}",
                            lessonSchedule.getId());
                    throw new ServiceException("Lesson schedule contains invalid data, schedule id="
                            + lessonSchedule.getId());
                }
                LessonScheduleDto lessonScheduleDto = createLessonScheduleDto(teacher, optionalStudent.get(),
                        optionalCourse.get(), lessonSchedule);
                lessonScheduleDtos.add(lessonScheduleDto);
            }
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while selecting user, {}", e.getMessage());
            throw new ServiceException("Error while selecting user", e);
        }
        return lessonScheduleDtos;
    }

    @Override
    public List<LessonScheduleDto> findFutureSchedulesByStudentId(long studentId) throws ServiceException {
        LessonScheduleDao lessonScheduleDao = DaoProvider.getInstance().getLessonScheduleDao();
        List<LessonSchedule> lessons;
        try {
            lessons = lessonScheduleDao.findFutureSchedulesForStudent(studentId);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while selecting lessons for student id={}. {}", studentId,
                    e.getMessage());
            throw new ServiceException("Error while selecting lessons for student id=" + studentId, e);
        }
        List<LessonScheduleDto> lessonScheduleDtos = new ArrayList<>();
        UserDao userDao = DaoProvider.getInstance().getUserDao();
        CourseDao courseDao = DaoProvider.getInstance().getCourseDao();
        try {
            Optional<User> optionalStudent = userDao.findEntityById(studentId);
            if (optionalStudent.isEmpty()) {
                logger.log(Level.ERROR, "Student not found, id={}", studentId);
                throw new ServiceException("Student not found, id=" + studentId);
            }
            User student = optionalStudent.get();

            for (LessonSchedule lessonSchedule : lessons) {
                Optional<User> optionalTeacher = userDao.findEntityById(lessonSchedule.getTeacherId());
                Optional<Course> optionalCourse = courseDao.findEntityById(lessonSchedule.getCourseId());
                if (optionalCourse.isEmpty() || optionalTeacher.isEmpty()) {
                    logger.log(Level.ERROR, "Lesson schedule contains invalid data, schedule id={}",
                            lessonSchedule.getId());
                    throw new ServiceException("Lesson schedule contains invalid data, schedule id="
                            + lessonSchedule.getId());
                }
                LessonScheduleDto lessonScheduleDto = createLessonScheduleDto(optionalTeacher.get(), student,
                        optionalCourse.get(), lessonSchedule);
                lessonScheduleDtos.add(lessonScheduleDto);
            }
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while selecting user, {}", e.getMessage());
            throw new ServiceException("Error while selecting user", e);
        }
        return lessonScheduleDtos;
    }

    @Override
    public Map<String, List<LessonScheduleDto>> mapLessonDtosToByDate(List<LessonScheduleDto> lessonScheduleDtos) {
        return lessonScheduleDtos.stream()
                .collect(Collectors.groupingBy(lesson -> lesson.getStartDateTime().toLocalDate().format(formatter)));
    }

    @Override
    public List<String> findDistinctDateLines(List<LessonScheduleDto> lessonScheduleDtos) {
        return lessonScheduleDtos.stream()
                .map(lesson -> lesson.getStartDateTime().toLocalDate())
                .distinct()
                .sorted((o1, o2) -> {
                    int result = 0;
                    if (o1.isAfter(o2)) {
                        result = 1;
                    }
                    if (o1.isBefore(o2)) {
                        result = -1;
                    }
                    return result;
                })
                .map(localDate -> localDate.format(formatter))
                .collect(Collectors.toList());
    }

    @Override
    public List<LessonSchedule> findTeacherLessonsForDate(long teacherId, LocalDate date) throws ServiceException {
        LessonScheduleDao lessonScheduleDao = DaoProvider.getInstance().getLessonScheduleDao();
        try {
            return lessonScheduleDao.findScheduleForTeacherForDate(teacherId, date);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while searching for teacher lesson schedule for date. {}", e.getMessage());
            throw new ServiceException("Error while searching for teacher lesson schedule for date", e);
        }
    }

    @Override
    public List<LessonScheduleDto> findLessonSchedulesBySubscription(long subscriptionId) throws ServiceException {
        LessonScheduleDao lessonScheduleDao = DaoProvider.getInstance().getLessonScheduleDao();
        UserDao userDao = DaoProvider.getInstance().getUserDao();
        CourseDao courseDao = DaoProvider.getInstance().getCourseDao();
        try {
            List<LessonSchedule> lessonSchedules = lessonScheduleDao.findLessonSchedulesBySubscription(subscriptionId);
            List<LessonScheduleDto> lessonScheduleDtos = new ArrayList<>();
            for (LessonSchedule schedule : lessonSchedules) {
                Optional<User> optionalStudent = userDao.findEntityById(schedule.getStudentId());
                Optional<User> optionalTeacher = userDao.findEntityById(schedule.getTeacherId());
                Optional<Course> optionalCourse = courseDao.findEntityById(schedule.getCourseId());
                if (optionalStudent.isEmpty() || optionalTeacher.isEmpty() || optionalCourse.isEmpty()) {
                    logger.log(Level.ERROR, "Invalid data in subscription id={}", subscriptionId);
                    throw new ServiceException("Invalid data in subscription id=" + subscriptionId);
                }
                lessonScheduleDtos.add(createLessonScheduleDto(optionalTeacher.get(), optionalStudent.get(), optionalCourse.get(), schedule));
            }
            return lessonScheduleDtos;
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while searching for schedule by subscription id={}. {}",
                    subscriptionId, e.getMessage());
            throw new ServiceException("Error while searching for schedule by subscription id=" + subscriptionId, e);
        }
    }

    @Override
    public void saveNewLessonSchedule(long studentId, long teacherId, long courseId, long subscriptionId,
                                      LocalDateTime startDateTime, LessonSchedule.LessonStatus status)
            throws ServiceException {
        LessonScheduleDao lessonScheduleDao = DaoProvider.getInstance().getLessonScheduleDao();
        try {
            lessonScheduleDao.insert(new LessonSchedule(0, studentId, teacherId, courseId, subscriptionId,
                    startDateTime, LocalTime.MIDNIGHT.plusMinutes(DEFAULT_LESSON_DURATION_MINUTES), status));
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while inserting lesson schedule. {}", e.getMessage());
            throw new ServiceException("Error while inserting lesson schedule.", e);
        }
    }

    private LessonScheduleDto createLessonScheduleDto(User teacher, User student, Course course,
                                                      LessonSchedule lessonSchedule) {
        LessonScheduleDto lessonScheduleDto = new LessonScheduleDto();
        lessonScheduleDto.setScheduleId(lessonSchedule.getId());
        lessonScheduleDto.setStudentName(student.getName());
        lessonScheduleDto.setStudentSurname(student.getSurname());
        lessonScheduleDto.setTeacherName(teacher.getName());
        lessonScheduleDto.setTeacherSurname(teacher.getSurname());
        lessonScheduleDto.setStatus(lessonSchedule.getStatus());
        lessonScheduleDto.setDuration(lessonSchedule.getDuration());
        lessonScheduleDto.setStartDateTime(lessonSchedule.getStartDateTime());
        lessonScheduleDto.setCourseName(course.getName());
        return lessonScheduleDto;
    }
}
