package com.my.qna.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.qna.dto.QnaBoardCommentDTO;

public class QnaBoardCommentDAOImpl implements QnaBoardCommentDAO {

	// Mybatis에서 db와 연결하고 sql문을 실행 할 SqlSessionFactory 인터페이스 선언
	private SqlSessionFactory sqlSessionFactory;

	public QnaBoardCommentDAOImpl() {

		// Mybatis 설정파일 로드
		String resource = "com/my/sql/mybatis-config.xml";
		InputStream inputStream;

		try {

			// 리소스 경로에 파일 읽어들이는 클래스(Resources)
			inputStream = Resources.getResourceAsStream(resource);

			// sqlSessionFactory를 멤버변수로 만듦
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

		} catch (IOException e) {
			e.printStackTrace();
		} // try-catch

	} // constructor
	
	@Override
	public void insertComment(Integer teamNo, QnaBoardCommentDTO dto) throws AddException {

		SqlSession session = null;

		try {
			
			Map<String, Object> map = new HashMap<>();
			
			String tableName = "QNACOMMENT_"+ String.valueOf(teamNo);
			
			map.put("tableName", tableName);
			map.put("teamNo", teamNo);
			map.put("qna_no", dto.getQnaNo());
			map.put("content", dto.getContent());
			
			// 이 teammember_id는 sessionId로 가져와야 할거같다.. 수정 필요
			map.put("teammember_id", dto.getTeammemberId());

			
			session = sqlSessionFactory.openSession();
			session.insert("com.my.qna.QnaboardCommentMapper.insertComment", map);
			
			session.commit();

		} catch(Exception e) {
			
			session.rollback();
			e.printStackTrace();
			throw new AddException(e.getMessage());
			
		} finally {
			if(session!=null) {
				session.close();
			}
		} // try-catch-finally

	} // insertComment

	@Override
	public void insertReplyComment(Integer teamNo, QnaBoardCommentDTO dto) throws AddException {
		
		SqlSession session = null;
		
		Map<String, Object> map = new HashMap<>();
		
		try {
			
			String tableName = "QNACOMMENT_"+ String.valueOf(teamNo);
		
			map.put("tableName", tableName);
			map.put("teamNo", teamNo);
			map.put("commentGroup", dto.getCommentGroup());
			map.put("qna_no", dto.getQnaNo());
			map.put("content", dto.getContent());
			
			// 이 teammember_id는 sessionId로 가져와야 할거같다.. 수정 필요
			map.put("teammember_id", dto.getTeammemberId());
			
			String teammemberId = (String) map.get("teammember_id");
			System.out.println("dao teammember_id: " + teammemberId);
			
			session = sqlSessionFactory.openSession();
			session.insert("com.my.qna.QnaboardCommentMapper.insertReplyComment", map);
			
			session.commit();
			
		} catch(Exception e) {
			session.rollback();
			e.printStackTrace();
			throw new AddException(e.getMessage());
		} finally {
			if(session!=null) {
				session.close();
			}
		} // try-catch-finally
		
	} // insertReplyComment

	@Override
	public List<QnaBoardCommentDTO> selectCommentByQnaNo(Integer teamNo, Integer qnaNo)
	//, int startRow, int endRow) 
	throws FindException {
		
		SqlSession session = null;
		
		// 조회한 게시물 저장할 변수 생성 
		List<QnaBoardCommentDTO> qnaCommentList = new ArrayList<>();
		
		Map<String, Object> map = new HashMap<>();

		try {
			
			session = sqlSessionFactory.openSession();
			
			String tableName = "QNACOMMENT_"+ String.valueOf(teamNo);
			
			map.put("tableName", tableName);
			map.put("qnaNo", qnaNo);
//			map.put("start", startRow);
//			map.put("end", endRow);
			
			qnaCommentList = session.selectList("com.my.qna.QnaboardCommentMapper.selectCommentByQnaNo", map);
			
//			QnaBoardCommentDTO item = qnaCommentList.get(0);
//			int qnaNO = item.getQnaNo();
//			System.out.println("qna_No값 확인하기 ============> " + qnaNo);
			
			return qnaCommentList;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			if(session != null) {
				session.close();
			} // if
		} // try-catch-finally
		
	} // end class

	@Override
	public Integer selectAllCount(Integer teamNo, Integer qnaNo) throws FindException {

		SqlSession session = null;
		
		Map<String, Object> map = new HashMap<>();
		
		try {
			
			session = sqlSessionFactory.openSession();
			
			String tableName = "QNACOMMENT_" + teamNo;
			
			map.put("tableName", tableName);
			map.put("qnaNo", qnaNo);
			
			int count = session.selectOne("com.my.qna.QnaboardCommentMapper.selectAllCount", map);
			
			return count;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}  finally {
			if(session != null) {
				session.close();
			} // if
		} // try-catch-finally
		
	} // selectAllCount
	
	
	
	

//	@Override
//	public List<QnaBoardCommentDTO> selectCommentReply(Integer teamNo, QnaBoardCommentDTO dto) throws FindException {
//		
//		SqlSession session = null;
//		
//		// 조회한 게시물 저장할 변수 생성 
//		List<QnaBoardCommentDTO> qnaCommentReplyList = new ArrayList<>();
//		
//		Map<String, Object> map = new HashMap<>();
//		
//		try {
//			
//			session = sqlSessionFactory.openSession();
//			
//			String tableName = "QNACOMMENT_"+ String.valueOf(teamNo);
//			
//			map.put("tableName", tableName);
//			map.put("qnaNo", dto.getQnaNo());
//			map.put("commentNo", dto.getCommentNo());
//			map.put("commentGroup", dto.getCommentGroup());
//			map.put("content", dto.getContent());
//			
//			// 이 teammember_id는 sessionId로 가져와야 할거같다.. 수정 필요
//			map.put("teammember_id", dto.getTeammemberId());
//			
//			qnaCommentReplyList = session.selectList("com.my.qna.QnaboardCommentMapper.selectCommentReply", map);
//			
//			return qnaCommentReplyList;
//			
//			
//		} catch(Exception e) {
//			e.printStackTrace();
//			throw new FindException(e.getMessage());
//		} finally {
//			if(session != null) {
//				session.close();
//			} // if
//		} // try-catch-finally
//		
////		return null;
//	} // selectCommentReply

	@Override
	public Integer commentPick(Integer teamNo, Integer qnaNo, Integer commentNo) throws ModifyException {

		SqlSession session = null;
		
		Map<String, Object> map = new HashMap<>();
		
		try {
			
			session = sqlSessionFactory.openSession();
			
			String tableName = "QNACOMMENT_" + teamNo;
			
			map.put("tableName", tableName);
			map.put("qnaNo", qnaNo);
			map.put("commentNo", commentNo);
			
			int check = session.update("com.my.qna.QnaboardCommentMapper.commentPick", map);
			
			session.commit();
			
			return check;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ModifyException(e.getMessage());
		} finally {
			if(session != null) {
				session.close();
			} // if
		} // try-catch-finally
		
	} // commentPick
	
	

	@Override
	public Integer update(Integer teamNo, QnaBoardCommentDTO dto) throws ModifyException {

		SqlSession session = null;
		
		Map<String, Object> map = new HashMap<>();
		
		try {
			
			String tableName="QNACOMMENT_" + teamNo;
			
			map.put("tableName",tableName);
			map.put("content", dto.getContent());
			map.put("qnaNo", dto.getQnaNo());
			map.put("commentNo", dto.getCommentNo());

			session = sqlSessionFactory.openSession();
			session.update("com.my.qna.QnaboardCommentMapper.update", map);
			
			session.commit();

		} catch(Exception e) {
			session.rollback();
			e.printStackTrace();
			throw new ModifyException(e.getMessage());
		} finally {
			if(session!=null) {
				session.close();
			} // if
		} // try-catch-finally

		return null;

	} // update

	@Override
	public Integer delete(Integer teamNo, Integer qnaNo, Integer commentNo) throws ModifyException {

		SqlSession session = null;
		
		Map<String, Object> map = new HashMap<>();

		try {
			
			String tableName="QNACOMMENT_" + teamNo;
			
			map.put("tableName", tableName);
			map.put("qnaNo", qnaNo);
			map.put("commentNo", commentNo);

			session = sqlSessionFactory.openSession();
			int result = session.delete("com.my.qna.QnaboardCommentMapper.delete", map);
			
			session.commit();

			return result; // 삭제된 행 수 반환
			
		} catch(Exception e) {
			session.rollback();
			e.printStackTrace();
			throw new ModifyException(e.getMessage());
		} finally {
			if(session!=null) {
				session.close();
			}
		} // try-catch-finally

	} // delete
	

	public static void main(String[] args) {
		
		// ============== insert 메서드 테스트 ==========================
		
//		QnaBoardCommentDAO dao = new QnaBoardCommentDAOImpl();
//		
//		QnaBoardCommentDTO dto = new QnaBoardCommentDTO();
//		
//		Integer teamNo = 64;
//		dto.setQnaNo(112);
//		dto.setContent("112번 게시글에 댓글작성 해볼게요1");
//		dto.setTeammemberId("psh2023");
//		
//		try {
//			dao.insertComment(teamNo, dto);
//			
//			System.out.println("댓글작성 성공");
//		} catch (AddException e) {
//			e.printStackTrace();
//			System.out.println("댓글작성 실패");
//		}
		
		// ============== insertReply 메서드 테스트 ==========================
		
//		QnaBoardCommentDAO dao = new QnaBoardCommentDAOImpl();
//		
//		QnaBoardCommentDTO dto = new QnaBoardCommentDTO();
//		
//		Integer teamNo = 64;
//		dto.setQnaNo(112);
//		dto.setCommentGroup(1);
//		dto.setContent("112번 게시글의 CommentNo가1인 댓글에 대댓글작성 해볼게요~~이게 찐임");
//		dto.setTeammemberId("psh2023");
//		
//		try {
//			dao.insertReplyComment(teamNo, dto);
//			
//			System.out.println("댓글작성 성공");
//		} catch (AddException e) {
//			e.printStackTrace();
//			System.out.println("댓글작성 실패");
//		}
		
		// ================== selectCommentReply 메서드 테스트 ======================
		
//	    QnaBoardCommentDAO dao = new QnaBoardCommentDAOImpl();
//
//	    QnaBoardCommentDTO dto = new QnaBoardCommentDTO();
//
//	    Integer teamNo = 64;
//	    dto.setQnaNo(112);
//	    dto.setCommentNo(1);
//	    dto.setCommentGroup(1);
//
//	    try {
//	        List<QnaBoardCommentDTO> commentReplyList = dao.selectCommentReply(teamNo, dto);
//
//	        System.out.println("댓글 조회 성공");
//	        
//	        for (QnaBoardCommentDTO commentList : commentReplyList) {
//	        	
//	            System.out.println("commentNo: " + commentList.getCommentNo());
//	            System.out.println("content: " + commentList.getContent());
//	            
//	        }
//	    } catch (FindException e) {
//	        e.printStackTrace();
//	        System.out.println("댓글 조회 실패");
//	    }
		
		// ============== 해당 게시글의 전체 댓글 메서드 테스트 ==========================
		
//		QnaBoardCommentDAO dao = new QnaBoardCommentDAOImpl();
//		
//	    int teamNo = 64; // 팀 번호 (원하는 팀 번호로 설정)
//	    int qnaNo =  112;
//	    int startPage = 1; // 가져올 페이지 번호 (1페이지)
//	    int endPage = 1;
//	    
//        // selectAll 메서드 호출
//        try {
//			List<QnaBoardCommentDTO> qnaList = dao.selectCommentByQnaNo(teamNo, qnaNo, startPage, endPage);
//			
//	        // 결과 출력
//	        for (QnaBoardCommentDTO qna : qnaList) {
////	            System.out.println("게시글 번호: " + qna.getQna_no());
//	            System.out.println("게시글 제목: " + qna.getContent());
//	            // 필요한 정보들을 출력하거나 활용할 수 있습니다.
//	        }
//			
//		} catch (FindException e) {
//			e.printStackTrace();
//		}
        
        // ===================== 해당 게시글 댓글 총갯수 조회 메서드 =============================
        
        // 팀 번호와 QnA 번호를 지정합니다.
//        Integer teamNo = 9999; // 팀 번호를 원하는 값으로 지정하세요.
//        Integer qnaNo = 64;  // QnA 번호를 원하는 값으로 지정하세요.
//
//        // QnaBoardCommentDAO 객체 생성
//        QnaBoardCommentDAO dao = new QnaBoardCommentDAOImpl();
//
//        try {
//            // selectAllCount 메서드 호출
//            int count = dao.selectAllCount(teamNo, qnaNo);
//
//            // 조회 결과 출력
//            System.out.println("댓글 총 개수: " + count);
//
//            System.out.println("댓글 조회 성공");
//        } catch (FindException e) {
//            e.printStackTrace();
//            System.out.println("댓글 조회 실패");
//        }
//	    
        // ========================= 댓글 채택 기능 메서드 테스트 =========================
        
//	    try {
//
//	        Integer teamNo = 64;
//	        Integer qnaNo = 112;
//	        // 게시글 작성자 아이디를 보내야함
////	        String teammember_id = "test01";
//	        // 게시글의 게시글번호를 가져옴
//	        Integer commentNo = 4;
//
//	        QnaBoardCommentDAO dao = new QnaBoardCommentDAOImpl();
//
//	        Integer result = dao.commentPick(teamNo, qnaNo, commentNo);
//
//	        if (result > 0) {
//	            System.out.println("채택 성공!");
//	        } else {
//	            System.out.println("채택 실패!");
//	        }
//	    } catch (ModifyException e) {
//	        e.printStackTrace();
//	    }

	} // main(test)

} // end class
