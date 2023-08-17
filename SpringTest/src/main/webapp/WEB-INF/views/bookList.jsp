<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>보유 도서 현황</title>
</head>
<body>
	<h1>보유 도서 현황</h1>
	
	<ol>
		<li>번호|        제목        |  작가  | 출판사 |   ISBN  |보유도서수</li>
		<li> 1 |해리포터와 아즈카반의 죄수|J.K롤링 |문학수첩|8983920726|12</li>
		<li> 2 |난중일기             |이순신   |서해문집|8974832232|8</li>
		<li>${param4 }</li>
		<li>${param5 }</li>
		<li>${param6 }</li>
	</ol>
	
	<h1>도서 입력</h1>
    <form method="GET" action="/bshop/bookList.do/">
        <ul>
            <li>
                <label for="title">제목 :</label>
                <input type="text" name="title" id="title" />
            </li>
            <li>
                <label for="author">작가 :</label>
                <input type="text" name="author" id="author" />
            </li>
            <li>
                <label for="company">출판사 :</label>
                <input type="text" name="company" id="company" />
            </li>
            <li>
                <label for="isbn">ISBN :</label>
                <input type="text" name="isbn" id="isbn" />
            </li>
            <li>
                <label for="count">보유도서수 :</label>
                <input type="text" name="count" id="count" />
            </li>
            <li>
                <button>등록</button>
            </li>
        </ul>
    </form>
</body>
</html>