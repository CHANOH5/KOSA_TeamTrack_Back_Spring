package com.my.task.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TaskDTO {

	private Integer taskNo;
	private String id;
	private String nickname;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Date duedate1;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Date duedate2;
	private String title;
	//private String type;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Date regdate;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Date enddate;
	private double avgReviewscore;
	private int answerCnt;
	//private List<Integer> answerList;

	// rank 산정에 필요한 변수
	private Double totalReviewscore;	//리뷰 점수 총점
	private Integer taskSubmitNum;		//출제자 과제 제출 개수
	private Integer monthlyTaskNum;		//월별 과제 개수
	private Integer month;				//월

}
