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
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class LessonScheduleServiceImpl implements LessonScheduleService {
    private static final Logger logger = LogManager.getLogger();
    private static final String DATE_FORMAT = "dd.MM.yyyy";
    private DateTimeFormatter formatter;

    public LessonScheduleServiceImpl() {
        formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
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
                    if(o1.isAfter(o2)){
                        result = 1;
                    }
                    if(o1.isBefore(o2)){
                        result = -1;
                    }
                    return result;
                })
                .map(localDate -> localDate.format(formatter))
                .collect(Collectors.toList());
    }

    private LessonScheduleDto createLessonScheduleDto(User teacher, User student, Course course,
                                                      LessonSchedule lessonSchedule) {
        LessonScheduleDto lessonScheduleDto = new LessonScheduleDto();
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
