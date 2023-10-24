package com.my.task.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MemberTaskDTO {

	private Integer taskNo;
	private int hwScore;
	private String id;
	private String nickname;
	private int reviewScore;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Date submitDate;
	//private List<Integer> memberAnswerList;
	private String title;

	// rank 산정에 필요한 변수
	private Double totalScore;	//총 과제 점수
	private Integer taskNum;	//제출한 과제 개수
	private Integer month;		//월

}
