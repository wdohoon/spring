package com.smart.home.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.home.dao.TransactionDAO;
import com.smart.home.dto.BoardDTO;

@Service
public class TransactionServiceImpl implements TransactionService {
	@Autowired
	TransactionDAO dao;
	@Override
	public int transactionInsert(BoardDTO dto) {
		return dao.transactionInsert(dto);
	}
	
}
