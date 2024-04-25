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
	width: 370px;
	padding: 10px;
}
</style>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
	$(function() {
		$('#doubleId').click(function(e) {
			e.preventDefault();
			$.ajax({
				url : 'memberDoubleId', // 보내는 url
				type : 'post', // doPost 방식
				async : true,
				dataType : 'text',
				data : {
					id : $('#id').val()
				}, // 사용자가 입력한 값(id)를 받아옴
				success : function(result) {
					if (result == "true") {
						alert("아이디가 중복됩니다.");
					} else if(result=="false") {
						alert("사용 가능한 아이디입니다.");
					} else {
						alert("아이디 중복 체크 오류");
					}
				}
			})
		})
		
		$('#doubleNickName').click(function(e) {
			e.preventDefault();
			$.ajax({
				url : 'memberDoubleNickName', // 보내는 url
				type : 'post', // doPost 방식
				async : true,
				dataType : 'text',
				data : {
					nickname : $('#nickname').val()
				}, // 사용자가 입력한 값(id)를 받아옴
				success : function(result) {
					if (result == "true") {
						alert("닉네임이 중복됩니다.");
					} else if(result=="false") {
						alert("사용 가능한 닉네임입니다.");
					} else {
						alert("닉네임 중복 체크 오류");
					}
				}
			})
		})
	})
</script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
    function daumPostCode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById("address").value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("detailAddress").focus();
            }
        }).open();
    }
</script>
</head>
<body>
<jsp:include page="main.jsp"/>
	<div class="header">
		<h3>회원가입</h3>
	</div>
	<form action="join" method="post" class="container">
		<table border="1">
			<tr>
				<th>아이디</th>
				<td><input type="text" id="id" name="id">&nbsp;
					<button id="doubleId">중복</button></td>
			</tr>
			<tr>
				<th>이름</th>
				<td><input type="text" name="name"></td>
			</tr>
			<tr>
				<th>비밀번호</th>
				<td><input type="password" name="password"></td>
			</tr>
			<tr>
				<th>닉네임</th>
				<td><input type="text" name="nickname" id="nickname">
					<button id="doubleNickName">중복</button></td>
			</tr>
			<tr>
				<th>이메일</th>
				<td><input type="text" name="email"></td>
			</tr>
			<tr>
				<th>주소</th>
				<td><input type="text" name="address" id="address" placeholder="주소">&nbsp;
					<input type="button" onclick="daumPostCode()" value="우편번호 찾기"><br>
					<input type="text" name="detailAddress" id="detailAddress" placeholder="상세주소">
				</td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="회원가입"></td>
			</tr>
		</table>
	</form>
</body>
</html>