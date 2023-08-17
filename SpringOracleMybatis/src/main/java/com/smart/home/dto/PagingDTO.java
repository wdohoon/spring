package com.smart.home.dto;

// 페이지관련변수, 검색어 관련변수
public class PagingDTO {
	private int nowPage = 1; // 현재 페이지
	private int onePageRecord = 5; // 한 페이지에 표시할 레코드 수
	private int totalRecord; // 총레코드 수
	private int totalPage; // 총레코드 수
	
	private int onePageNumCount = 5; // 한페이지에 표시되는 페이지 수
	private int startPageNum = 1;
	
	private int lastPageRecord = 5;//마지막 페이지의 남아있는 레코드 수
	
	private String searchKey; // 검색키 subject, content, userid
	private String searchWord;// 검색어
	
	public int getNowPage() {
		return nowPage;
	}
	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
		
		// 페이지의 시작번호 생성하기
		// 시작번호 = ((현재페이지-1)/한페이지페이에 표시할 페이지수)*한페이지에 표시할 페이지수 + 1;
		startPageNum = ((nowPage-1)/onePageNumCount)*onePageNumCount + 1;
	}
	public int getOnePageRecord() {
		return onePageRecord;
	}
	public void setOnePageRecord(int onePageRecord) {
		this.onePageRecord = onePageRecord;
	}
	public int getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
		
		// 총페이지 수 계산하기 16->4 15->3 11->3
		// 				   3.444 3.0   2.111
		// ceil():올림, round():반올림, floor():버림
		totalPage = (int)Math.ceil((double)totalRecord/onePageRecord);
		
		// 마지막페에지의 남아 있는 레코드 수
		lastPageRecord = onePageRecord;//5
		if(totalPage==nowPage) {
			if(totalRecord%onePageRecord!=0) {
				lastPageRecord = totalRecord%onePageRecord; // 1,2,3,4
			}
		}
		System.out.println("lastPageRecord->"+lastPageRecord);
	}	
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
		
	}
	public int getOnePageNumCount() {
		return onePageNumCount;
	}
	public void setOnePageNumCount(int onePageNumCount) {
		this.onePageNumCount = onePageNumCount;
	}
	public int getStartPageNum() {
		return startPageNum;
	}
	public void setStartPageNum(int startPageNum) {
		this.startPageNum = startPageNum;
	}
	public int getLastPageRecord() {
		return lastPageRecord;
	}
	public void setLastPageRecord(int lastPageRecord) {
		this.lastPageRecord = lastPageRecord;
	}
	public String getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	public String getSearchWord() {
		return searchWord;
	}
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}
	
}
