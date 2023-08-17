<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script src="https://cdn.ckeditor.com/ckeditor5/38.0.1/super-build/ckeditor.js"></script>
<script src="/home/inc/ckeditor.js"></script>
<style>
   .ck-editor__editable[role="textbox"] {
       /* editing area */
       min-height: 200px;
   }
   .ck-content .image {
       /* block images */
       max-width: 80%;
       margin: 20px auto;
   }
</style>
<script>
	// document가 로딩이 완료되면 ready이벤트에 의해서 호출된
    $(function(){ // ready 이벤트
		// $(선택자).함수(function(){
		// 실행문
		// });
    	// 선택자			이벤트	선택자
    	$(document).on('click','#frm input[value=" + "]',function(){
    		// 파일첨부 input 추가
    		var tag ="<div><input type='file' name='filename'/>";
    		tag += "<input type='button' value=' + '/></div>";
    		$("#filelist").append(tag);
    		
    		// 방금 이벤트가 발생한 + -> -로 변경한다.
    		$(this).val(' - ');
    		$(this).parent().css("background","yellow");
    	});
    	$(document).on('click','#frm input[value=" - "]',function(){
    		$(this).parent().remove();
    	});
	});
</script>
<main>
   <h1>자료실 글쓰기</h1>
   <!-- 파일첨부가 있을 경우 form태그에 enctype속성을 반드시 기술하여야 한다. -->
   <form method="post" id="frm" action="/home/data/dataWriteOk" enctype="multipart/form-data">
      <ul>
         <li>제목</li>
         <li><input type="text" name="subject" id="subject"/></li>
         <li>글내용</li>
         <li><textarea name="content" id="content"></textarea></li>
         <li>첨부파일</li>
         <li id="filelist"></li>
         <li>
         	<div>
         		<input type="file" name="filename" id="filename"/>
         		<input type="button" value=" + " />
         	</div>
         </li>
         <li><input type="submit" value="글등록"/></li>
      </ul>
   </form>
</main>
<script>
   CKEDITOR.ClassicEditor.create(document.getElementById("content"),option);
</script>