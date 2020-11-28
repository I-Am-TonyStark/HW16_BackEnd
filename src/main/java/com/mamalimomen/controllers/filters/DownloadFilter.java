package com.mamalimomen.controllers.filters;

import com.mamalimomen.base.controllers.utilities.ApachePOI;
import com.mamalimomen.domains.Person;

import javax.servlet.*;
import java.io.IOException;
import java.util.List;

public class DownloadFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ServletContext context = servletRequest.getServletContext();
        List<Person> people = (List<Person>) context.getAttribute("people");

            ApachePOI.groupingExcelFiles(context.getInitParameter("uploadDirectory"), context.getInitParameter("excelFilesName"), people, new Person[10]);
            ApachePOI.writeNewWordFile(context.getInitParameter("uploadDirectory"), context.getInitParameter("wordFileName"), servletRequest.getParameter("document"));
            ApachePOI.createZipFile(context.getInitParameter("uploadDirectory"), context.getInitParameter("zipFileName"));
        System.out.println();
        filterChain.doFilter(servletRequest, servletResponse);
        ApachePOI.formatDirectory(context.getInitParameter("uploadDirectory"));
    }

    @Override
    public void destroy() {

    }
}
