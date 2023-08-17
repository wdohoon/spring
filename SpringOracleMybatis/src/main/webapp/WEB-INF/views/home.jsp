<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<script>

</script>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            margin: 20px;
        }

        div.container {
            max-width: 600px;
            margin: 0 auto;
            background-color: #fff;
            padding: 20px;
            border: 1px solid #ccc;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        h2 {
            color: #333;
            font-size: 20px;
            margin-bottom: 10px;
        }

        ol {
            margin-left: 20px;
        }

        li {
            margin-bottom: 5px;
        }

        form {
            margin-top: 20px;
        }

        input[type="text"] {
            padding: 5px;
            width: 200px;
        }

        input[type="submit"] {
            padding: 5px 10px;
            background-color: #337ab7;
            color: #fff;
            border: none;
            cursor: pointer;
        }

        img {
            max-width: 100%;
            margin-bottom: 10px;
        }

        a {
            color: #337ab7;
            text-decoration: none;
            margin-right: 10px;
        }
    </style>
</head>
<body>
<div class="container">
    <div>
        <a href="/home/test?num=100&name=원도훈">접속하기1</a>
        <a href="/home/test2?no=200&id=ggm">접속하기2</a>
        <a href="/home/test3?num=300&username=원도훈&tel=010-9987-9987">접속하기3</a>
        <%= request.getContextPath() %>
        <a href="<%= request.getContextPath() %>/test5/5">접속하기4</a>
    </div>
    <div>
        <h2>받은 데이터</h2>
        <ol>
            <li>이름: ${name}</li>
            <li>연락처: ${tel}</li>
            <li>${num}, ${id}</li>
            <li>DTO: ${dto.num}, ${dto.username}, ${dto.tel}</li>
        </ol>
    </div>
    <div>
        <h2>post방식의 전송</h2>
        <form method="post" action="<%= request.getContextPath() %>/test4">
            이름: <input type="text" name="username"/><br/>
            주소: <input type="text" name="addr"/><br/>
            <input type="submit" value="서버로 보내기"/>
        </form>
    </div>
    <img src="<%= request.getContextPath() %>/img/img02.jpg"/>
</div>
</body>
</html>
