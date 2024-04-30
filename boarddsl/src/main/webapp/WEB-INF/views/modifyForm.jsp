<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="path" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 글 수정</title>
<style type="text/css">
h2 {
	text-align: center;
}

table {
	margin: auto;
	width: 450px;
}

.td_left {
	width: 150px;
	background: orange;
}

.td_right {
	width: 300px;
	background: skyblue;
}

#commandCell {
	text-align: center;
}
</style>
<script>
 window.onload = function() {
	 const fileDom = document.querySelector('#file');
	 const imageBox = document.querySelector('#image-box');
	 
	 fileDom.addEventListener('change', function(){
		 const imageSrc = URL.createObjectURL(fileDom.files[0]);
		 imageBox.src = imageSrc;
	 })
 }
</script>
</head>
<body>
<jsp:include page="main.jsp"/>
	<section id="./writeForm">
		<h2>게시판 글 수정</h2>
		<form action="/boardModify" method="post" enctype="multipart/form-data">
		<input type="hidden" name="writer" value="${user }" />
		<input type="hidden" name="num" value="${board.num }" />
		<input type="hidden" name="viewCount" value="${board.viewCount }" />
		<input type="hidden" name="likeCount" value="${board.likeCount }" />
		<input type="hidden" name="writeDate" value="${board.writeDate }" />
			<table>
				<tr>
					<td class="td_left"><label for="subject">제목</label></td>
					<td class="td_right">
						<input name="subject" type="text"id="subject" required="required" value="${board.subject }" />
					</td>
				</tr>
				<tr>
					<td class="td_left"><label for="content">내용</label></td>
					<td><textarea id="content" name="content" cols="40" rows="15"
							required="required">${board.content }</textarea></td>
				</tr>
				<c:if test="${board.fileNum ne null  }">
					<tr>
						<td class="td_left"><label>이미지</label></td>
						<td class="td_right"><img src="${path }/image/${board.fileNum }" id="image-box"
							width="100px" /></td>
					</tr>
				</c:if>
				<tr>
					<td class="td_left"><label for="file">이미지 파일 첨부</label></td>
					<td class="td_right"><input name="file" type="file" id="file"
						accept="image/*" /></td>
				</tr>
			</table>
			<section id="commandCell">
				<input type="submit" value="수정">&nbsp;&nbsp; 
				<input type="reset" value="다시 쓰기" />
			</section>
		</form>
	</section>
</body>
</html>