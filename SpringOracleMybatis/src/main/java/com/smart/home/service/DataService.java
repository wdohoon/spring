package com.smart.home.service;

import java.util.List;

import com.smart.home.dto.DataDTO;
import com.smart.home.dto.DataFileDTO;

public interface DataService {
	// �ڷ�� ���
	public List<DataDTO> getDataList();
	// �� ����
	public DataDTO dataSelect(int no);
	// �� ���
	public int dataInsert(DataDTO dto);
	// ���ϸ� ���
	public int dataFileInsert(List<DataFileDTO> fileList);
	// �� ����
	public int dataUpdate(DataDTO dto);
	// �� ����
	public int dataDelete(int no, String userid);
	// ��ȸ�� ����
	public void hitCount(int no);
	// ÷�����ϸ�� ����
	public int dataFileDelete(int no);
	// �ش���� ÷������ ����
	public List<DataFileDTO> dataFileSelect(int no);
}
