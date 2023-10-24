package com.my.qna.dao;

import java.util.List;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;
import com.my.qna.dto.QnaBoardDTO;

public interface QnaBoardDAO {

	/**
	 * qna게시판에 게시글을 생성한다
	 * @param qnaBoardDTO 게시글 정보
	 * @throws AddException DB와의 연결 실패 또는 제약조건에 위반시 예외 발생
	 */
	public void create(Integer teamNo, QnaBoardDTO qnaBoardDTO) throws AddException;

	/**
	 * qna게시판의 게시글 목록을 조회한다
	 * @param teamNo 팀번호
	 * @param startRow 시작행
	 * @param endRow 끝행
	 * @return 게시글
	 * @throws FindException DB와의 연결 실패 또는 게시글 없으면 예외 발생
	 */
	public List<QnaBoardDTO> selectAll(Integer teamNo, int startRow, int endRow) throws FindException;

	/**
	 * 전체 게시글 수를 조회한다
	 * @param teamNo 팀번호
	 * @return 전체 게시글 수
	 * @throws FindException 게시글이 없거나 DB와의 연결이 실패하면 예외 발생한다
	 */
	public Integer selectAllCount(Integer teamNo) throws FindException;
	
	/**
	 * 게시글 번호로 게시글을 조회한다
	 * @param teamNo 팀번호
	 * @param QnaNo 게시글 번호
	 * @return 게시글
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
	 * @param qna_no 게시글 번호
	 * @return
	 * @throws RemoveException DB와의 연결 실패 또는 게시물이 없을 경우 예외 발생
	 */
	public Integer delete(Integer teamNo, Integer qna_no) throws ModifyException;
	
	/**
	 * 팀 멤버인지 확인 = 결과가 1이면 멤버임
	 * @param teamMemberDTO
	 * @return
	 * @throws FindException
	 */
	Integer selectTeamMemberStatus(String id, Integer teamNo) throws FindException;

} // end class
