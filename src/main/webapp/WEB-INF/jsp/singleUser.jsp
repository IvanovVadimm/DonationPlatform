<%@ page import="com.example.DonationPlatform.domain.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% User userJsp = (User) request.getAttribute("user");%>
<html>
<head>
    <title>User info</title>
</head>
<body>
<h1> Hello, this is your User!</h1>
<h3>User id:  <%=userJsp.getId()%></h3>
<h3>User birthdate:  <%=userJsp.getBirthdate()%></h3>
<h3>User login:  <%=userJsp.getLogin()%></h3>
<h3>User password:  <%=userJsp.getPassword()%></h3>
<h3>User email:  <%=userJsp.getEmail()%></h3>
</body>
</html>
