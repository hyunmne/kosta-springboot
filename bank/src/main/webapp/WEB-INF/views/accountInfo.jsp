<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.kosta.bank.dto.Account"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Account Info</title>
    <style>
        .header {
            text-align: center;
        }
        .container {
            margin: 0 auto;
            border: 1px solid;
            width: 280px;
            padding: 10px;
        }
        .row {
            height: 30px;
        }
        .title {
            float: left;
            width: 80px;
            font-weight: bold;
            text-align: center;
        }
        .input {
            float: left;
            
        }
    </style>
</head>
<body>
	<%@ include file="header.jsp" %>
    <div class="header"><h3>계좌조회</h3></div>
    <c:if test="${acc ne Empty }">
    <div class="container">
        <div class="row">
            <div class="title">계좌번호</div>
            <div class="input"><input type="text" disabled value='${acc.id }'></div>
        </div>
        <div class="row">
            <div class="title">이름</div>
            <div class="input"><input type="text" disabled value='${acc.name }'></div>
        </div>
        <div class="row">
            <div class="title">입금액</div>
            <div class="input"><input type="text" disabled value='${acc.balance }'></div>
        </div>
        <div class="row">
            <div class="title">종류</div>
            <div class="input"><input type="text" disabled value='${acc.type }'></div>
        </div> 
        <div class="row">
            <div class="title">등급</div>
            <div class="input"><input type="text" disabled value='${acc.grade }'></div>
        </div>
    </div>
    </c:if>
</body>
</html>