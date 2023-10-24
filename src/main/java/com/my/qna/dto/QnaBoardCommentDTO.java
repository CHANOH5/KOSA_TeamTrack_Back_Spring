package com.my.qna.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QnaBoardCommentDTO {
	
	Integer commentNo;			// 댓글번호

	Integer qnaNo;				// 게시글 번호
	
	String teammemberId;		// 작성자 아이디
	
	Integer commentGroup;		// 부모댓글번호
	
	String content;				// 댓글 내용
	
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	Date regdate;				// 작성 날짜
	
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	Date regdate_modified;		// 댓글 수정 날짜
	
	Integer commentStatus;		// 댓글 삭제 여부 -> 1은 댓글o , 0은 댓글삭제
	
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	Date pickeddate;			// 댓글 채택 여부 -> date가 있으면 채택, null이면 채택x

	//rank에 필요한 변수
	Integer month;
	Double pickedNum;

} //end class
