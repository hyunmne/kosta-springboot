<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 글 등록</title>
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
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script>
var reader = new FileReader();
reader.onload=function(e) {
	console.log(e.target.result)
	const image = new Image();
	image.src = e.target.result;		
	image.onload = imageEvent => {
		imageSizeChange(image);
		console.log(image)
		$('#preview').attr("src",image.imgUrl);
	};		
}
function imageSizeChange(image) {
    let canvas = document.createElement("canvas");
    max_size = 100, // 최대 기준을 1280으로 잡음.
    width = image.width,
    height = image.height;
    if (width > height) {
      // 가로가 길 경우
      if (width > max_size) {
        height *= max_size / width;
        width = max_size;
      }
    } else {
      // 세로가 길 경우
      if (height > max_size) {
        width *= max_size / height;
        height = max_size;
      }
    }
    canvas.width = width;
    canvas.height = height;
    canvas.getContext("2d").drawImage(image, 0, 0, width, height);
    image.imgUrl = canvas.toDataURL("image/jpeg", 0.5);
  }
	
	$(function() {
		$("#file").change(function(e) {
			var file = e.target.files[0];
			if (file) {
				reader.readAsDataURL(file);
			}
		})
	})
</script>
</head>
<body>
<jsp:include page="main.jsp"/>
	<section id="./writeForm">
		<h2>게시판 글 등록</h2>
		<form action="boardWrite" method="post" enctype="multipart/form-data" name="boardform">
			<table>
				<tr>
					<td class="td_left"><label for="writer">글쓴이</label></td>
					<td class="td_right"><input type="text" name="writer" id="writer"/></td>
				</tr>
				<tr>
					<td class="td_left"><label for="subject">제목</label></td>
					<td class="td_right"><input name="subject" type="text"
						id="subject" required="required" /></td>
				</tr>
				<tr>
					<td class="td_left"><label for="content">내용</label></td>
					<td><textarea id="content" name="content"
							cols="40" rows="15" required="required"></textarea></td>
				</tr>
				<tr>
					<td class="td_left"><label for="file">이미지 파일 첨부</label></td>
					<td class="td_right">
					<img src="" id="preview" alt="" /> <br>
					<input name="file" type="file" id="file" accept="image/*"/></td>
				</tr>
			</table>
			<section id="commandCell">
				<input type="submit" value="등록">&nbsp;&nbsp; 
				<input type="reset" value="다시쓰기" />
			</section>
		</form>
	</section>
</body>
</html>