<%--
  Name: Sai Manogna Pentyala
  Andrew: spentyal
  Task: Project 1 Task 2a
  Description: Form to ask the input from the user - the name of the bird
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- to make it responsive --%>
<% if (request.getAttribute("doctype") != null) { %>
<%= request.getAttribute("doctype") %>
<% } %>
<html>
<head>
    <title>Project 1 Task 2a</title>
</head>
<body>
<form action="getBirdDropDown" method="GET">
    <h1><b>Migratory Birds</b></h1><br> <br>
    Created by Sai Manogna Pentyala <br><br>
    <label for="birdName">Enter the name of a bird </label> <br><br>
    <%-- name of the bird --%>
    <input type="text" name="birdName" id="birdName" value=""/> <br><br>
    <%-- submit button --%>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>
