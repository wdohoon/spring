<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
    body {
        background-color: #f2f2f2;
        font-family: Arial, sans-serif;
        margin: 0;
        padding: 0;
    }

    main {
        max-width: 400px;
        margin: 100px auto;
        background-color: #fff;
        border-radius: 5px;
        box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        padding: 20px;
    }

    h1 {
        text-align: center;
        color: #333;
        margin-bottom: 30px;
    }

    form {
        margin-bottom: 20px;
    }

    ul {
        list-style: none;
        padding: 0;
        margin: 0;
    }

    li {
        margin-bottom: 10px;
    }

    input[type="text"],
    input[type="password"] {
        width: 100%;
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 3px;
    }

    input[type="submit"] {
        width: 100%;
        padding: 10px;
        border: none;
        border-radius: 3px;
        background-color: #0C82E9;
        color: #fff;
        cursor: pointer;
    }

    input[type="submit"]:hover {
        background-color: #45a049;
    }

    div.links {
        text-align: center;
    }

    div.links a {
        color: #666;
        margin-right: 30px;
        text-decoration: none;
    }

    div.links a:hover {
        color: #000;
    }
</style>
<script>
	function logChk(){
		// 아이디 존재유무
		if(document.getElementById("userid").value==""){
			alert("아이디를 입력하세요.");
			return false;
		}
		if(document.getElementById("userpwd").value==""){
			alert("비밀번호를 입력하세요.");
			return false;
		}
		return true;
	}
</script>
<main>
    <h1>서울시 통합회원 로그인</h1>
    <form method="post" action="/home/register/loginOk" onsubmit="return logChk()">
        <ul>
            <li><input type="text" name="username" id="username" placeholder="아이디 입력" /></li>
            <li><input type="password" name="userpwd" id="userpwd" placeholder="비밀번호 입력" /></li>
            <li><input type="submit" value="로그인" /></li>
        </ul>
    </form>
    <div class="links">
    	<!-- 이름과 연락처를 입력받아 DB에서 아이디, 이메이리을 조회 후 아이디를 이메일로 보낸다. -->
        <a href="/home/register/idSearch">아이디 찾기</a>
        <a href="#">비밀번호 찾기</a>
        <a href="/home/register/regForm">회원가입</a>
    </div>
</main>
