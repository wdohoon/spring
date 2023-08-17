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
   //�� ���
   @GetMapping("/dataList")
   public String dataList(Model model) {
      //db���ڵ� �����Ͽ� model�� ����
	   model.addAttribute("list",service.getDataList());
	   return "data/dataList";
   }
   //�� ��� ��
   @GetMapping("/dataWrite")
   public String dataWrite() {
      return "data/dataWrite";
   }
   
   //�� ��� DB��
   @PostMapping("/dataWriteOk")
   public ModelAndView dataWriteOk(HttpServletRequest request, DataDTO dto) {
   //������ ���ε� �� ��� / ���ε��� ���� �ּ�(������)
      String path=request.getSession().getServletContext().getRealPath("/upload");
       System.out.println("path->"+path);
       
       //dto -> ����, �� ����, session ���� �۾���
       // no -> ������, hit, writedate -> default��
       dto.setUserid((String)request.getSession().getAttribute("logId"));
       //---------------------------------���Ͼ��ε�--------------------------
       //1. ���Ͼ��ε带 ���ؼ��� request ��ü�� multipartRequest ��ü�� ����ȯ��.
       MultipartHttpServletRequest mr = (MultipartHttpServletRequest)request;
       //2. MulitpartFile ��ü�� ������
       List<MultipartFile> fileList = mr.getFiles("filename");
       System.out.println("fileList.size()->"+fileList.size());
       //÷�� ������ ���� �� 
       
       //���ε��� ���ϵ��� ������ �÷��� 
       List<DataFileDTO> upFileList = new ArrayList<DataFileDTO>();
       
       if(fileList!=null) {//÷�������� ���� �� if 1
          for(int i=0; i<fileList.size(); i++){//÷�� ���� ����ŭ �ݺ� ����//÷�� ���� ����ŭ �ݺ� ����
             MultipartFile mf=fileList.get(i);//÷�ε� MulitipartFile ��ü ������ 
          
             String orgFileName=mf.getOriginalFilename();//���ϸ� ������
             System.out.println("orgFileName->"+orgFileName);
             
             if(orgFileName!=null && !orgFileName.equals("")) {//���ε��� ������ ������ if 2
                File f=new File(path,orgFileName);
                if(f.exists()) {//true�̸� ������ ����, false�̸� ������ ���������ʴ´�.
                   //���ϸ� ���� 
                   int point=orgFileName.lastIndexOf(".");//���ϸ�� Ȯ���� �и� 
                   String orgFile=orgFileName.substring(0,point);
                   String orgExt=orgFileName.substring(point+1);
                   
                   for(int renameNum=1; ;renameNum++) {//1,2,3,4,5....
                      String newFileName=orgFile + "("+renameNum+")."+orgExt;
                      f = new File(path, newFileName);//������ ���� ����� �����. 
                      if(!f.exists()) {//���� �� 
                         //���� ������� ���ϸ��� ���ε��� �� ����Ͽ��� �ϹǷ� 
                         orgFileName=newFileName;
                         break;//�ݺ� ���� 
                      }
                   }//for
                   
                }
                //���ε� ����
                try {
                mf.transferTo(new File(path,orgFileName));
                }catch(Exception e) {
                e.printStackTrace();
                }
                System.out.println("���ε� �� ���ϸ� : " + orgFileName);
                DataFileDTO upFileDTO = new DataFileDTO();
                upFileDTO.setFilename(orgFileName);
                upFileList.add(upFileDTO);
             }//if 2
          }//for 1
       }//if 1
       ModelAndView mav=new ModelAndView();
       try {
       //=========DBó��==================================================================================
       //���� insert ���� �� ������ ������ ��ȣ�� ����� �޾ƾ� ���ϸ��� DB�� �߰��� �� ����ؾ� �Ѵ�. 
       int result=service.dataInsert(dto);
       //���ϸ��� �ִ� DTO�� ���۹�ȣ�� �߰�
       for(int i=0; i<upFileList.size(); i++) {
          upFileList.get(i).setNo(dto.getNo());
          
       }
          //���ϸ� DB insert
          int resultFile=service.dataFileInsert(upFileList);
          
          //������
          mav.setViewName("redirect:dataList");
       }catch(Exception e) {
          e.printStackTrace();
          //���� �����(dto.no)
          service.dataDelete(dto.getNo(),dto.getUserid());
          //���ϸ�(DB) �����(dto.no)
          service.dataFileDelete(dto.getNo());
          //���� ����(upFileList)
          for(int i=0; i<upFileList.size(); i++) {
             fileDelete(path,upFileList.get(i).getFilename());
          }
          //����
          mav.setViewName("data/dataResult");
       }

      
      
      return mav;
   }
   // �ڷ�� �۳��� ����
   @GetMapping("/dataView/{no}")
   public ModelAndView dataView(@PathVariable("no") int no) {
	   ModelAndView mav = new ModelAndView();
	   // ��ȸ��
	   service.hitCount(no);
	   // �ش� �ۼ���
	   mav.addObject("dto", service.dataSelect(no));
	   // �ش���� ÷������ ����
	   mav.addObject("fileList", service.dataFileSelect(no));
	   // ��������
	   mav.setViewName("data/dataView");
	   return mav;
   }
   // �ڷ�� �� ����
   @GetMapping("/dataEdit")
   public ModelAndView dataEdit(int no) {
	   ModelAndView mav = new ModelAndView();
	   // ����� 
	   mav.addObject("dto",service.dataSelect(no));
	   // ÷�����ϼ���
	   mav.addObject("fileList",service.dataFileSelect(no));
	   
	   mav.setViewName("/data/dataEdit");
	   return mav;
   }
   // �ڷ�� �� �����ϱ�
   @PostMapping("/dataEditOk") // no, subject, content, filename, delFile
   public ModelAndView dataEditOk(DataDTO dto, HttpSession session, HttpServletRequest request) {
	   
	   // 1. ������ ���ε�� ���ϸ�� DB���� ��������
	   List<DataFileDTO> orgFileList = service.dataFileSelect(dto.getNo());
	   
	   // 2. ���� ��ġ
	   String path = session.getServletContext().getRealPath("/upload");
	   
	   // 3. ���� �߰��� ���Ͼ��ε��ϱ� -> MultipartHttpServletRequest(request��ü)
	   MultipartHttpServletRequest mr = (MultipartHttpServletRequest)request;
	   
	   // 4. ���ε�� ����(MultipartFile)���
	   List<MultipartFile> fileList= mr.getFiles("filename");
	   // ���� ���ε��� ���ϸ��
	   List<DataFileDTO> newFileList = new ArrayList<DataFileDTO>();
	   // 5. ���ε�� ������ ������ ���ε�(rename)
	   if(fileList!=null) {
		   for(int i=0;i<fileList.size();i++) {
			   MultipartFile mf = fileList.get(i);
			   // ���ϸ� ���ϱ�
			   String orgFileName = mf.getOriginalFilename();
			   if(orgFileName!=null && !orgFileName.equals("")) {// ���ϸ��� ������
				   File f = new File(path, orgFileName);
				   if(f.exists()) {// ���� ���ϸ��� ���� ������ �����ϸ�
					   // �������ϸ�� �ߺ��˻�
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
				   // ���ε�
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
	    * ����DB����		-> List<DataFileDTO> orgFileList a.gif, b.gif, c.gif
	    * ���ξ��ε������	-> List<DataFileDTO> newFileList d.gif
	    * ����������		-> List<DataFileDTO> delFile	 b.gif
	    * */
	   // orgFileList�� ���ξ��ε�� ���ϸ���� �߰��ϱ�
	    orgFileList.addAll(newFileList);
//	   for(DataFileDTO newDTO:newFileList) {
//		   orgFileList.add(newDTO);
//	   }
	   
	   // orgFileList���� ������ ���ϰ�ü�� �����
	   if(dto.getDelFile()!=null) {// ���������� ������
		   for(int i=0; i<dto.getDelFile().size(); i++) {
			   String del = dto.getDelFile().get(i);	// b.gif
			   for(int idx=0; idx<orgFileList.size(); idx++) {
				   DataFileDTO resetFile = orgFileList.get(idx);
				   // b.gif					b.gif
				   if(del.equals(resetFile.getFilename())) {// ������ ���ϸ�� orgFileList�� �ִ� ���ϸ��� ������
					   orgFileList.remove(idx);
					   break;
				   }
			   }
		   }
	   }
	   // -----
	   ModelAndView mav = new ModelAndView();
	   try {
		   // �� ���ڵ� ������Ʈ 
		   int result = service.dataUpdate(dto);
		   // ���ϸ�� -> ����, �߰�
		   service.dataFileDelete(dto.getNo());
		   service.dataFileInsert(orgFileList);
		   // ������ ������ /upload�������� ����
		   if(dto.getDelFile()!=null) {
			   for(String delFilename:dto.getDelFile()) {
				   fileDelete(path, delFilename);
			   }
		   }
		   // �۳��뺸��� �̵�
		   mav.setViewName("redirect:dataView/"+dto.getNo());
	   }catch(Exception e) {
		   e.printStackTrace();
		   
		   // ���ξ��ε�� ���� ����
		   for(DataFileDTO fDTO: newFileList) {
			   fileDelete(path, fDTO.getFilename());
		   }
		   // �۳������
		   mav.setViewName("redirect:dataEdit?no="+dto.getNo());
	   }
	   return mav;
   }
   // �ڷ�� �� �����ϱ�
   @GetMapping("/dataDel")
   public ModelAndView dataDelete(int no,HttpSession session) {
	   // ���ϻ����� ��� �ʿ���
	   String path = session.getServletContext().getRealPath("/upload");
	   
	   // 1. ������ ���� ÷������ ���ú����Ѵ�.
	   List<DataFileDTO> fileList = service.dataFileSelect(no);
	   
	   // 2. ÷������ ���ڵ带 �����.
	   int delCount = service.dataFileDelete(no);
	   
	   // 3. ����(DB����)
	   int result = service.dataDelete(no, (String)session.getAttribute("logId"));
	   
	   // 4. ÷������ ����
	   for(DataFileDTO dto : fileList) {
		   fileDelete(path, dto.getFilename());
	   }
	   // 5. ������ �۸��
	   ModelAndView mav = new ModelAndView();
	   if(result>0) {// ������
		   mav.setViewName("redirect:dataList");
	   }else {// ���н� �۳��뺸��
		   mav.setViewName("redirect:dataView/"+no);
	   }
	   return mav;
	   
   }
   // ���� �����ϴ� �޼ҵ� 
   public void fileDelete(String path, String filename) {
      try {
         File f=new File(path,filename);
         f.delete();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   
}