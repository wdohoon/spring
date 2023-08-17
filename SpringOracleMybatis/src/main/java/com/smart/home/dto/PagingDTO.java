package com.smart.home.dto;

// ���������ú���, �˻��� ���ú���
public class PagingDTO {
	private int nowPage = 1; // ���� ������
	private int onePageRecord = 5; // �� �������� ǥ���� ���ڵ� ��
	private int totalRecord; // �ѷ��ڵ� ��
	private int totalPage; // �ѷ��ڵ� ��
	
	private int onePageNumCount = 5; // ���������� ǥ�õǴ� ������ ��
	private int startPageNum = 1;
	
	private int lastPageRecord = 5;//������ �������� �����ִ� ���ڵ� ��
	
	private String searchKey; // �˻�Ű subject, content, userid
	private String searchWord;// �˻���
	
	public int getNowPage() {
		return nowPage;
	}
	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
		
		// �������� ���۹�ȣ �����ϱ�
		// ���۹�ȣ = ((����������-1)/�����������̿� ǥ���� ��������)*���������� ǥ���� �������� + 1;
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
		
		// �������� �� ����ϱ� 16->4 15->3 11->3
		// 				   3.444 3.0   2.111
		// ceil():�ø�, round():�ݿø�, floor():����
		totalPage = (int)Math.ceil((double)totalRecord/onePageRecord);
		
		// �������信���� ���� �ִ� ���ڵ� ��
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