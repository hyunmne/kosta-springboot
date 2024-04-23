<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Document</title>
<style>
    .header {
        text-align: center;
    } 
    .container {
        width: 320px;
        border: 1px solid black;
        padding: 10px;
        margin: 0 auto;
    }
    .row {
        height: 30px;
    }
    .title {
        float: left;
        width: 70px;
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
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
   window.onload = function() {
       let select = document.querySelector("#grade");
       let types = document.querySelectorAll("input[name='type']");
       for(type of types) {
           type.onclick = function() {
               if(this.checked && this.value == 'special') {
                   select.disabled = false;
               } else {
                   select.disabled = true;
               }
           }
       }

/*            let form = document.forms[0];  //form에 name이 없으니까 form들 중 첫번째 form 가져온다는 것
            form.onsubmit = function() {
                let id = form.elements.id.value;
                let name = form.elements.name.value;
                let balance = form.elements.balance.value;
                let type = document.querySelector("input[name='type']:checked").value;  //type 중 체크된 것의 값
                let grade = '';
                if(type == 'special') {
                    // grade = document.querySelector("#grade>option:checked").value;  //type 중 체크된 것의 값
                    grade = document.querySelector("#grade").value;  //select태그의 value는 option으로 선택된 걸 가져온다(그래서 이렇게만 써도 됨)
                }
                console.log(`계좌번호:${id}, 이름:${name}, 잔액:${balance}, 종류:${type}, 등급:${grade}`);
                return false;
            } */
   }
      
         
   $(function() {
      $('#doubleId').click(function(e){
         e.preventDefault();
         $.ajax({
            url:'accountDoubleId',
            type:'post',
            async:true,
            dataType:'text',
            data:{id:$('#id').val()},
            success:function(result) {
               if(result == "true") {
                  alert("계좌번호가 중복됩니다.");
               } else if (result=="false"){
                  alert("사용 가능한 계좌번호 입니다.");
               } else {
            	   alert("계좌중복 체크 오류")
               }
            },
            error:function(result) {
               
            }
         })
      })
   })
</script>
</head>
<body>
<%@ include file="header.jsp" %>
    <form action="makeAccount" method="post">
        <div class="header"><h3>계좌개설</h3></div>
        <div class="container">
            <div class="row">
                <div class="title">계좌번호</div>
                <div class="input"><input type="text" name="id" id="id"></div>&nbsp;
                <button id="doubleId">중복</button>
            </div>
            <div class="row">
                <div class="title">이름</div>
                <div class="input"><input type="text" name="name" id="name"></div>
            </div>
            <div class="row">
                <div class="title">입금액</div>
                <div class="input"><input type="text" name="balance" id="balance"></div>
            </div>
            <div class="row">
                <div class="title">종류</div>
                <div class="input">
                    <input type="radio" name="type" checked value="normal">일반
                    <input type="radio" name="type" value="special">특수
                </div>
            </div>
            <div class="row">
                <div class="title">등급</div>
                <div class="input">
                    <select name="grade" id="grade" disabled>
                        <option>선택</option>
                        <option value="VIP">VIP</option>
                        <option value="Gold">Gold</option>
                        <option value="Silver">Silver</option>
                        <option value="Normal">Normal</option>
                    </select>
                </div>
            </div>
            <div class="button">
                <input type="submit" value="개설">
            </div>
        </div>
    </form>
</body>
</html>