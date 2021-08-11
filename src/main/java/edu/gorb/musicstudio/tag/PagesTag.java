package edu.gorb.musicstudio.tag;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class PagesTag extends TagSupport {
    private static final Logger logger = LogManager.getLogger();
    private String pagesCountAttribute;
    private String searchLine;
    private String command;

    public void setPagesCountAttribute(String pageCountAttribute) {
        this.pagesCountAttribute = pageCountAttribute;
    }

    public void setSearchLine(String searchLine) {
        this.searchLine = searchLine;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public int doStartTag() throws JspException {
        int pageCount;
        try {
            pageCount = Integer.parseInt(pagesCountAttribute);
        } catch (NumberFormatException e) {
            logger.log(Level.ERROR, "Illegal attribute value");
            throw new JspException(e.getMessage());
        }

        try {
            StringBuilder stringBuilder =
                    new StringBuilder("<nav class=\"mt-4\" aria-label=\"Page navigation example\">\n" +
                            "                <ul class=\"pagination pagination-lg flex-wrap justify-content-center\">");


            String contextPath = pageContext.getServletContext().getContextPath();

            String currentTemplate = searchLine.isBlank() ?
                    "<li class=\"page-item\"><a class=\"page-link\" href=\"" + contextPath
                            + "/controller?command=" + command + "&page=%d\">%d</a></li>" :
                    "<li class=\"page-item\"><a class=\"page-link\" href=\"" + contextPath
                            + "/controller?command=" + command + "&page=%d&search=" + searchLine + "\">%d</a></li>";

            for (int i = 1; i <= pageCount; i++) {
                stringBuilder.append(String.format(currentTemplate, i, i));
            }

            stringBuilder.append("</ul>\n" +
                    "            </nav>");
            pageContext.getOut().write(stringBuilder.toString());
        } catch (IOException e) {
            logger.log(Level.ERROR, "Error while writing to stream");
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }
}
