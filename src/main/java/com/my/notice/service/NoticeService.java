package com.my.notice.service;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;
import com.my.notice.dto.NoticeDTO;
import com.my.util.PageGroup;

public interface NoticeService {
	/**
	 * 전체 공지 게시글들을 페이징하여 모두 반환한다
	 * @author 나원희
	 * @param currentPage 현재 페이지 번호
	 * @param teamNo 팀번호
	 * @return 페이징된 게시글들
	 * @throws FindException DB 연결 실패할 시 예외 발생한다
	 */
	PageGroup<NoticeDTO> findNoticeAll(int currentPage, Integer teamNo) throws FindException;

	/**
	 * 팀 공지 테이블의 게시글 번호에 해당되는 공지 게시글 한개를 반환한다
	 * @author 나원희
	 * @param teamNo 팀번호
	 * @param noticeNo 공지게시글 번호
	 * @return 게시글
	 * @throws FindException DB연결 실패할 시 예외 발생한다
	 */
	NoticeDTO findByNoticeNo(Integer teamNo, Integer noticeNo) throws FindException;

	/**
	 * 작성한 게시글 정보를 추가한다
	 * @author 나원희
	 * @param teamNo 팀번호
	 * @param notice 게시글번호
	 * @throws AddException DB연결 실패할 시 예외 발생한다
	 */
	Integer writeNotice(Integer teamNo, NoticeDTO notice) throws AddException;

	/**
	 * 해당되는 게시글을 삭제한다
	 * @author 나원희
	 * @param teamNo 팀번호
	 * @param notice_no 게시글번호
	 * @throws RemoveException DB연결 실패할 시 예외 발생한다
	 */
	void removeNotice(Integer teamNo, Integer noticeNo) throws RemoveException;

	/**
	 * 게시글 정보를 수정한다
	 * @author 나원희
	 * @param teamNo 팀번호
	 * @param notice 게시글번호
	 * @throws ModifyException DB연결 실패 시 예외 발생한다
	 */
	void modifyNotice(Integer teamNo, NoticeDTO notice) throws ModifyException;

	/**
	 * 메인공지 여부를 통해 게시글을 조회한다
	 * @author 나원희
	 * @param teamNo 팀번호
	 * @return 메인공지 게시글
	 * @throws FindException DB와의 연결 실패 시 예외 발생한다
	 */
	NoticeDTO findMainNotice(Integer teamNo) throws FindException;

	/**
	 * 메인공지 여부를 변경한다
	 * @param teamNo 팀번호
	 * @param noticeNo 게시글번호
	 * @param mainStatus 메인공지 여부
	 * @throws ModifyException DB와의 연결 실패 시 예외 발생한다
	 */
	void setMainNotice(Integer teamNo, Integer noticeNo, Integer mainStatus) throws ModifyException, FindException;
}
