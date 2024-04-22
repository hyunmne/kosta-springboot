<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String cookieHeader = request.getHeader("Cookie");
	boolean autoLogin = false;
	String id = "";
	String password = "";
	if(cookieHeader!=null){
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies) {
			if(cookie.getName().equals("autoLogin")) {
				if (cookie.getValue().equals("true"))
					autoLogin = true;
				else autoLogin = false;
			} else if (cookie.getName().equals("id")) {
				id = cookie.getValue();
			} else if (cookie.getName().equals("password")) {
				password = cookie.getValue();
			}
		}
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Kosta Bank Login</title>
<style type="text/css">
        .header {
            text-align: center;
        }
        .container {
            margin: 0 auto;
            border: 1px solid;
            width: 280px;
            padding: 10px;
        }</style>
</head>
<body>
<% pageContext.include("header.jsp"); %>
<div class="header"><h3>로그인</h3></div>
<form action="login" method="post">
	<table border="1" class="container">
		<tr>
			<td>아이디</td>
			<td><input type="text" name="id" value="<%=id%>"/></td>
		</tr>
		<tr>
			<td>비밀번호</td>
			<td><input type="password" name="password" value="<%=password%>"/></td>
		</tr>
		<tr>
			<td colspan="2">
			<%if(autoLogin) { %>
				<input type="checkbox" value="true" name="autoLogin" checked="checked"/>자동 로그인
			<%} else { %>
				<input type="checkbox" value="true" name="autoLogin" />자동 로그인
			<%} %>
			</td>
		</tr>
		<tr>
			<td colspan="2"><input type="submit" value="로그인"/></td>
		</tr>
	</table>
</form>
</body>
</html>