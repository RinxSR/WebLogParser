package servlets;

import dbService.DAO.MessageDAO;
import dbService.DBService;
import dbService.dataSets.MessageDataSet;
import logic.Message;
import logic.MyLogParser;
import org.hibernate.Session;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "LogParserServlet", urlPatterns = {"/log-parser"})
@MultipartConfig
public class LogParserServlet extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");


        final Part filePart = request.getPart("file");
        final String fileName = getFileName(filePart);
        final PrintWriter writer = response.getWriter();
        final InputStream fileContent = filePart.getInputStream();

        try {

            if (fileName.equals("")) {
                throw new FileNotFoundException();
            }

            MyLogParser logParser = new MyLogParser();
            ArrayList<Message> messages = logParser.parseLogFile(fileContent);

            String handledText = logParser.getHandledLog();
            String sourceText = logParser.getSourceLog();



            Session session = DBService.getSession();
            MessageDAO messageDAO = new MessageDAO(session);

            session.beginTransaction();
            for (Message message : messages) {
                messageDAO.insert(new MessageDataSet(message));
            }
            session.getTransaction().commit();





            request.setAttribute("sourceText", sourceText);
            request.setAttribute("handledText", handledText);

            getServletContext().getRequestDispatcher("/log-parser.jsp").forward(request, response);

        } catch (FileNotFoundException e) {
            getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);

        } finally {
            if (fileContent != null) {
                fileContent.close();
            }
            if (writer != null) {
                writer.close();
            }
        }
    }

    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}