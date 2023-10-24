package com.my.qna.service;

import java.util.List;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.qna.dao.QnaBoardCommentDAO;
import com.my.qna.dao.QnaBoardCommentDAOImpl;
import com.my.qna.dto.QnaBoardCommentDTO;
import com.my.util.PageGroup;

public class QnaBoardCommentServiceImpl implements QnaBoardCommentService {
	
	private QnaBoardCommentDAO qnaBoardCommentDAO;

	private static QnaBoardCommentServiceImpl  service = new QnaBoardCommentServiceImpl();

	private QnaBoardCommentServiceImpl() {
		qnaBoardCommentDAO = new QnaBoardCommentDAOImpl();
	}

	public static QnaBoardCommentServiceImpl getInstance() {
		return service;
	}

	@Override
	public void insert(Integer teamNo, QnaBoardCommentDTO dto) throws AddException {
		
		qnaBoardCommentDAO.insertComment(teamNo, dto);
		
	} // insert

	@Override
	public void insertReply(Integer teamNo, QnaBoardCommentDTO dto) throws AddException {
		
		qnaBoardCommentDAO.insertReplyComment(teamNo, dto);
		
	} // insertReply

	@Override
	public List<QnaBoardCommentDTO> selectCommentByQnaNo(Integer teamNo, Integer qnaNo) throws FindException {

		System.out.println("service teamNO ============> " + teamNo);

		List<QnaBoardCommentDTO> list = qnaBoardCommentDAO.selectCommentByQnaNo(teamNo, qnaNo);
		return list;
	
	} // selectCommentByQnaNo
	
	@Override
	public Integer commentPick(Integer teamNo, Integer qnaNo, Integer commentNo) throws ModifyException {

		int result = qnaBoardCommentDAO.commentPick(teamNo, qnaNo, commentNo);
		
		return result;
	
	} // commentPick

	@Override
	public Integer update(Integer teamNo, QnaBoardCommentDTO dto) throws ModifyException {
	
		qnaBoardCommentDAO.update(teamNo, dto);
		
		return null;
	}
	
	@Override
	public Integer delete(Integer teamNo, Integer qnaNo, Integer commentNo) throws ModifyException {
	
		qnaBoardCommentDAO.delete(teamNo, qnaNo, commentNo);
		
		return null;
	}
	
	// ================= selectCommentByQnaNo 메서드 테스트 ===================

	public static void main(String[] args) {

//	    QnaBoardCommentDAO dao = new QnaBoardCommentDAOImpl();
//		
//		// 팀 번호와 QnA 번호를 지정합니다.
//	    Integer teamNo = 9999;
//	    Integer qnaNo = 64;
//	    int currentPage = 1;
//
//        try {
//
//            PageGroup<QnaBoardCommentDTO> result = service.selectCommentByQnaNo(teamNo, qnaNo, currentPage);
//
//            // 페이지 정보 출력
//            System.out.println("=======> Total Pages: " + result.getTotalPage());
//            System.out.println("=======> Current Page: " + result.getCurrentPage());
//            System.out.println("=======> Total Count: " + result.getTotalCnt());
//
//        } catch (FindException e) {
//            e.printStackTrace();
//        } 

		
		// =================== commentPick 메서드 테스트 =============================
		
//	    QnaBoardCommentService service = QnaBoardCommentServiceImpl.getInstance();
//
//	    Integer teamNo = 64;
//	    Integer qnaNo = 112;
//	    Integer commentNo = 4;
//
//	    try {
//	        Integer result = service.commentPick(teamNo, qnaNo, commentNo);
//
//	        if (result > 0) {
//	            System.out.println("댓글 채택 성공!");
//	        } else {
//	            System.out.println("댓글 채택 실패!");
//	        }
//	    } catch (ModifyException e) {
//	        e.printStackTrace();
//	    }
	    
	    // ================================= selectCommentReply메서드 테스트  ======================================

//	    QnaBoardCommentService service = new QnaBoardCommentServiceImpl();
//
//	    QnaBoardCommentDTO dto = new QnaBoardCommentDTO();
//	    
//	    Integer teamNo = 64;
//	    dto.setQnaNo(112);
//	    dto.setCommentNo(1);
//	    dto.setCommentGroup(1);
//
//	    try {
//
//	        List<QnaBoardCommentDTO> commentReplyList = service.selectCommentReply(teamNo, dto);
//
//            System.out.println("댓글 조회 성공");
//            for (QnaBoardCommentDTO commentList : commentReplyList) {
//            	
//                System.out.println("commentNo: " + commentList.getCommentNo());
//                System.out.println("content: " + commentList.getContent());
//                
//            }
//
//	    } catch (FindException e) {
//	        e.printStackTrace();
//	        System.out.println("댓글 조회 실패");
//	    }
		
	} // main(test)

} // end class
