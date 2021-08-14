package edu.gorb.musicstudio.model.service.impl;

import edu.gorb.musicstudio.entity.TeacherDescription;
import edu.gorb.musicstudio.exception.DaoException;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.dao.DaoProvider;
import edu.gorb.musicstudio.model.dao.TeacherDescriptionDao;
import edu.gorb.musicstudio.model.service.TeacherDescriptionService;
import edu.gorb.musicstudio.util.HtmlEscapeUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Part;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class TeacherDescriptionServiceImpl implements TeacherDescriptionService {
    private static final Logger logger = LogManager.getLogger();
    private static final String PICTURE_PROPERTIES = "properties/picture.properties";
    private static final String BASE_PATH_PROPERTY = "image.path.base";
    private static final String TEACHER_FOLDER_PROPERTY = "image.path.teacher";
    private static final String JPEG_EXTENSION = ".jpeg";
    private static final String DEFAULT_BASE_PATH = "D:/pic/";
    private static final String DEFAULT_TEACHER_PATH = "teacher/";

    private String basePicturePath;
    private String teacherFolderPath;

    public TeacherDescriptionServiceImpl() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            Properties properties = new Properties();
            properties.load(classLoader.getResourceAsStream(PICTURE_PROPERTIES));
            basePicturePath = properties.getProperty(BASE_PATH_PROPERTY);
            teacherFolderPath = properties.getProperty(TEACHER_FOLDER_PROPERTY);
            if(basePicturePath == null || teacherFolderPath == null){
                basePicturePath = DEFAULT_BASE_PATH;
                teacherFolderPath = DEFAULT_TEACHER_PATH;
            }
        } catch (IOException e) {
            logger.log(Level.ERROR, "Error while reading property file {}. {}", PICTURE_PROPERTIES, e.getMessage());
            basePicturePath = DEFAULT_BASE_PATH;
            teacherFolderPath = DEFAULT_TEACHER_PATH;
        }
    }

    @Override
    public Optional<TeacherDescription> findTeacherDescriptionByTeacherId(long teacherId) throws ServiceException {
        TeacherDescriptionDao descriptionDao = DaoProvider.getInstance().getTeacherDescriptionDao();
        try {
            return descriptionDao.findEntityByTeacherId(teacherId);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while searching for teacher description by id={}", teacherId, e);
            throw new ServiceException("Error while searching for teacher description by id=" + teacherId, e);
        }
    }

    @Override
    public boolean teacherDescriptionExists(long teacherId) throws ServiceException {
        TeacherDescriptionDao descriptionDao = DaoProvider.getInstance().getTeacherDescriptionDao();
        try {
            return descriptionDao.findEntityByTeacherId(teacherId).isPresent();
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while searching for teacher description by id={}", teacherId, e);
            throw new ServiceException("Error while searching for teacher description by id=" + teacherId, e);
        }
    }

    @Override
    public void saveTeacherDescription(long teacherId, List<Part> imageParts, String descriptionContent, int workExperienceYears)
            throws ServiceException {
        TeacherDescriptionDao descriptionDao = DaoProvider.getInstance().getTeacherDescriptionDao();
        String relativeImagePath = teacherFolderPath + teacherId + JPEG_EXTENSION;
        descriptionContent = HtmlEscapeUtil.escape(descriptionContent);
        TeacherDescription teacherDescription = new TeacherDescription(
                teacherId,
                descriptionContent,
                workExperienceYears,
                relativeImagePath);
        savePicture(relativeImagePath, imageParts);
        try {
            descriptionDao.insert(teacherDescription);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while inserting teacher description. {}", e.getMessage());
            throw new ServiceException("Error while inserting teacher description", e);
        }
    }

    @Override
    public void updateTeacherDescriptionWithImageUpload(long teacherId, List<Part> imageParts, String descriptionContent, int workExperienceYears)
            throws ServiceException {
        TeacherDescriptionDao descriptionDao = DaoProvider.getInstance().getTeacherDescriptionDao();
        String relativeImagePath = teacherFolderPath + teacherId + JPEG_EXTENSION;
        descriptionContent = HtmlEscapeUtil.escape(descriptionContent);
        TeacherDescription teacherDescription = new TeacherDescription(
                teacherId,
                descriptionContent,
                workExperienceYears,
                relativeImagePath);
        savePicture(relativeImagePath, imageParts);
        try {
            descriptionDao.update(teacherDescription);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while updating teacher description. {}", e.getMessage());
            throw new ServiceException("Error while updating teacher description", e);
        }
    }

    @Override
    public void updateTeacherDescription(long teacherId, String description, int workExperienceYears)
            throws ServiceException {
        TeacherDescriptionDao descriptionDao = DaoProvider.getInstance().getTeacherDescriptionDao();
        try {
            Optional<TeacherDescription> optionalDescription = descriptionDao.findEntityByTeacherId(teacherId);
            if (optionalDescription.isEmpty()) {
                throw new ServiceException();
            }
            TeacherDescription teacherDescription = optionalDescription.get();
            String escapedDescription = HtmlEscapeUtil.escape(description);
            teacherDescription.setDescription(escapedDescription);
            teacherDescription.setExperience(workExperienceYears);
            descriptionDao.update(teacherDescription);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while updating teacher description. {}", e.getMessage());
            throw new ServiceException("Error while updating teacher description", e);
        }
    }


    private void savePicture(String relativePath, List<Part> imageParts) throws ServiceException {
        String absolutePath = basePicturePath + relativePath;
        try (FileOutputStream fileOutputStream = new FileOutputStream(absolutePath)) {
            for (Part part : imageParts) {
                part.getInputStream().transferTo(fileOutputStream);
            }
        } catch (IOException e) {
            logger.log(Level.ERROR, "Error while saving image. {}", e.getMessage());
            throw new ServiceException("Error while saving image. " + e.getMessage(), e);
        }
    }
}
