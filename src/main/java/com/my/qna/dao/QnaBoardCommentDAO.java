package com.my.qna.dao;

import java.util.List;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;
import com.my.qna.dto.QnaBoardCommentDTO;
import com.my.qna.dto.QnaBoardDTO;

public interface QnaBoardCommentDAO {

	/**
	 * 댓글을 작성한다
	 * @param teamNo 팀번호
	 * @param dto 댓글 작성 데이터
	 * @throws AddException  DB와의 연결 실패 또는 제약조건에 위반시 예외 발생
	 */
	public void insertComment (Integer teamNo, QnaBoardCommentDTO dto) throws AddException;
	
	/**
	 * 대댓글을 작성한다
	 * @param teamNo 팀 번호
	 * @param dto 대댓글 작성 데이터
	 * @throws AddException DB와의 연결 실패 또는 제약조건에 위반시 예외 발생
	 */
	public void insertReplyComment (Integer teamNo, QnaBoardCommentDTO dto) throws AddException;
	
	/**
	 * 해당 팀의 qna게시글번호에 해당하는 댓글 전체를 불러온다
	 * @param teamNo 팀번호
	
	 * @param endRow 끝행
	 * @return 댓글
	 * @throws FindException DB와의 연결 실패 또는 댓글 없으면 예외 발생
	 */
	public List<QnaBoardCommentDTO> selectCommentByQnaNo(Integer teamNo, Integer qnaNo) throws FindException;
	
	/**
	 * 해당 팀의 qna게시글 번호에 해당하는 댓글의 갯수를 획득
	 * @param teamNo 팀번호
	 * @param qnaNo 댓글 번호
	 * @return 댓글 갯수
	 * @throws FindException DB와의 연결 실패 또는 댓글 없으면 예외 발생
	 */
	public Integer selectAllCount(Integer teamNo, Integer qnaNo) throws FindException;
	
//	/**
//	 * 원댓글 번호에 해당하는 대댓글 전체를 불러온다
//	 * @param teamNo 팀 번호
//	 * @param dto 댓글 데이터 (commentNO - 댓글번호, commentGroup - 원댓글번호)
//	 * @return 대댓글
//	 * @throws FindException DB와의 연결 실패 또는 대댓글 없으면 예외 발생
//	 */
//	public List<QnaBoardCommentDTO> selectCommentReply(Integer teamNo, QnaBoardCommentDTO dto) throws FindException;
	
	/**
	 * 답변을 채택한다
	 * @param teamNo 팀번호
	 * @param qnaNo 게시글 번호
	 * @param commentNo 게시글의 댓글 번호
	 * @return 채택여부
	 * @throws ModifyException DB와의 연결 실패 또는 댓글이 없거나 commentNo값이 일치하지 않으면 예외 발생
	 */
	public Integer commentPick(Integer teamNo, Integer qnaNo, Integer commentNo) throws ModifyException;
	
	/**
	 * qna 게시글의 댓글을 수정한다
	 * @param qnaBoardDTO 게시글 수정 정보
	 * @return
	 * @throws ModifyException DB와의 연결 실패 또는 게시글 수정 실패시 예외 발생
	 */
	public Integer update(Integer teamNo, QnaBoardCommentDTO dto) throws ModifyException;

	/**
	 * qna 게시글의 댓글을 삭제한다
	 * @param qna_no 게시글 번호
	 * @return
	 * @throws RemoveException DB와의 연결 실패 또는 게시물이 없을 경우 예외 발생
	 */
	public Integer delete(Integer teamNo, Integer qnaNo, Integer commetNo) throws ModifyException;

} // end class
