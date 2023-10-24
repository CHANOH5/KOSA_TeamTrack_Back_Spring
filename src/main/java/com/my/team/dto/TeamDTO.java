package com.my.team.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter @Setter @ToString
public class TeamDTO {

	// 팀 테이블
	private int teamNo;
	private String teamName;
	private String leaderId;
	private String studyType;
	private String onOffLine;
	private Integer joinMember;
	private int maxMember;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Date createDate;
	private String startDate;
	private String endDate;
	private String briefInfo;
	private String teamInfo;
	private Integer viewCnt;
	
} // end class
