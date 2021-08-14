package edu.gorb.musicstudio.model.service;

import edu.gorb.musicstudio.entity.TeacherDescription;
import edu.gorb.musicstudio.exception.ServiceException;

import javax.servlet.http.Part;
import java.util.List;
import java.util.Optional;

public interface TeacherDescriptionService {
    /**
     * Finds teacher description by teacher id
     *
     * @param teacherId teacher id
     * @return optional of {@link TeacherDescription}
     */
    Optional<TeacherDescription> findTeacherDescriptionByTeacherId(long teacherId) throws ServiceException;

    /**
     * Checks if teacher description exists
     *
     * @param teacherId teacher id
     * @return if teacher description exists
     */
    boolean teacherDescriptionExists(long teacherId) throws ServiceException;

    /**
     * Saves teacher description
     *
     * @param teacherId           teacher id
     * @param imageParts          teacher image parts
     * @param description         teacher text description
     * @param workExperienceYears teacher work experience in years
     */
    void saveTeacherDescription(long teacherId, List<Part> imageParts, String description, int workExperienceYears)
            throws ServiceException;

    /**
     * Updates teacher description with image upload
     *
     * @param teacherId           teacher id
     * @param imageParts          teacher image parts
     * @param description         teacher text description
     * @param workExperienceYears teacher work experience in years
     */
    void updateTeacherDescriptionWithImageUpload(long teacherId, List<Part> imageParts, String description, int workExperienceYears)
            throws ServiceException;

    /**
     * Updates teacher description
     *
     * @param teacherId           teacher id
     * @param description         teacher text descriptions
     * @param workExperienceYears teacher work experience in years
     */
    void updateTeacherDescription(long teacherId, String description, int workExperienceYears)
            throws ServiceException;
}
