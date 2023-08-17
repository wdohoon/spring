<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script>
	function delChk(){
		// 확인(true),취소(false)
		if(confirm("글을 삭제하시겠습니까?")){
			location.href = "/home/board/boardDel?no=${dto.no}";
		}
	}
	$(function(){
		// 댓글 목록 가져오기
		function replyAllList(){
			$.ajax({
				url:'/home/reply/replyList',
				data:{
					no:${dto.no}// 원글글번호
				},
				success:function(replyResult){
					$("#replyList").html("");// 원래 있는 목록
					console.log(replyResult);
					$(replyResult).each(function(i, coment){
						var tag = "<li><div>";
						tag += "<b>"+coment.userid+"("+coment.writedate+")</b>";
						// 수정,삭제
						// 로그인 한 사람이 쓴 댓글일때 
						if(coment.userid=='${logId}'){// '아이디' == '아이디'
							tag += "<input type='button' value='Edit' />";
							tag += "<input type='button' value='Del' title='"+coment.re_no+"'/>";
							tag += "<p>"+coment.coment+"</p></div>"; // 댓글 내용
							// 수정 폼
							tag += "<div style='display:none'>";
							tag += "<form>";
							tag += "<textarea style='width:400px' name='coment'>";
							// 글내용수정, 댓글번호
							tag += coment.coment;
							tag += "</textarea>";
							tag += "<input type='hidden' name='re_no' value='"+coment.re_no+"'/>";
							tag += "<input type='button' value='수정하기'/>";
							tag += "</form>";
							tag += "</div>";
						}else{
							tag += "<p>" +coment.coment+"</p></div>";
						}
						
						
						tag += "</li>";
						
						$("#replyList").append(tag);
					});
				},
				error:function(e){
					console.log(e.responseText);
				}
			});
		}
		// 댓글쓰기
		$("#replyFrm").submit(function(){
			// form태그의 기본 이동의 기능을 가진 action으로 이동하는 것을 해제
			event.preventDefault();
			// 0. 댓글입력 확인
			if($("#coment").val()==""){
				alert("댓글을 입력하세요");
				return false;
			}
			// 1. 데이터 준비 no=99&coment=댓글내용 -> 폼내의 값을 쿼리로 만들어주는 함수(serialize())
			var params = $("#replyFrm").serialize();
			console.log('params', params);
			
			// 2. ajax실행
			$.ajax({
				url:'/home/reply/replyWrite',
				type:'POST',
				data:params,
				success:function(result){
					console.log(result);
					// 이미 DB에 등록된 글 폼에서 지우기
					$("#coment").val("");
					// 댓글리스트 다시출력
					replyAllList();
				},
				error:function(e){
					console.log(e.responseText);
				}
			});
		});
		// 댓글수정폼
		$("#replyList input[value=Edit]").click();
		$(document).on('click','#replyList input[value=Edit]',function(){
			// 해당 댓글은 숨기고
			$(this).parent().css('display','none');
			// 수정폼은 보여주기
			$(this).parent().next().css('display','block')
		});
		// 댓글수정(DB)
		$(document).on('click','#replyList input[value=수정하기]',function(){
			var params = $(this).parent().serialize(); // re_no=88&coment=fjfj
			
			$.ajax({
				url:'/home/reply/replyEditOk',
				data:params,
				type:'POST',
				success:function(result){
					if(result=='0'){
						alert('댓글이 수정되지 않았습니다.');
					}else{
						replyAllList();
					}
				},error:function(){
					console.log("댓글수정실패");
				}
			});
		});
		// 댓글 삭제
		$(document).on('click','#replyList input[value=Del]',function(){
			if(!confirm('댓글을 삭제하시겠습니까?')){
				return false;
			}
			// 댓글번호	attr(),	prop()
			//			attr('title'), attr('title','200')
			var re_no = $(this).attr('title')
			$.ajax({
				url:"/home/reply/replyDel",
				data:{
					re_no:re_no
				},
				success:function(result){
					if(result=='0'){
						alert("댓글 삭제 안됨");
					}else{
						replyAllList();
					}	
				},error:function(e){
					console.log("댓글삭제 에러 발생.");
				}
			});
		});
		// 해당글의 댓글 목록
		replyAllList();
	});
</script>
<main>
	<h1>글내용보기</h1>
	<div>
		<a href='/home/board/boardList?nowPage=${pDTO.nowPage}<c:if test="${pDTO.searchWord!=null }">&searchKey=${pDTO.searchKey }&searchWord=${pDTO.searchWord }</c:if>'>목록</a>
	</div>
	<ul>
		<li>글번호 : ${dto.no}</li>
		<li>글쓴이 : ${dto.userid}</li>
		<li>조회수 : ${dto.hit}</li>
		<li>날짜 : ${dto.writedate}</li>
		<li>제목 : ${dto.subject}</li>
		<li>글내용<br />
			${dto.content}</li>
	</ul>
	<div>
		<!-- session의 로그인아이디(logId)와 현재글의 글쓴이(userid)가 같으면 수정,삭제 표시한다.  -->
		<c:if test="${logId == dto.userid }">
			<a href="/home/board/boardEdit?no=${dto.no }">수정</a>
			<a href="javascript:delChk()">삭제</a>
		</c:if>
	</div>
	<!-- 댓글달기 -->
	<style>
		#replyList>li{
			border-bottom:1px solid #ddd;
			padding: 5px 0px
		}
	</style>
	<div id="reply">
		<!-- 로그인시 댓글 폼 -->
		<c:if test="${logStatus=='Y' }">
			<form method="post" id="replyFrm">
				<input type="hidden" name="no" value="${dto.no }" /><!-- 원글번호 -->
				<textarea name="coment" id="coment" cols="100" rows="10"></textarea>
				<input type="submit" value="댓글등록하기" />
			</form>
		</c:if>
		<hr />
		<ul id="replyList">
		</ul>
	</div>
</main>