package edu.gorb.musicstudio.conroller;

import edu.gorb.musicstudio.conroller.command.RequestParameter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ImageServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();
    private static final String PICTURE_PROPERTIES = "properties/picture.properties";
    private static final String BASE_PATH_PROPERTY = "image.path.base";

    private String basePicturePath;

    @Override
    public void init() throws ServletException {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            Properties properties = new Properties();
            properties.load(classLoader.getResourceAsStream(PICTURE_PROPERTIES));
            basePicturePath = properties.getProperty(BASE_PATH_PROPERTY);
        } catch (IOException e) {
            logger.log(Level.ERROR, "Property file exists");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getParameter(RequestParameter.PATH);
        if (path == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        String fullPath = basePicturePath + path;
        try (InputStream fileStream = new FileInputStream(fullPath)) {
            ServletOutputStream outputStream = resp.getOutputStream();
            fileStream.transferTo(outputStream);
        } catch (IOException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
