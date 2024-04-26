<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="path" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 상세</title>
<style type="text/css">
	h3 {text-align: center;}
	table {margin: auto; width: 450px;}
	.td_left {width: 150px; background: orange;}
	.td_right {width: 300px; background: skyblue;}
	#commandCell {text-align: center;}
	#content {height: 200px;}
</style>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
	$(function() {
		$('#like').click(function(){
			$.ajax({
				url:'/boardLike',
				type:'post',
				async:true,
				data:{like:JSON.stringify({memberId:"${user}", boardNum:"${brd.num}"})},
				success:function(result){
					if(result=='true') {
						$('#like').attr("src", "${path}/resources/image/redheart.png")
					} else if(result=='false'){
						$('#like').attr("src", "${path}/resources/image/blackheart.png")
					} else {
						alert("좋아요 오류")
					}
				}
			})
		})
	})
</script>
</head>
<body>
<jsp:include page="main.jsp"/>
	<h3>게시물 상세보기</h3>
	
	<table border="1">
		<tr>
			<td class="td_left"><label>작성자</label></td>
			<td class="td_right"><span>${brd.writer }</span></td>
		</tr>
		<tr>
			<td class="td_left"><label>제목</label></td>
			<td class="td_right"><span>${brd.subject }</span></td>
		</tr>
		<tr>
			<td class="td_left"><label>내용</label></td>
			<td class="td_right"><div id="content">${brd.content }</div></td>
		</tr>
		<c:if test="${brd.fileNum ne null  }">
			<tr>
				<td class="td_left"><label>이미지</label></td>
				<td class="td_right"><img src="${path }/image/${brd.fileNum }" width="100px" /></td>
			</tr>
		</c:if>
	</table>
	
	<div id="commandCell">
		<c:if test="${user eq brd.writer }">
			<a href="${path }/boardModify/${brd.num }">수정</a>&nbsp;&nbsp;&nbsp; 
		</c:if>
		<a href="${path }/boardList">목록</a>&nbsp;&nbsp;&nbsp;
		<c:if test="${user ne Empty }">
			<c:choose>
				<c:when test="${like eq 'true' }">
					<img src="${path}/resources/image/redheart.png" width="40px" height="40px" style="margin-top:5px;" id="like" />
				</c:when>
				<c:otherwise>
					<img src="${path}/resources/image/blackheart.png" width="40px" height="40px" style="margin-top:5px;" id="like"/>
				</c:otherwise>
			</c:choose>
		</c:if>
	</div>
	
</body>

</html>