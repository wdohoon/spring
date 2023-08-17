<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <style>
        html,
        body {
            margin: 0;
            padding: 0;
            height: 100%;
            font-family: Arial, sans-serif;
        }

        main {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100%;
            background-color: #F4F4F4;
        }

        h1 {
            margin-top: 0;
            color: #333;
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.2);
        }

        .board_list,
        .page>ul {
            overflow: auto;
        }

        .board_list>li {
            float: left;
            height: 40px;
            line-height: 40px;
            border-bottom: 1px solid #ddd;
            width: 10%;
            background-color: #FFF;
            box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.1);
        }

        .board_list>li:nth-child(6n+3) {
            width: 50%;
            /*말줄임표시*/
            white-space:nowrap;/*줄바꾸지 않기*/
            overflow:hidden;/*넘친값 숨기기*/
            text-overflow:ellipsis;/*말줄임 ...표시하기*/
        }

        .board_list>li:nth-child(n+8) {
            background-color: #F9F9F9;
        }

        .page li {
            float: left;
            width: 40px;
            height: 40px;
            text-align: center;
            background-color: #FFF;
            box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.1);
            transition: background-color 0.3s ease;
        }

        .page li:hover {
            background-color: #FFD700;
            color: #FFF;
        }

        .search {
            text-align: center;
        }

        /* 추가한 스타일 */
        html,
        body,
        main {
            height: 100%;
        }



        main {
            padding: 20px;
            box-sizing: border-box;
        }

        .board_list {
            width: 100%;
            list-style: none;
            padding: 0;
            margin: 0;
        }

        .board_list>li {
            width: 10%;
            font-weight: bold;
            color: #666;
        }

        .board_list>li:nth-child(6n+3) {
            width: 50%;
        }

        .board_list>li:nth-child(n+8) {
            background-color: #F9F9F9;
        }

        .page {
            margin-top: 20px;
        }

        .search {
            margin-top: 20px;
        }

        form {
            margin: 0;
        }

        a {
            color: #333;
            text-decoration: none;
            transition: color 0.3s ease;
        }

        a:hover {
            color: #FFD700;
        }
    </style>

<main>
    <h1>게시판 목록</h1>
    <div>
        <a href="/home/board/boardWrite">글쓰기</a>
    </div>
    <div>
        총 레코드 수 :${pDTO.totalRecord }개
    </div>
    <ul class="board_list">
        <li>&nbsp;</li>
        <li>no</li>
        <li>제목</li>
        <li>글쓴이</li>
        <li>등록일</li>
        <li>hit</li>
		<!-- 			변수			데이터(list) -->
		<c:forEach var="dto" items="${list }">
			<li><input type="checkbox" /></li>
	        <li>${dto.no }</li>
	        <li><a href='/home/board/boardView?no=${dto.no }&nowPage=${pDTO.nowPage}<c:if test="${pDTO.searchWord!=null }">&searchKey=${pDTO.searchKey }&searchWord=${pDTO.searchWord }</c:if>'>${dto.subject }</a></li>
	        <li>${dto.userid }</li>
	        <li>${dto.writedate }</li>
	        <li>${dto.hit }</li>
		</c:forEach>

        

    </ul>
    <div class="page">
        <ul>
        	<!-- 이전페이지 -->
        	<c:if test="${pDTO.nowPage==1 }">
            	<li>prev</li>
            </c:if>
            <c:if test="${pDTO.nowPage>1 }">
            	<li style="background:yellow"><a href='/home/board/boardList?nowPage=${pDTO.nowPage-1 }<c:if test="${pDTO.searchWord!=null }">&searchKey=${pDTO.searchKey }&searchWord=${pDTO.searchWord }</c:if>'>prev</a></li>
            </c:if>
            <!-- 페이지 번호 -->
            <!-- 			변수			시작값						끝값											증가값 -->
            <c:forEach var="p" begin="${pDTO.startPageNum}" end= "${pDTO.startPageNum + pDTO.onePageNumCount - 1}" step="1">
           	 	<c:if test="${p<=pDTO.totalPage }"><!-- totalPage -->
			         <c:if test="${p==pDTO.nowPage }">
			            <li style="background:yellow"><a href='/home/board/boardList?nowPage=${p}<c:if test="${pDTO.searchWord!=null }">&searchKey=${pDTO.searchKey }&searchWord=${pDTO.searchWord }</c:if>'>${p}</a></li>
			         </c:if>
			         <c:if test="${p!=pDTO.nowPage }">
			            <li><a href='/home/board/boardList?nowPage=${p}<c:if test="${pDTO.searchWord!=null }">&searchKey=${pDTO.searchKey }&searchWord=${pDTO.searchWord }</c:if>'>${p}</a></li>
			         </c:if>
		   		</c:if>
         	</c:forEach>
         	
         	<c:if test="${pDTO.nowPage>=pDTO.totalPage}" >
         		<li>next</li>
         	</c:if>
         	<c:if test="${pDTO.nowPage<pDTO.totalPage }">
         		<li><a href='/home/board/boardList?nowPage=${pDTO.nowPage+1 }<c:if test="${pDTO.searchWord!=null }">&searchKey=${pDTO.searchKey }&searchWord=${pDTO.searchWord }</c:if>'>next</a></li>
         	</c:if>

        </ul>
    </div>
    <div class="search">
        <form action="/home/board/boardList">
            <select name="searchKey">
                <option value="subject">제목</option>
                <option value="content">글내용</option>
                <option value="userid">글쓴이</option>
            </select>
            <input type="text" name="searchWord" id="searchWord" />
            <input type="submit" value="Search" />
        </form>
    </div>
</main>