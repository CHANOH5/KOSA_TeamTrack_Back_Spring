package com.my.notice.dao;

import java.util.List;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;
import com.my.notice.dto.NoticeDTO;

public interface NoticeDAO {
	/**
	 * 상품을 오름차순 정렬된 공지 게시글들 중 시작행에서부터 끝행까지 상품들을 조회한다
	 * @author 나원희
	 * @param startRow 시작행
	 * @param endRow 마지막행
	 * @param teamNo 팀번호
	 * @return 게시글들
	 * @throws FindException DB와의 연결 실패 시 예외 발생한다
	 */
	List<NoticeDTO> selectNoticeAll(int startRow, int endRow, Integer teamNo) throws FindException;

	/**
	 * 전체 게시글 수를 조회한다
	 * @author 나원희
	 * @param teamNo 팀번호
	 * @return 전체 게시글 수
	 * @throws FindException 게시글이 없거나 DB와의 연결이 실패하면 예외 발생한다
	 */
	int selectNoticeCount(Integer teamNo) throws FindException;

	/**
	 * 팀 테이블의 게시글 번호에 해당되는 게시글을 조회한다
	 * @author 나원희
	 * @param teamNo 팀번호
	 * @param noticeNo 게시글번호
	 * @return 게시글
	 * @throws FindException DB와의 연결 실패 시 예외 발생한다
	 */
	NoticeDTO selectByNoticeNo(Integer teamNo, Integer noticeNo) throws FindException;

	/**
	 * 작성한 게시글 정보를 DB에 추가한다
	 * @author 나원희
	 * @param teamNo 팀번호
	 * @param notice 게시글정보
	 * @return 게시글번호
	 * @throws AddException DB와의 연결 실패 시 예외 발생한다
	 */
	Integer insertNotice(Integer teamNo, NoticeDTO notice) throws AddException;

	/**
	 * 해당되는 게시글을 DB에서 삭제한다
	 * @author 나원희
	 * @param teamNo 팀번호
	 * @param noticeNo 게시글번호
	 * @throws RemoveException DB와의 연결 실패 시 예외 발생한다
	 */
	void deleteNotice(Integer teamNo, Integer noticeNo) throws RemoveException;

	/**
	 * 게시글 정보를 수정한다
	 * @author 나원희
	 * @param teamNo 팀번호
	 * @param notice 게시글번호
	 * @throws ModifyException DB와의 연결 실패 시 예외 발생시킨다
	 */
	void updateNotice(Integer teamNo, NoticeDTO notice) throws ModifyException;

	/**
	 * 메인공지 게시글을 조회한다
	 * @author 나원희
	 * @param teamNo 팀번호
	 * @return 메인공지 게시글
	 * @throws FindException DB와의 연결 실패 시 예외 발생한다
	 */
	NoticeDTO selectMainNotice(Integer teamNo) throws FindException;

	/**
	 * 메인공지여부를 변경한다
	 * @author 나원희
	 * @param teamNo 팀번호
	 * @param noticeNo 게시글번호
	 * @param mainStatus 메인공지 여부
	 * @throws ModifyException DB와의 연결 실패 시 예외 발생한다
	 */
	void updateMainStatus(Integer teamNo, Integer noticeNo, Integer mainStatus) throws ModifyException;
}
