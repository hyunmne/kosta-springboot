<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% 
	String user = (String)session.getAttribute("user");
%>
<style>
	a {
		text-decoration: none;
		margin-left: 10px;
		margin-right: 10px;
	}
</style>
<div style="width: 100%; height: 120px; background-color: navajowhite; padding: 5px;">
	<h1 style="text-align: center;"><i>kosta bank</i></h1>
	<div>
		<a href="makeAccount">계좌개설</a>
		<a href="deposit">입금</a>
		<a href="withdraw">출금</a>
		<a href="accountInfo">계좌조회</a>
		<a href="allAccountInfo">전체계좌조회</a>
		<a href="transfer">계좌이체</a>
		<div style="float: right;">
			<c:choose>
				<c:when test="${sessionScope.user eq Empty}">
					<a href="login">로그인</a>
				</c:when>
				<c:otherwise>
					<c:out value="${user }" /> 님, 환영합니다.&nbsp;&nbsp;
					<a href="logout">로그아웃</a>
				</c:otherwise>
			</c:choose>
			<a href="join">회원가입</a>
		</div>
	</div>
</div>
	