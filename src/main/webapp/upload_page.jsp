<%@ page contentType="text/html; ISO-8859-1;charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="description" content="Upload"/>
    <meta name="keywords" content="HTML, CSS, Java, Servlet, Filter, MongoDB"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Upload page</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700"/>
    <link rel="stylesheet" href="page_style.css"/>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.1/css/all.css"
          integrity="sha384-5sAR7xN1Nv6T6+dT2mhtzEpVJvfS3NScPQTrOxhwjIuvcA67KV2R5Jz6kr4abQsz" crossorigin="anonymous">

</head>
<body>
<div class="main-block">
    <h1>Insert From File</h1>
    <form name="upload" method="post" action="/HW16_BackEnd_war/upload" enctype="multipart/form-data">
        <hr>
        <label for="uploaded">Excel File:</label>
        <input type="file" name="uploaded" id="uploaded" size="50" required/>
        <hr>
        <% String message = (String) request.getAttribute("message");
            if (message != null && !message.isEmpty()) { %>
        <h6><%=message%>
        </h6>
        <hr>
        <%}%>
        <div class="btn-block">
            <h5>For search please click <b><a href="download_page.jsp">Search</a></b> here!</h5>
            <h5>For insert manually please click <b><a href="insert_page.jsp">Insert</a></b> here!</h5>
            <button type="submit">Upload</button>
        </div>
    </form>
</div>
</body>
</html>