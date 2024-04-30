<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>transfer</title>
    <style>
        .header {
            text-align: center;
        }
        .container {
            width: 310px;
            border: 1px solid black;
            padding: 10px;
            margin: 0 auto;
        }
        .row {
            height: 30px;
        }
        .title {
            float: left;
            width: 120px;
            text-align: center;
            font-weight: bold;
        }
        .input {
            float: left;
        }
        input[type='submit'] {
            font-weight: bold;
            width: 120px;
            background-color: lightgray;
            display: block;
            margin: 0 auto;
        }
    </style>
</head>
<body>
	<%@include file="header.jsp"%>
    <form action="transfer" method="post">
        <div class="header"><h3>계좌 송금</h3></div>
        <div class="container">
            <div class="row">
                <div class="title">보내는 계좌</div>
                <div class="input"><input type="text" name="sid"></div>
            </div>
            <div class="row">
                <div class="title">받는 계좌</div>
                <div class="input"><input type="text" name="rid"></div>
            </div>
            <div class="row">
                <div class="title">송금액</div>
                <div class="input"><input type="text" name="money"></div>
            </div>

            <div class="button">
                <input type="submit" value="송 금">
            </div>
        </div>
    </form>
</body>
</html>