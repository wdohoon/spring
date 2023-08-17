<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script>
	$(function(){
		$("input[value=아이디찾기]").click(function(){
			$.ajax({
				url:'/home/register/idSearchOk',
				data:{
					username:$("#username").val(),
					tel:$("#tel").val()
				},
				type:"post",
				success:function(result){
					if(result=="Y"){
						alert("아이디를 이메일로 전송하였습니다.");
						location.href = "/home/register/login";
					}else{
						alert("일치하는 정보가 존재하지 않습니다.");
					}
				},error:function(){
					console.log("아이디찾기 에러발생");
				}
			});
		});
	});
</script>
<main>
	<h1>아이디 찾기</h1>
	<form id="idSearchFrm">
	<ul>
		<li>이름 : <input type="text" name="username" id="username"/></li>
		<li>번호 : <input type="text" name="tel" id="tel" placeholder="예)010-1234-1234"/></li>
		<li><input type="button" value="아이디찾기" /></li>
	</ul>
	</form>
</main>