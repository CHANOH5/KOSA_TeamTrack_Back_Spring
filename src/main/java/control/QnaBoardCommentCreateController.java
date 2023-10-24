package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.qna.dto.QnaBoardCommentDTO;

public class QnaBoardCommentCreateController extends QnaController {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		// CORS 문제 해결
//		res.setHeader("Access-Control-Allow-Origin", "http://localhost:5500");
//		res.setHeader("Access-Control-Allow-Credentials", "true");
		
		res.setContentType("application/json;charset=utf-8");
		
		// 응답출력스트림 얻기
		PrintWriter out = res.getWriter();
		
		// jackson 라이브러리에서 제공하는 ObjectMapper 클래스 사용하기
		ObjectMapper mapper = new ObjectMapper(); // JSON 문자열 만드는 API
		Map<String, Object> map = new HashMap<>();

		// 요청 전달데이터 얻기
		// =====================  여기 session 받아와야하니까 마지막에 수정해야함
		HttpSession session = req.getSession();
		
		Integer teamNo = Integer.parseInt(req.getParameter("teamNo"));
		Integer qnaNo = Integer.parseInt(req.getParameter("qnaNo"));
		String content = req.getParameter("content");
		String teammemberId = req.getParameter("teammemberId");
		
		String teamNoStr = req.getParameter("teamNo");
		if (teamNoStr != null && !teamNoStr.isEmpty()) {
			try {
				teamNo = Integer.parseInt(teamNoStr);
			} catch (NumberFormatException e) {
				e.printStackTrace();

				return null;
			}
		}
		
		String qnaNoStr = req.getParameter("qnaNo");
		if (teamNoStr != null && !teamNoStr.isEmpty()) {
			try {
				qnaNo = Integer.parseInt(qnaNoStr);
			} catch (NumberFormatException e) {
				e.printStackTrace();

				return null;
			}
		}

		try {

			System.out.println("teammemberId ============> " + teammemberId);
			System.out.println("Content ============> " + content);
//			System.out.println("loginedId ============> " + loginedId);
			
			QnaBoardCommentDTO dto = new QnaBoardCommentDTO();
			
			dto.setQnaNo(qnaNo);
			dto.setContent(content);
			dto.setTeammemberId(teammemberId);

			commentService.insert(teamNo, dto);
			
			map.put("status", 1);
			map.put("msg", "댓글이 작성되었습니다.");

			} catch (Exception e) {
				
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", "회원만 댓글을 작성할 수 있습니다");
			
			}
		
		// JSON문자열 응답
		String jsonStr = mapper.writeValueAsString(map);
		out.print(jsonStr);

		return null;
	} // execute
} // end class
