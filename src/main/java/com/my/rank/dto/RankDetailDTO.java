package com.my.rank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Data
@NoArgsConstructor
@AllArgsConstructor
public class RankDetailDTO {
	private String id;
	private Double attendanceRate;
	private Double avgTaskScore;
	private Double totalReviewScore;
	private Integer qnaPickedScore;
}
