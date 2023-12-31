package com.smart.home.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.smart.home.dto.DataDTO;
import com.smart.home.dto.DataFileDTO;
import com.smart.home.service.DataService;

@Controller
@RequestMapping("/data")
public class DataController {
@Autowired
   DataService service;
   //글 목록
   @GetMapping("/dataList")
   public String dataList(Model model) {
      //db레코드 선택하여 model에 셋팅
	   model.addAttribute("list",service.getDataList());
	   return "data/dataList";
   }
   //글 등록 폼
   @GetMapping("/dataWrite")
   public String dataWrite() {
      return "data/dataWrite";
   }
   
   //글 등록 DB에
   @PostMapping("/dataWriteOk")
   public ModelAndView dataWriteOk(HttpServletRequest request, DataDTO dto) {
   //파일을 업로드 할 경로 / 업로드의 실제 주소(절대경로)
      String path=request.getSession().getServletContext().getRealPath("/upload");
       System.out.println("path->"+path);
       
       //dto -> 제목, 글 내용, session 에서 글쓴이
       // no -> 시퀀스, hit, writedate -> default값
       dto.setUserid((String)request.getSession().getAttribute("logId"));
       //---------------------------------파일업로드--------------------------
       //1. 파일업로드를 위해서는 request 객체들 multipartRequest 객체로 형변환다.
       MultipartHttpServletRequest mr = (MultipartHttpServletRequest)request;
       //2. MulitpartFile 객체를 얻어오기
       List<MultipartFile> fileList = mr.getFiles("filename");
       System.out.println("fileList.size()->"+fileList.size());
       //첨부 파일이 있을 때 
       
       //업로드한 파일들을 보관할 컬렉션 
       List<DataFileDTO> upFileList = new ArrayList<DataFileDTO>();
       
       if(fileList!=null) {//첨부파일이 있을 때 if 1
          for(int i=0; i<fileList.size(); i++){//첨부 파일 수만큼 반복 수행//첨부 파일 수만큼 반복 수행
             MultipartFile mf=fileList.get(i);//첨부된 MulitipartFile 객체 얻어오기 
          
             String orgFileName=mf.getOriginalFilename();//파일명 얻어오기
             System.out.println("orgFileName->"+orgFileName);
             
             if(orgFileName!=null && !orgFileName.equals("")) {//업로드한 파일이 있으면 if 2
                File f=new File(path,orgFileName);
                if(f.exists()) {//true이면 파일이 존재, false이면 파일이 존재하지않는다.
                   //파일명 변경 
                   int point=orgFileName.lastIndexOf(".");//파일명과 확장자 분리 
                   String orgFile=orgFileName.substring(0,point);
                   String orgExt=orgFileName.substring(point+1);
                   
                   for(int renameNum=1; ;renameNum++) {//1,2,3,4,5....
                      String newFileName=orgFile + "("+renameNum+")."+orgExt;
                      f = new File(path, newFileName);//파일이 없을 대까지 만든다. 
                      if(!f.exists()) {//없을 때 
                         //새로 만들어진 파일명을 업로드할 때 사용하여야 하므로 
                         orgFileName=newFileName;
                         break;//반복 중지 
                      }
                   }//for
                   
                }
                //업로드 수행
                try {
                mf.transferTo(new File(path,orgFileName));
                }catch(Exception e) {
                e.printStackTrace();
                }
                System.out.println("업로드 된 파일명 : " + orgFileName);
                DataFileDTO upFileDTO = new DataFileDTO();
                upFileDTO.setFilename(orgFileName);
                upFileList.add(upFileDTO);
             }//if 2
          }//for 1
       }//if 1
       ModelAndView mav=new ModelAndView();
       try {
       //=========DB처리==================================================================================
       //원글 insert 수행 ▶ 생성된 시퀀스 번호를 결과로 받아야 파일명을 DB에 추가할 때 사용해야 한다. 
       int result=service.dataInsert(dto);
       //파일명이 있는 DTO에 원글번호를 추가
       for(int i=0; i<upFileList.size(); i++) {
          upFileList.get(i).setNo(dto.getNo());
          
       }
          //파일명 DB insert
          int resultFile=service.dataFileInsert(upFileList);
          
          //정상구현
          mav.setViewName("redirect:dataList");
       }catch(Exception e) {
          e.printStackTrace();
          //원글 지우기(dto.no)
          service.dataDelete(dto.getNo(),dto.getUserid());
          //파일명(DB) 지우기(dto.no)
          service.dataFileDelete(dto.getNo());
          //파일 삭제(upFileList)
          for(int i=0; i<upFileList.size(); i++) {
             fileDelete(path,upFileList.get(i).getFilename());
          }
          //실패
          mav.setViewName("data/dataResult");
       }

      
      
      return mav;
   }
   // 자료실 글내용 보기
   @GetMapping("/dataView/{no}")
   public ModelAndView dataView(@PathVariable("no") int no) {
	   ModelAndView mav = new ModelAndView();
	   // 조회수
	   service.hitCount(no);
	   // 해당 글선택
	   mav.addObject("dto", service.dataSelect(no));
	   // 해당글의 첨부파일 선택
	   mav.addObject("fileList", service.dataFileSelect(no));
	   // 뷰페이지
	   mav.setViewName("data/dataView");
	   return mav;
   }
   // 자료실 글 수정
   @GetMapping("/dataEdit")
   public ModelAndView dataEdit(int no) {
	   ModelAndView mav = new ModelAndView();
	   // 현재글 
	   mav.addObject("dto",service.dataSelect(no));
	   // 첨부파일선택
	   mav.addObject("fileList",service.dataFileSelect(no));
	   
	   mav.setViewName("/data/dataEdit");
	   return mav;
   }
   // 자료실 글 수정하기
   @PostMapping("/dataEditOk") // no, subject, content, filename, delFile
   public ModelAndView dataEditOk(DataDTO dto, HttpSession session, HttpServletRequest request) {
	   
	   // 1. 기존에 업로드된 파일목록 DB에서 가져오기
	   List<DataFileDTO> orgFileList = service.dataFileSelect(dto.getNo());
	   
	   // 2. 저장 위치
	   String path = session.getServletContext().getRealPath("/upload");
	   
	   // 3. 새로 추가한 파일업로드하기 -> MultipartHttpServletRequest(request객체)
	   MultipartHttpServletRequest mr = (MultipartHttpServletRequest)request;
	   
	   // 4. 업로드된 파일(MultipartFile)목록
	   List<MultipartFile> fileList= mr.getFiles("filename");
	   // 새로 업로드한 파일명들
	   List<DataFileDTO> newFileList = new ArrayList<DataFileDTO>();
	   // 5. 업로드된 파일이 있으면 업로드(rename)
	   if(fileList!=null) {
		   for(int i=0;i<fileList.size();i++) {
			   MultipartFile mf = fileList.get(i);
			   // 파일명 구하기
			   String orgFileName = mf.getOriginalFilename();
			   if(orgFileName!=null && !orgFileName.equals("")) {// 파일명이 있으면
				   File f = new File(path, orgFileName);
				   if(f.exists()) {// 같은 파일명을 가진 파일이 존재하면
					   // 기존파일명과 중복검사
					   int p = orgFileName.lastIndexOf(".");
					   String fileNoExt = orgFileName.substring(0, p);
					   String ext = orgFileName.substring(p+1);
					   
					   for(int fileNum=1; ; fileNum++) {
						   String newFile = fileNoExt+" ("+fileNum+")."+ext;
						   f = new File(path, newFile);
						   if(!f.exists()) {
							   orgFileName = newFile;
							   break;
						   }
					   }
				   }
				   // 업로드
				   try {
					   mf.transferTo(new File(path, orgFileName));
					   DataFileDTO fDTO = new DataFileDTO();
					   fDTO.setNo(dto.getNo());
					   fDTO.setFilename(orgFileName);
					   newFileList.add(fDTO);
				   }catch(Exception e) {e.printStackTrace();
					   
				   }
			   }
		   }
	   }
	   /*
	    * 원래DB파일		-> List<DataFileDTO> orgFileList a.gif, b.gif, c.gif
	    * 새로업로드된파일	-> List<DataFileDTO> newFileList d.gif
	    * 삭제된파일		-> List<DataFileDTO> delFile	 b.gif
	    * */
	   // orgFileList에 새로업로드된 파일목록을 추가하기
	    orgFileList.addAll(newFileList);
//	   for(DataFileDTO newDTO:newFileList) {
//		   orgFileList.add(newDTO);
//	   }
	   
	   // orgFileList에서 삭제될 파일객체를 지우기
	   if(dto.getDelFile()!=null) {// 삭제파일이 있으면
		   for(int i=0; i<dto.getDelFile().size(); i++) {
			   String del = dto.getDelFile().get(i);	// b.gif
			   for(int idx=0; idx<orgFileList.size(); idx++) {
				   DataFileDTO resetFile = orgFileList.get(idx);
				   // b.gif					b.gif
				   if(del.equals(resetFile.getFilename())) {// 삭제할 파일명과 orgFileList에 있는 파일명이 같으면
					   orgFileList.remove(idx);
					   break;
				   }
			   }
		   }
	   }
	   // -----
	   ModelAndView mav = new ModelAndView();
	   try {
		   // 원 레코드 업데이트 
		   int result = service.dataUpdate(dto);
		   // 파일목록 -> 삭제, 추가
		   service.dataFileDelete(dto.getNo());
		   service.dataFileInsert(orgFileList);
		   // 삭제한 파일을 /upload폴더에서 제거
		   if(dto.getDelFile()!=null) {
			   for(String delFilename:dto.getDelFile()) {
				   fileDelete(path, delFilename);
			   }
		   }
		   // 글내용보기로 이동
		   mav.setViewName("redirect:dataView/"+dto.getNo());
	   }catch(Exception e) {
		   e.printStackTrace();
		   
		   // 새로업로드된 파일 삭제
		   for(DataFileDTO fDTO: newFileList) {
			   fileDelete(path, fDTO.getFilename());
		   }
		   // 글내용수정
		   mav.setViewName("redirect:dataEdit?no="+dto.getNo());
	   }
	   return mav;
   }
   // 자료실 글 삭제하기
   @GetMapping("/dataDel")
   public ModelAndView dataDelete(int no,HttpSession session) {
	   // 파일삭제시 경로 필요함
	   String path = session.getServletContext().getRealPath("/upload");
	   
	   // 1. 삭제할 글의 첨부파일 선택보관한다.
	   List<DataFileDTO> fileList = service.dataFileSelect(no);
	   
	   // 2. 첨부파일 레코드를 지운다.
	   int delCount = service.dataFileDelete(no);
	   
	   // 3. 원글(DB삭제)
	   int result = service.dataDelete(no, (String)session.getAttribute("logId"));
	   
	   // 4. 첨부파일 삭제
	   for(DataFileDTO dto : fileList) {
		   fileDelete(path, dto.getFilename());
	   }
	   // 5. 삭제시 글목록
	   ModelAndView mav = new ModelAndView();
	   if(result>0) {// 삭제시
		   mav.setViewName("redirect:dataList");
	   }else {// 실패시 글내용보기
		   mav.setViewName("redirect:dataView/"+no);
	   }
	   return mav;
	   
   }
   // 파일 삭제하는 메소드 
   public void fileDelete(String path, String filename) {
      try {
         File f=new File(path,filename);
         f.delete();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   
}