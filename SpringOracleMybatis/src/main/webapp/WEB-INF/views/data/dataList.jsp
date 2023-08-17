<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
	.data_list{
		overflow:auto;
	}
	.data_list>li{
		float: left;
		width: 10%;
		height: 40px;
		line-height: 40px;
		border-bottom: 1px solid #ddd;
	}
	.data_list>li:nth-child(5n+2){
		width: 60%
	}
</style>
<main>
	<h1>자료실 글 목록</h1>
	<div>
		<a href="${pageContext.request.contextPath }/data/dataWrite">자료실 글쓰기</a>
	</div>
	<ul class="data_list">
		<li>번호</li>
		<li>제목</li>
		<li>글쓴이</li>
		<li>조회수</li>
		<li>등록일</li>
		<c:forEach var="dto" items="${list }">
			<li>${dto.no }</li>
			<li><a href="/home/data/dataView/${dto.no }">${dto.subject }</a></li>
			<li>${dto.userid }</li>
			<li>${dto.hit }</li>
			<li>${dto.writedate }</li>
		</c:forEach>
	</ul>
</main>