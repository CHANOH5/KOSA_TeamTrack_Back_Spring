package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.exception.ModifyException;

public class QnaBoardCommentPickController extends QnaController {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		// CORS 문제 해결
//		res.setHeader("Access-Control-Allow-Origin", "http://localhost:5500");
//		res.setHeader("Access-Control-Allow-Credentials", "true");
//		
		res.setContentType("application/json;charset=utf-8");
		
		// 응답출력스트림 얻기
		PrintWriter out = res.getWriter();
		
		// jackson 라이브러리에서 제공하는 ObjectMapper 클래스 사용하기
		ObjectMapper mapper = new ObjectMapper(); // JSON 문자열 만드는 API
		
		Map<String, Object> map = new HashMap<>();
		
		// 요청 전달데이터 얻기
		Integer teamNo = Integer.parseInt(req.getParameter("teamNo"));
		Integer qnaNo = Integer.parseInt(req.getParameter("qnaNo"));
		Integer commentNo = Integer.parseInt(req.getParameter("commentNo"));
		
		System.out.println("pick teamNo ===================> " + teamNo);
		System.out.println("pick qnaNo ===================> " + qnaNo);
		System.out.println("pick commentNo =================> " + commentNo);
		
		// teamNo 파라미터 처리
		String teamNoStr = req.getParameter("teamNo");
		if (teamNoStr != null && !teamNoStr.isEmpty()) {
			try {
				teamNo = Integer.parseInt(teamNoStr);
			} catch (NumberFormatException e) {
				e.printStackTrace();

				return null;
			}
		}
		
		// teamNo 파라미터 처리
		String qnaNoStr = req.getParameter("qnaNo");
		if (teamNoStr != null && !teamNoStr.isEmpty()) {
			try {
				teamNo = Integer.parseInt(teamNoStr);
			} catch (NumberFormatException e) {
				e.printStackTrace();

				return null;
			}
		}
		
		// teamNo 파라미터 처리
		String commentNoStr = req.getParameter("teamNo");
		if (teamNoStr != null && !teamNoStr.isEmpty()) {
			try {
				teamNo = Integer.parseInt(teamNoStr);
			} catch (NumberFormatException e) {
				e.printStackTrace();

				return null;
			}
		}
		
		try {
			commentService.commentPick(teamNo, qnaNo, commentNo);
			
			map.put("status", 1);
			map.put("msg", "채택되었습니다.");
			
		} catch (ModifyException e) {

			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", e.getMessage());
			
		} // try-catch

		// JSON문자열 응답
		String jsonStr = mapper.writeValueAsString(map);
		out.print(jsonStr);
		
		
		return null;
		
	} // execute   

} // QnaBoardCommentPickController
