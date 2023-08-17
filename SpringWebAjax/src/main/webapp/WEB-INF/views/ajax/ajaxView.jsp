<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>스프링에서 ajax구현하기</title>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.7.0/dist/jquery.min.js"></script>
<style>
	#resultView{
	height:400px;
	background-color: pink; 
	}
</style>
<script>
	$(function(){
		// 서버에 비동기식으로 접속하여 문자열 받아오기
		$("button").eq(0).click(function(){
			// 서버로 보낼데이터 <a href="/a/?name=홍&age=20">
			var params = "name=홍길동&age=25";
			
			$.ajax({
				data:params,
				type:'get',
				url:'/myapp/ajaxString',
				success:function(result){// 서버에 응답받은 정보가 result담긴다.
					// html(), text(), append(), appendTo(), insert(), prepend(), prependTo() ...
					$("#resultView").append("<li>"+result+"</li>");
				},
				error:function(error){
					console.log(error.responseText);
				}
			});
		});
		// 서버에서 DTO, VO를 받아오기
		$("button").eq(1).on('click',function(){
			var params = "username=이순신&addr=서초구";
			var url = "${pageContext.request.contextPath}/ajaxObject";
			$.ajax({
				type:"get",
				data:params,
				url:url,
				success:function(result){
					console.log(result);
					var tag = "<li>";
					tag += "번호:" +result.num;
					tag += ", 이름:"+result.username;
					tag += ", 연락처:"+result.tel;
					tag += ", 주소:"+result.addr;
					tag+="</li>"
					$("#resultView").append(tag);
				},
				error:function(e){
					console.log(e.responseText);
				}
			});
		});
		$("button").eq(2).click(function(){
			// json 보내기
			var data = {
				productName : "컴퓨터",
				price:100
			}
			$.ajax({
				data:data,
				dataType:'json',
				url:'/myapp/ajaxList',
				success:function(result){
					console.log(result);
					// 서버에서 List<DTO>리턴하면
					// 배열에서 DTO를 json데이터로 받는다.
					let memberTag = "<li><ol>"
					//						index, dto
					$(result).each(function(idx, data){
						memberTag += "<li>num:"+data.num+",username:"+data.username;
						memberTag += ", tel:"+data.tel+", addr:"+data.addr+"</li>";
					});
					memberTag += "</ol></li>";
					$("#resultView").append(memberTag);
				},
				error:function(e){
					console.log(e.responseText);
				}
			});
		});
		$("button").eq(3).click(function(){
			// 서버에서 Map데이터 가져오기
			$.ajax({
				url:"/myapp/ajaxMap",
				success:function(result){
					console.log(result);
					var tag = "<li>";
					
					tag += "<div>총레코드수:"+result.total+", 현재페이지:"+result.page+"</div>";
					tag += "<div>"
					$(result.list).each(function(i, d){
						tag += "("+i+")"+d.no + ", "+ d.subject + "<br/>";
					});
					tag += "</div>";
					tag += "</li>";
					
					$("#resultView").append(tag);
				},
				error:function(e){
					console.log(e.responseText);
				}
			});
		});
		$("button").eq(4).click(function(){
			
			$.ajax({
				url:'/myapp/ajaxJson',
				success:function(result){
					console.log(result);
					// JSON.parse() : 문자열 -> json
					// JSON.stringify() : json -> 문자열
					var jsonData = JSON.parse(result);
					console.log(jsonData);
					var tag = "<li>";
					tag += "이름->"+jsonData.name+"<br/>";
					tag += "나이->"+jsonData.age+"<br/>";
					tag += "이메일 ->"+jsonData.email.first+", "+jsonData.email.second+"<br/>";
					tag += "</li>";
					$("#resultView").append(tag);
				},
				error:function(e){
					console.log(e.responseText);
				}
			});
		});
		$("button").eq(5).click(function(){
			$.ajax({
				url:'/myapp/ajaxObjectRest',
				success:function(result){
					console.log(result);
					var tag = "<li>";
					tag += "번호->"+result.no+"<br/>";
					tag += "제목->"+result.subject+"<br/>";
					tag += "아이디->"+result.userid+"<br/>";
					tag += "조회수->"+result.hit+"<br/>";
					tag += "</li>";
					$("#resultView>ul").append(tag)
				},
				error:function(e){
					console.log(e.responseText);
				}
			});
		});
		
		$("button").eq(6).click(function(){
			$.ajax({
				url:'/myapp/ajaxListRest',
				success:function(result){
					console.log(result);
					$(result).each(function(i, d){
						$("#resultView>ul").append("<li>"+d.num+", "+d.username+", "+d.tel+", "+d.addr)
					});
					/* let membersTag = "<li><ol>"
					$(result).each(function(idx, data){
						membersTag += "<li>num:"+data.num+",username:"+data.username;
						membersTag += ", tel:"+data.tel+", addr:"+data.addr+"</li>";
					}); 
					membersTag += "</ol></li>";
					$("#resultView>ul").append(membersTag); */

				},
				error:function(e){
					console.log(e.responseText);
				}
			});
		});
	});// jquery
</script>
</head>
<body>
<h1>비동기식 처리(ajax)</h1>
<button>ajaxString:문자열</button>
<button>ajaxObject:DTO,VO</button>
<button>ajaxList</button>
<button>ajaxMap</button>
<button>ajaxJson</button>
<button>ajaxObject(Rest)</button>
<button>ajaxList(Rest)</button>
<hr />
<div id="resultView">
	<ul></ul>
</div>
</body>
</html>