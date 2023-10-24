package com.my.qna.service;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;
import com.my.qna.dto.QnaBoardDTO;
import com.my.util.PageGroup;

public interface QnaBoardService {

	/**
	 * qna게시판에 게시글을 생성한다
	 * @param qnaBoardDTO 게시글 정보
	 * @throws AddException DB와의 연결 실패 또는 제약조건에 위반시 예외 발생
	 */
	public void create(Integer teamNo, QnaBoardDTO qnaBoardDTO) throws AddException;
	
	/**
	 * 전체 게시글을 페이징 처리하여 불러온다.
	 * @param teamNo 팀 번호
	 * @param currentPage 현재 페이지
	 * @return 게시글
	 * @throws FindException
	 */
	public PageGroup<QnaBoardDTO> selectAll(Integer teamNo, int currentPage) throws FindException;
	
	/**
	 * 
	 * @param teamNo
	 * @param QnaNo
	 * @return
	 * @throws FindException
	 */
	public QnaBoardDTO selectByQnaNo(Integer teamNo, Integer QnaNo) throws FindException;
	

	/**
	 * qna게시판의 게시글을 수정한다
	 * @param qnaBoardDTO 게시글 수정 정보
	 * @return
	 * @throws ModifyException DB와의 연결 실패 또는 게시글 수정 실패시 예외 발생
	 */
	public Integer update(Integer teamNo, QnaBoardDTO qnaBoardDTO) throws ModifyException;

	/**
	 * qna게시판의 게시글을 삭제한다
	 * @param teamNo
	 * @param qna_no 게시글 번호
	 * @return
	 * @throws ModifyException  DB와의 연결 실패 또는 게시물이 없을 경우 예외 발생
	 */
	public Integer delete(Integer teamNo, Integer qna_no) throws ModifyException;
	
	/**
	 * qna게시판 - 팀 멤버인지 확인하기
	 * @param teamMemberDTO
	 * @return
	 * @throws FindException
	 */
	Integer selectTeamMemberStatus(String id, Integer teamNo) throws FindException;
	

} // end interface
