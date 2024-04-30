<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
        .header {
            text-align: center;
        }
        .container {
            margin: 0 auto;
            border: 1px solid;
            width: 330px;
            padding: 10px;
        }
</style>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
	$(function(){
		$('#doubleId').click(function(e){
			e.preventDefault();
			$.ajax({
				url:'memberDoubleId', // 보내는 url
				type:'post', // doPost 방식
				async:true,
				dataType:'text',
				data:{id:$('#id').val()}, // 사용자가 입력한 값(id)를 받아옴
				success:function(result){
					if(result=='true'){
						alert("아이디가 중복됩니다.");
					} else {
						alert("사용 가능한 아이디입니다.")
					}
				},
				error:function(result){
					
				}
			})
		})
	})
</script>
</head>
<body>
<% pageContext.include("header.jsp"); %>
<div class="header"><h3>회원가입</h3></div>
<form action="join" method="post" class="container">
	<table border="1">
		<tr><th>아이디</th><td> <input type="text" id="id" name="id">&nbsp;<button id="doubleId">중복</button> </td></tr>
		<tr><th>이름</th><td> <input type="text" name="name"> </td></tr>
		<tr><th>비밀번호</th><td> <input type="password" name="password"> </td></tr>
		<tr><th>이메일</th><td> <input type="text" name="email"> </td></tr>
		<tr><th>주소</th><td> <input type="text" name="address"> </td></tr>
		<tr><td colspan="2"><input type="submit" value="회원가입"></td></tr>
	</table>
</form>
</body>
</html>