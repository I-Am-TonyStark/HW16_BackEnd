package com.mamalimomen.controllers.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class DownloadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (PrintWriter out = resp.getWriter()) {
            resp.setContentType("text/html");
            String file = "people.zip";
            String filePath = "D:\\عزم راسخ\\جاوا مکتب\\کلاس\\46\\HW16_BackEnd\\src\\main\\webapp\\upload\\";
            resp.setContentType("APPLICATION/OCTET-STREAM");
            resp.setHeader("Content-Disposition", "attachment; filename=\""
                    + file + "\"");

            FileInputStream fileInputStream = new FileInputStream(filePath
                    + file);

            int i;
            while ((i = fileInputStream.read()) != -1) {
                out.write(i);
            }
            fileInputStream.close();
        }

        getServletContext().removeAttribute("people");
    }
}
