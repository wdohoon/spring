package com.smart.home.dao;

import java.util.List;

import com.smart.home.dto.ReplyDTO;

public interface ReplyDAO {
	public int replyInsert(ReplyDTO dto);
	public List<ReplyDTO> replySelect(int no);
	public int replyUpdate(ReplyDTO dto);
	public int replyDelete(int re_no);
}
