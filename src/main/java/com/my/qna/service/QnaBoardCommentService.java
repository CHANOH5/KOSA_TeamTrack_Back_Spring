package com.my.qna.service;

import java.util.List;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.qna.dto.QnaBoardCommentDTO;
import com.my.qna.dto.QnaBoardDTO;
import com.my.util.PageGroup;

public interface QnaBoardCommentService {

	/**
	 * 댓글을 작성한다
	 * @param teamNo 팀번호
	 * @param dto 댓글작성 데이터
	 * @throws AddException 
	 */
	public void insert(Integer teamNo, QnaBoardCommentDTO dto) throws AddException;
	
	public void insertReply (Integer teamNo, QnaBoardCommentDTO dto) throws AddException;
	
	/**
	 * 해당 팀의 qna게시글 번호에 맞는 현재페이지에 있는 댓글을 조회한다
	 * @param teamNo 팀번호
	 * @param qnaNo 게시글 번호
	 * @param currentPage 현재페이지
	 * @return 현재페이지의 댓글
	 * @throws FindException
	 */
	public List<QnaBoardCommentDTO> selectCommentByQnaNo(Integer teamNo, Integer qnaNo) throws FindException;

	/**
	 * 댓글을 채택한다
	 * @param teamNo 팀번호
	 * @param qnaNo 게시글 번호
	 * @param commentNo 댓글 번호
	 * @return 댓글을 채택해서 바뀐 pickkeddate 반환
	 * @throws ModifyException
	 */
	public Integer commentPick(Integer teamNo, Integer qnaNo, Integer commentNo) throws ModifyException;
	
	/**
	 * qna 댓글을 수정한다
	 * @param qnaBoardDTO 게시글 수정 정보
	 * @return
	 * @throws ModifyException DB와의 연결 실패 또는 게시글 수정 실패시 예외 발생
	 */
	public Integer update(Integer teamNo, QnaBoardCommentDTO dto) throws ModifyException;

	/**
	 * qna 댓글을삭제한다
	 * @param teamNo
	 * @param qna_no 게시글 번호
	 * @return
	 * @throws ModifyException  DB와의 연결 실패 또는 게시물이 없을 경우 예외 발생
	 */
	public Integer delete(Integer teamNo, Integer qnaNo, Integer commentNo) throws ModifyException;
	
} // end interface
