package edu.gorb.musicstudio.model.service;

import edu.gorb.musicstudio.entity.TeacherSchedule;
import edu.gorb.musicstudio.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface TeacherScheduleService {
    List<TeacherSchedule> findAllById(long teacherId) throws ServiceException;

    boolean alterSchedule(long teacherId, String startTimeParam, String endTimeParam, int dayOfWeek, boolean isRemove)
            throws ServiceException;

    Optional<TeacherSchedule> findScheduleForDay(long teacherId, int dayOfWeek) throws ServiceException;
}
