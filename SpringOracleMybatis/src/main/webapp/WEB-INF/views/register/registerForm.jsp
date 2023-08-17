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
        max-width: 1000px;
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

    form ul {
        list-style: none;
        padding: 0;
        margin: 0;
    }

    form ul li {
        margin-bottom: 15px;
    }

    form label {
        display: block;
        font-weight: bold;
        margin-bottom: 5px;
    }

    form input[type="text"],
    form input[type="password"],
    form input[type="email"] {
        width: 100%;
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 3px;
    }

    form button {
        width: 100%;
        padding: 10px;
        border: none;
        border-radius: 3px;
        background-color: #4CAF50;
        color: #fff;
        cursor: pointer;
    }

    form button:hover {
        background-color: #45a049;
    }
    
</style>

<main>
    <h1>회원가입 폼</h1>
    <form method="post" action="/home/register/registerOk/">
        <ul>
            <li>
                <label for="userid">아이디 :</label>
                <input type="text" name="userid" id="userid" />
            </li>
            <li>
                <label for="userpwd">비밀번호 :</label>
                <input type="password" name="userpwd" id="userpwd" />
            </li>
            <li>
                <label for="userpwd2">비밀번호 확인 :</label>
                <input type="password" name="userpwd2" id="userpwd2" />
            </li>
            <li>
                <label for="username">이름 :</label>
                <input type="text" name="username" id="username" />
            </li>
            <li>
			    <label for="tel" >연락처 :</label>
			    <input type="text" name="tel1" id="tel1" size="4"/> -
			    <input type="text" name="tel2" id="tel2" size="4"/> -
			    <input type="text" name="tel3" id="tel3" size="4"/>
			</li>
            <li>
                <label for="email">이메일 :</label>
                <input type="email" name="email" id="email" />
            </li>
            <li>
                <button>회원가입하기</button>
            </li>
        </ul>
    </form>
</main>
