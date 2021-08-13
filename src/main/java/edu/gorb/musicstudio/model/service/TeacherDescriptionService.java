package edu.gorb.musicstudio.model.service;

import edu.gorb.musicstudio.entity.TeacherDescription;
import edu.gorb.musicstudio.exception.ServiceException;

import javax.servlet.http.Part;
import java.util.List;
import java.util.Optional;

public interface TeacherDescriptionService {
    Optional<TeacherDescription> findTeacherDescriptionByTeacherId(long teacherId) throws ServiceException;

    boolean teacherDescriptionExists(long teacherId) throws ServiceException;

    void saveTeacherDescription(long teacherId, List<Part> imageParts, String description, int workExperienceYears)
            throws ServiceException;

    void updateTeacherDescriptionWithImageUpload(long teacherId, List<Part> imageParts, String description, int workExperienceYears)
            throws ServiceException;

    void updateTeacherDescription(long teacherId, String description, int workExperienceYears)
            throws ServiceException;
}
