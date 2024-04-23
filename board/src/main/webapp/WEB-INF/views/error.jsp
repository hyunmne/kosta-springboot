<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>error</title>
</head>
<body>
<jsp:include page="main.jsp"/>
<h3 style="text-align:center;"><%=request.getAttribute("err") %></h3>
</body>
</html>