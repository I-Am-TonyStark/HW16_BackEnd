<%@ page import="com.mamalimomen.domains.Person" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; ISO-8859-1;charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="description" content="Download"/>
    <meta name="keywords" content="HTML, CSS, Java, Servlet, Filter, MongoDB"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Download page</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700"/>
    <link rel="stylesheet" href="page_style.css"/>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.1/css/all.css"
          integrity="sha384-5sAR7xN1Nv6T6+dT2mhtzEpVJvfS3NScPQTrOxhwjIuvcA67KV2R5Jz6kr4abQsz" crossorigin="anonymous">

</head>
<body>
<div class="main-block">
    <h1>See Persons</h1>
    <form name="see" method="get" action="/HW16_BackEnd_war/search">
        <hr>
        <label id="icon" for="information"><i class="fas fa-user"></i></label>
        <input type="text" name="information" id="information" placeholder="Search Key"/><br/>
        <label for="fName_like">FirstName</label>
        <input type="radio" name="search" id="fName_like" checked value="fName_like"/>
        <label for="lName_like">LastName</label>
        <input type="radio" name="search" id="lName_like" value="lName_like"/>
        <label for="phone_like">PhoneNumber</label>
        <input type="radio" name="search" id="phone_like" value="phone_like"/>
        <hr>
        <% String message = (String) request.getAttribute("message");
            if (message != null && !message.isEmpty()) { %>
        <h6><%=message%>
        </h6>
        <hr>
        <%}%>
        <% List<Person> people = (List<Person>) request.getServletContext().getAttribute("people");
            if (people != null && !people.isEmpty()) { %>
        <table border="1">
            <thead>
            <tr>
                <th>id</th>
                <th>first name</th>
                <th>last name</th>
                <th>phone number</th>
            </tr>
            </thead>
            <tbody>
            <% for (Person person : people) {%>
            <tr>
                <td><%=person.getEntityId()%>
                </td>
                <td><%=person.getFirstName()%>
                </td>
                <td><%=person.getLastName()%>
                </td>
                <td><%=person.getPhoneNumber()%>
                </td>
            </tr>
            <%}%>
            </tbody>
        </table>
        <hr>
        <%}%>
        <div class="btn-block">
            <p>For download this persons Excel file please click <b><a href="download">Download</a></b> here!</p>
            <button type="submit">Search</button>
        </div>
    </form>
</div>
</body>
</html>