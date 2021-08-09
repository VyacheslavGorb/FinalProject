package edu.gorb.musicstudio.model.service.impl;

import edu.gorb.musicstudio.entity.TeacherSchedule;
import edu.gorb.musicstudio.exception.DaoException;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.dao.DaoProvider;
import edu.gorb.musicstudio.model.dao.TeacherScheduleDao;
import edu.gorb.musicstudio.model.service.TeacherScheduleService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalTime;
import java.util.List;

public class TeacherScheduleServiceImpl implements TeacherScheduleService {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public List<TeacherSchedule> findAllById(long teacherId) throws ServiceException {
        TeacherScheduleDao teacherScheduleDao = DaoProvider.getInstance().getTeacherScheduleDao();
        try {
            return teacherScheduleDao.findSchedulesForTeacher(teacherId);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while searching for teacher schedules teacher id={}. {}",
                    teacherId, e.getMessage());
            throw new ServiceException("Error while searching for teacher schedules teacher id=" + teacherId, e);
        }
    }

    @Override
    public boolean alterSchedule(long teacherId, String startHourParam, String endHourParam, int dayOfWeek, boolean isRemove)
            throws ServiceException {
        TeacherScheduleDao scheduleDao = DaoProvider.getInstance().getTeacherScheduleDao();
        try {
            boolean scheduleExist = scheduleDao.findScheduleForTeacher(teacherId, dayOfWeek).isPresent();
            if (!scheduleExist && isRemove) {
                logger.log(Level.ERROR, "Schedule for teacher doesn't exist");
                return false;
            }

            if (isRemove) {
                scheduleDao.removeSchedule(teacherId, dayOfWeek);
                return true;
            }

            LocalTime startTime = LocalTime.MIDNIGHT.plusHours(Integer.parseInt(startHourParam));
            LocalTime endTime = LocalTime.MIDNIGHT.plusHours(Integer.parseInt(endHourParam));

            if (scheduleExist) {
                scheduleDao.update(new TeacherSchedule(teacherId, dayOfWeek, startTime, endTime));
            } else {
                scheduleDao.insert(new TeacherSchedule(teacherId, dayOfWeek, startTime, endTime));
            }
        } catch (DaoException | NumberFormatException e) {
            logger.log(Level.ERROR, "Error while altering teacher schedule teacher id={}. {}",
                    teacherId, e.getMessage());
            throw new ServiceException("Error while altering teacher schedule teacher id=" + teacherId, e);
        }

        return true;
    }
}
