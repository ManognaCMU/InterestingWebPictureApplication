<%--
  Name: Sai Manogna Pentyala
  Andrew: spentyal
  Task: Project 1 Task 2a
  Description: To display the result to the user - the image of the bird, credit and the photogrpaher name
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
<%-- Bird chosen by the user --%>
<h2>Bird Chosen: <%= request.getAttribute("birdName") %>
</h2>
<br><br>
<%-- image of the bird --%>
<img src="<%= request.getAttribute("birdUrl")%>">
<br><br>
Credit: https://nationalzoo.si.edu/migratory-birds
<br><br>
<%-- photographer name of the bird --%>
Photographer: <%= request.getAttribute("photographerName")%>
<br><br><br>
<%-- to go back to the first input page --%>
<form action="getFirstInput" method="GET">
    <button type="continue" value="continue">Continue</button>
</form>
</body>
</html>
