package com.smart.home.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// lombok은 
// 어노테이션 @Data를 표기하면 @setter,@getter,toString()을 
// 		   기술하지 않아도 된다.

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataDTO {
	private int no;
	private String subject;
	private String content;
	private String userid;
	private String hit;
	private String writedate;
	// 삭제된 파일명
	private List<String> delFile;

}
