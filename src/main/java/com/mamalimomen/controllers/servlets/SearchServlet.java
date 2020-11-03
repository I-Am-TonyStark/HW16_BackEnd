package com.mamalimomen.controllers.servlets;

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
import java.util.List;

public class SearchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().removeAttribute("people");

        PersonService ps = AppManager.getService(Services.PERSON_SERVICE);

        String message;

        List<Person> people = ps.retrieveExistPersons(req);

        if (people == null) {
            message = "wrong radio button!";
        } else if (people.isEmpty()) {
            message = "there is not any result!";
        } else {
            message = "here you are!";
        }

        req.setAttribute("message", message);
        getServletContext().setAttribute("people", people);

        String destPage = "download_page.jsp";
        RequestDispatcher dispatcher = req.getRequestDispatcher(destPage);
        dispatcher.forward(req, resp);
    }
}
