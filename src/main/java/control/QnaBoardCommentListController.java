package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.exception.FindException;
import com.my.qna.dto.QnaBoardCommentDTO;

public class QnaBoardCommentListController extends QnaController {

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
		
		// 메인에서 실행하는 모든 서비스 메소드들의 결과값을 map에 넣어서 리턴하기
		Map<String, Object> methodMap = new HashMap<>();
		
		// 요청 전달데이터 얻기
		Integer teamNo = Integer.parseInt(req.getParameter("teamNo"));
		Integer qnaNo = Integer.parseInt(req.getParameter("qnaNo"));

		System.out.println("comment teamNo ===================> " + teamNo);
		System.out.println("comment qnaNo ===================> " + qnaNo);

		
		try {
			
			List<QnaBoardCommentDTO> list = commentService.selectCommentByQnaNo(teamNo, qnaNo);

			Map<String,Object> map = new HashMap<>();
			map.put("list", list);
			map.put("status", 1);
			String jsonStr = mapper.writeValueAsString(map);
			out.print(jsonStr);
			
			System.out.println("==========> jsonStr 출력" + jsonStr);
			
		} catch (FindException e) {
			
			e.printStackTrace();	
			Map<String,Object> map = new HashMap<>();
			map.put("msg", "댓글조회 실패");
			map.put("status", 0);
			String jsonStr = mapper.writeValueAsString(map);
			out.print(jsonStr);
			
		} // try-catch
		
		return null;
		
	} // execute

} // end class
