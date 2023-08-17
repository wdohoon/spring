<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="<%=request.getContextPath()%>/webjars/bootstrap/5.3.0/dist/css/bootstrap.min.css">
    <script src="<%=request.getContextPath()%>/webjars/bootstrap/5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/bxslider/4.2.12/jquery.bxslider.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/bxslider/4.2.12/jquery.bxslider.min.js"></script>
    <title>Work01</title>
    
    <style>
       
        body {
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 1000px;
            margin: 0 auto;
            text-align: center;
        }

        .header {
            background: #f2f2f2;
            padding: 10px;
        }

        .logo {
            text-decoration: none;
            color: black;
            font-size: 24px;
            font-weight: bold;
        }

        .banner {
        	background:#3F48CC;
        	text-align:left;
            
        }

        .content {
            padding: 20px;
        }

        .footer {
            background: #f2f2f2;
            padding: 10px;
        }

        .menu {
            background: #f2f2f2;
            padding: 10px;
        }
        .time{
           margin: 0 auto;
           text-align: center;
           max-width: 1000px;
           background: #000000;
           color: #ffffff; 
        }
        
        .nav-link{
        	padding: 10px;
        	text-align: left;
	        color: #F1F8FC;
	        text-decoration: none;
	        font-size: 24px;
            font-weight: bold; 
        }
        .slider{
        	margin-bottom: 500px;
        }
    </style>
</head>
<body>
      <div class="time">
                <span id="current-time"></span>
                <script>
                    function updateTime() {
                        var now = new Date();
                        var hours = now.getHours();
                        var minutes = now.getMinutes();
                        document.getElementById("current-time").innerHTML = hours + ":" + minutes;
                    }
                    setInterval(updateTime, 1000);
                    $(document).ready(function(){
                        $(".slider").bxSlider();
                      });
                </script>
            </div>
       <div class="container">
        <div class="header">
            
            <div><a href="https://smhrd.or.kr/" class="logo">스마트 인재개발원</a></div>
        </div>
        <div class="banner">
            <nav class="nav">
			  <a class="nav-link active" aria-current="page" href="https://smhrd.or.kr/">홈</a>
			  <a class="nav-link" href="https://smhrd.or.kr/">로그인</a>
			  <a class="nav-link" href="https://smhrd.or.kr/">커뮤니티</a>
			  <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true"></a>
			</nav>
        </div>
        <div class="slider">
      <div>슬라이더를 넘겨보세요.</div>
      <div>잘했어요 칭찬합니다.</div>
    </div>

    <img src="img/img02.jpg"/>
 
        <div class="content">
           
        </div>
        <div class="footer">
            <h1>르세라핌 리더 김채원</h1>
        </div>
        <div class="menu">
            <!-- Add your menu content here -->
        </div>
    </div>
</body>
</html>