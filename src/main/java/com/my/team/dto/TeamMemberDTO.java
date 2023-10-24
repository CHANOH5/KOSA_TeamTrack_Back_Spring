package com.my.team.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul") // 일단 보류
@Getter @Setter @ToString
public class TeamMemberDTO {

	// 팀원 테이블
	private String id;					// 회원 아이디
	private String nickname;			// 닉네임
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Date joinDate;				// 팀가입일
	private Integer attendance;			// 출석수
	private Integer rankSum;			// 개인랭킹 누적합
	private Integer bestRank;			// 개인랭킹 최고순위
	private Integer status;				// 회원 상태
	
	private int rankAvg;
	private double attendanceRate;
	private double taskCompleteRate;
	private Integer month;				//랭킹을 위한 월

} // end class
