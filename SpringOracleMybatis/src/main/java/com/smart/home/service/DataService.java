package com.smart.home.service;

import java.util.List;

import com.smart.home.dto.DataDTO;
import com.smart.home.dto.DataFileDTO;

public interface DataService {
	// 자료실 목록
	public List<DataDTO> getDataList();
	// 글 선택
	public DataDTO dataSelect(int no);
	// 글 등록
	public int dataInsert(DataDTO dto);
	// 파일명 등록
	public int dataFileInsert(List<DataFileDTO> fileList);
	// 글 수정
	public int dataUpdate(DataDTO dto);
	// 글 삭제
	public int dataDelete(int no, String userid);
	// 조회수 증가
	public void hitCount(int no);
	// 첨부파일목록 삭제
	public int dataFileDelete(int no);
	// 해당글의 첨부파일 선택
	public List<DataFileDTO> dataFileSelect(int no);
}
