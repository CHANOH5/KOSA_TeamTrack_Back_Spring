package com.my.notice.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class NoticeDTO {
	private Integer noticeNo;
	private String noticeTitle;
	private String noticeContent;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Date regDate;
	private Integer mainStatus;

	public NoticeDTO(String noticeTitle, Date regDate, String noticeContent, Integer mainStatus){
		this.noticeTitle = noticeTitle;
		this.noticeContent = noticeContent;
		this.regDate = regDate;
		this.mainStatus = mainStatus;
	}

	public NoticeDTO(Integer noticeNo, String noticeTitle, String noticeContent, Integer mainStatus){
		this.noticeNo = noticeNo;
		this.noticeTitle = noticeTitle;
		this.noticeContent = noticeContent;
		this.mainStatus = mainStatus;
	}
}
