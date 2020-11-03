<%@ page contentType="text/html; ISO-8859-1;charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="description" content="Insert"/>
    <meta name="keywords" content="HTML, CSS, Java, Servlet, Filter, MongoDB"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Insert page</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700"/>
    <link rel="stylesheet" href="page_style.css"/>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.1/css/all.css"
          integrity="sha384-5sAR7xN1Nv6T6+dT2mhtzEpVJvfS3NScPQTrOxhwjIuvcA67KV2R5Jz6kr4abQsz" crossorigin="anonymous">

</head>
<body>
<div class="main-block">
    <h1>Insert Manually</h1>
    <form name="insert" method="get" action="/HW16_BackEnd_war/insert">
        <hr>
        <label id="icon" for="first_name"><i class="fas fa-user"></i></label>
        <input type="text" name="first_name" id="first_name" placeholder="FirstName" required/>
        <label id="icon" for="last_name"><i class="fas fa-user"></i></label>
        <input type="text" name="last_name" id="last_name" placeholder="LastName" required/>
        <label id="icon" for="phone_number"><i class="fas fa-user"></i></label>
        <input type="number" name="phone_number" id="phone_number" placeholder="PhoneNumber" required/>
        <hr>
        <% String message = (String) request.getAttribute("message");
        if (message != null && !message.isEmpty()) { %>
        <h6><%=message%>
        </h6>
        <hr>
        <%}%>
        <div class="btn-block">
            <p>For download or see please click <b><a href="download_page.jsp">Download</a></b> here!</p>
            <p>For inserting from file please click <b><a href="upload_page.jsp">Upload</a></b> here!</p>
            <button type="submit">Insert</button>
        </div>
    </form>
</div>
</body>
</html>