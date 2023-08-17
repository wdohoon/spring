package com.smart.home.service;

import java.util.List;

import com.smart.home.dto.ReplyDTO;

public interface ReplyService {
	public int replyInsert(ReplyDTO dto);
	public List<ReplyDTO> replySelect(int no);
	public int replyUpdate(ReplyDTO dto);
	public int replyDelete(int re_no);
}
