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
 #filelist b{
	cursor:pointer;
}
</style>
<script>
//document가 로딩이 완료되면 ready 이벤트에 의해서 호출된다.
/* jQuery(function(){      
}); */
/* $(document).ready(function(){
   
}); */

$(function(){
   //$(선택자).함수(function){}
/* }); */
   $(document).on('click','#frm input[value="+"]',function(){
      //파일첨부 input 추가   
      var tag="<div><input type='file' name='filename' />";                           
      tag += "<input type='button' value='+' /></div>";
      //현재 내용의 마지막에 푸가
      $("#filelist").append(tag);
      //방금 이벤트가 발생한 + => -로 변경한다.
      $(this).val(' - ');
      $(this).parent().css("background","yellow");
   });
   $(document).on('click','#frm input[value=" - "]',function(){
      $(this).parent().remove();
   });
   //수정 시 파일 삭제를 할 경우 파일명은 화면에서 숨기고
   //hidden input은 name 속성을 설정한다. 
   $("#filelist b").click(function(){
      $(this).parent().css("display","none"); //삭제된 파일명 숨기기 
      //삭제된 파일명을 가진 input을 name 속성 설정하기
      //HTML 속성 변경 :  attr()▶속성과 값이 있을 때, prop()▶속성만 있을 때
      $(this).parent().next().attr("name","delFile")
   });
});   
</script>
<main>
   <h1>자료실 글수정</h1>
   <!--파일 첨부가 있을 경우 form태그에 enctype속성을 반드시 기술하여야한다  -->
<form id="frm" action="/home/data/dataEditOk" method="post" enctype="multipart/form-data">
   <!--현재 수정 글의 글 번호를 폼에 보관 나중에 수정 기준-->
<input type="hidden" name="no" value="${dto.no}" />
<ul>
	<li>
      제목
   </li>
   <li>
      <input type="text" name="subject" id="subject" value="${dto.subject }"/>
   </li>
   <li>글 내용</li>
   <li>
      <textarea name="content" id="content">${dto.content}</textarea>
   </li>
   <li>
      첨부파일
   </li>
   <li id='filelist'>
      <!--이미 첨부된 파일을 디시 수정할 수 있도록 해준다-->
      <c:forEach var="fileDTO" items="${fileList}">
         <div>
            ${fileDTO.filename }<b>『X』</b>
         </div>
         <input type="hidden" name="" value="${fileDTO.filename }" />
      </c:forEach>
            <div>
               <input type="file" name="filename" id="filename" />
               <input type="button" value="+"/>
            </div>
         </li>
         <li>
            <input type="submit" value="자료실 수정하기" />
         </li>
      </ul>
   </form>
</main>
<script>
CKEDITOR.ClassicEditor.create(document.getElementById("content"), option);
</script>