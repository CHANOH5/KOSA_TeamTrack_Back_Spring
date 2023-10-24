package control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.exception.FindException;
import com.my.qna.dto.QnaBoardDTO;

public class QnaBoardDetailController extends QnaController {

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

		Integer teamNo = null;
		Integer qnaNo = null;

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

		// qnaNo 파라미터 처리
		String qnaNoStr = req.getParameter("qnaNo");
		if (qnaNoStr != null && !qnaNoStr.isEmpty()) {
			System.out.println("qnaNoStr ========================> " + qnaNoStr);
			try {
				qnaNo = Integer.parseInt(qnaNoStr);
				System.out.println(qnaNo);
			} catch (NumberFormatException e) {

				e.printStackTrace();

				return null; 
			}
		}

		System.out.println("detail teamNo ====================== >>>>> "  + teamNo);
		System.out.println("detail qnaNo ====================== >>>>> " + qnaNo);
		
		try {
			
			QnaBoardDTO dto = service.selectByQnaNo(teamNo, qnaNo);
			
			String jsonStr = mapper.writeValueAsString(dto);
			out.print(jsonStr);
			
		} catch (FindException e) {
			
			e.printStackTrace();
			
		} // try-catch
		
		return null;
	} // execute

} // end class
