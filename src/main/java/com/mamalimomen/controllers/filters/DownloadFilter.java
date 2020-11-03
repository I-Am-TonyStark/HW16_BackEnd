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
        System.out.println("filter download");
        List<Person> people = (List<Person>) servletRequest.getServletContext().getAttribute("people");
        try {
            servletRequest.setAttribute("excelFile", ApachePOI.writeNewExcelFile("D:\\عزم راسخ\\جاوا مکتب\\کلاس\\46\\HW16_BackEnd\\src\\main\\webapp\\upload\\mamali", people.toArray(new Person[0])));
            servletRequest.setAttribute("zipFile", ApachePOI.createZipFile("people", "D:\\عزم راسخ\\جاوا مکتب\\کلاس\\46\\HW16_BackEnd\\src\\main\\webapp\\upload\\"));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
