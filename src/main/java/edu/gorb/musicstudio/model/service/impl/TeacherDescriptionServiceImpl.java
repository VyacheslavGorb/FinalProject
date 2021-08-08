package edu.gorb.musicstudio.model.service.impl;

import edu.gorb.musicstudio.entity.TeacherDescription;
import edu.gorb.musicstudio.exception.DaoException;
import edu.gorb.musicstudio.exception.ServiceException;
import edu.gorb.musicstudio.model.dao.DaoProvider;
import edu.gorb.musicstudio.model.dao.TeacherDescriptionDao;
import edu.gorb.musicstudio.model.service.TeacherDescriptionService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class TeacherDescriptionServiceImpl implements TeacherDescriptionService {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Optional<TeacherDescription> findTeacherDescriptionById(long teacherId) throws ServiceException {
        TeacherDescriptionDao descriptionDao = DaoProvider.getInstance().getTeacherDescriptionDao();
        try {
            return descriptionDao.findEntityById(teacherId);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while searching for teacher description by id={}", teacherId, e);
            throw new ServiceException("Error while searching for teacher description by id=" + teacherId, e);
        }
    }
}
