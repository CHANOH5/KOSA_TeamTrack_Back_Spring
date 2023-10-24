package com.my.rank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Data
@AllArgsConstructor @NoArgsConstructor
public class RankDTO implements Comparable{
	
	//Rank 기본 변수 
	private Integer rankNo;		//랭킹 번호
	private String rankDate;	//랭킹 일자
	private Integer rank;		//실제 랭킹
	private Double totalScore;	//랭킹 총 점수
	private String id;			//멤버 아이디
	
	//랭킹 리스트에 필요한 회원 정보
	private String nickname;	//회원 닉네임
	private String profile;		//회원 프로필
	private Integer month;		//월
	
	@Override
	public int compareTo(Object o) {
		RankDTO dto = (RankDTO) o;
		return getTotalScore().compareTo(dto.getTotalScore());
	}
	
}
