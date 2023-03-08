<%--
  Created by IntelliJ IDEA.
  User: Siarhey.Pavirayeu
  Date: 2/6/2023
  Time: 9:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Successfully</title>
</head>
<body>
<c:forEach var="card" items="${cards}">
    <p>Cards of user with id: <%request.getParameter("id");%> ${card}</p>
</c:forEach>
</body>
</html>
