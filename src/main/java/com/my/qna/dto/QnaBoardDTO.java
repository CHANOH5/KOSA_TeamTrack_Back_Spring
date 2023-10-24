package com.my.qna.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QnaBoardDTO {

	//	private Integer qna_no;		// 게시글 번호
	private Integer qnaNo;		// 게시글 번호
	private String id;			// 작성자 아이디
	private String title;		// 게시글 제목
	private String content;		// 게시글 내용
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Date regdate;		// 작성 날짜
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Date updatedate;	// 수정 날짜
	private Integer status;

	public QnaBoardDTO(Integer qnaNo, String title, String content) {
		
		this.qnaNo = qnaNo;
		this.title = title;
		this.content = content;
		
	} // constructor	
	
	public QnaBoardDTO(String id, String title, String content) {
		
		this.id = id;
		this.title = title;
		this.content = content;
	
	} // constructor	
	

} // end class
