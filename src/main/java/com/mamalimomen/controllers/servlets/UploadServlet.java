package com.mamalimomen.controllers.servlets;

import com.mamalimomen.base.controllers.utilities.ApachePOI;
import com.mamalimomen.base.controllers.utilities.ApacheUpload;
import com.mamalimomen.controllers.utilities.AppManager;
import com.mamalimomen.controllers.utilities.Services;
import com.mamalimomen.domains.Person;
import com.mamalimomen.services.PersonService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class UploadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("filePath", ApacheUpload.fileUpload(req));

        try {
            PersonService ps = AppManager.getService(Services.PERSON_SERVICE);
            List<Person> people = ApachePOI.readExcelFile((String) req.getAttribute("filePath"), Person.class, 0);

            String message;

            if (ps.saveMany(people))
                message = "Persons add to database successfully!";
            else message = "There is a problem when adding to database!";

            //ApachePOI.formatDirectory(req.getServletContext().getInitParameter("uploadDirectory"));

            req.setAttribute("message", message);

            RequestDispatcher dispatcher = req.getRequestDispatcher("upload_page.jsp");
            dispatcher.forward(req, resp);
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
