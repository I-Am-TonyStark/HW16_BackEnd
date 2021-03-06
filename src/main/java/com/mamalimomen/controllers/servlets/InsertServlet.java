package com.mamalimomen.controllers.servlets;

import com.mamalimomen.controllers.utilities.AppManager;
import com.mamalimomen.controllers.utilities.Services;
import com.mamalimomen.services.PersonService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class InsertServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PersonService ps = AppManager.getService(Services.PERSON_SERVICE);
        String message = ps.createNewPerson(req);

        req.setAttribute("message", message);

        RequestDispatcher dispatcher = req.getRequestDispatcher("insert_page.jsp");
        dispatcher.forward(req, resp);
    }
}
