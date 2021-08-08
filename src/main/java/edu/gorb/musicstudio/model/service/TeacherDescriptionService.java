package edu.gorb.musicstudio.model.service;

import edu.gorb.musicstudio.entity.TeacherDescription;
import edu.gorb.musicstudio.exception.ServiceException;

import javax.sql.rowset.serial.SerialException;
import java.util.Optional;

public interface TeacherDescriptionService {
    Optional<TeacherDescription> findTeacherDescriptionById(long teacherId) throws ServiceException;
}
