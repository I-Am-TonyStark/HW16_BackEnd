package com.mamalimomen.controllers.servlets;

import com.mamalimomen.base.controllers.utilities.ApachePOI;
import com.mamalimomen.domains.Person;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class DownloadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = req.getServletContext();
        /*List<Person> people = (List<Person>) context.getAttribute("people");
        if (people != null) {
            ApachePOI.groupingExcelFiles(context.getInitParameter("uploadDirectory"), context.getInitParameter("excelFilesName"), people, new Person[10]);
            ApachePOI.writeNewWordFile(context.getInitParameter("uploadDirectory"), context.getInitParameter("wordFileName"), req.getParameter("document"));
            ApachePOI.createZipFile(context.getInitParameter("uploadDirectory"), context.getInitParameter("zipFileName"));
        }*/

        try (PrintWriter out = resp.getWriter()) {

            String file = context.getInitParameter("zipFileName");
            String directory = context.getInitParameter("uploadDirectory");

            resp.setContentType("text/html");
            resp.setContentType("APPLICATION/OCTET-STREAM");
            resp.setHeader("Content-Disposition", "attachment; filename=\"" + file + "\"");

            try {
                FileInputStream fileInputStream = new FileInputStream(directory + file);

                int i;
                while ((i = fileInputStream.read()) != -1) {
                    out.write(i);
                }

                fileInputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        //ApachePOI.formatDirectory(context.getInitParameter("uploadDirectory"));
        getServletContext().removeAttribute("people");
    }
}
