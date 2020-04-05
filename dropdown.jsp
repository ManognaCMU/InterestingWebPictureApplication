<%--
  Name: Sai Manogna Pentyala
  Andrew: spentyal
  Task: Project 1 Task 2a
  Description: To create the dropdown list containing the bird names
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"
           prefix="fn" %>
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
<% boolean errorFlag = false; %>
<%-- when the bird requested for is not present in the zoo site --%>
<% if (request.getAttribute("birdSubTotalListSize") != null && request.getAttribute("birdSubTotalListSize").equals("zero")) {
    errorFlag = true; %>
<h2> Sorry ! <%= request.getAttribute("actualBirdName")%> Bird Cannot Be Found ! </h2><br><br><br>
<h4> Please try again by choosing another bird </h4>
<%-- when the hit to the zoo site failed --%>
<% } else if (request.getAttribute("error") != null) {
    errorFlag = true; %>
<h2><%= request.getAttribute("error")%></h2> <br>
<h4> Please try again after sometime </h4>
<%-- when the user enters no bird name and clicks on submit --%>
<% } else if(request.getAttribute("nobird") != null) {
    errorFlag = true;
%>
<h2><%= request.getAttribute("nobird")%></h2>
<% } %> <br>
<%-- if any of the above errors happen, then the user can try again by giving the input --%>
<% if (errorFlag) { %>
<form action="getFirstInput" method="GET">
    <button type="continue" value="continue">Try Again</button>
</form>
<% } else { %>
<form method="GET" action="getImageResult">
    <br>
    <label> Choose a bird from the dropdown </label> <br><br>
    <%-- dropdown list containing bird names--%>
    <select name="birdDropDownList">
        <c:forEach items="${birdSubTotalList}" var="bird">
            <option value="${fn:replace(bird, '|', ' ')}" name="eachBird">${bird}</option>
        </c:forEach>
    </select><br><br>
    <%-- submit button --%>
    <input type="submit" value="Submit"/>
    <% } %>
</form>
</body>
</html>
