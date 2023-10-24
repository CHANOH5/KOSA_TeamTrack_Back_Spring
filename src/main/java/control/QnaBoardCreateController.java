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
import com.my.qna.dto.QnaBoardDTO;
import com.my.util.Attach;

public class QnaBoardCreateController extends QnaController {

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
//		Date regDate = Date.from(Instant.now());

		// 요청 전달데이터 얻기
		// =====================  여기 session 받아와야하니까 마지막에 수정해야함
		HttpSession session = req.getSession();
//		HttpSession session = req.getSession();
		String loginedId = (String) session.getAttribute("loginedId");

		try {
			
			Attach attach=new Attach(req);
			Integer teamNo = Integer.parseInt(attach.getParameter("teamNo"));
			
			System.out.println("teamNo =============> " + teamNo);
			String qnaTitle=attach.getParameter("title");
			String qnaContent=attach.getParameter("content");
//			String loginedId =attach.getParameter("loginedId");
			
			System.out.println("qnaTitle ============> " + qnaTitle);
			System.out.println("qnaContent ============> " + qnaContent);
			System.out.println("logindedId ============> " + loginedId);
			
			QnaBoardDTO dto = new QnaBoardDTO(loginedId, qnaTitle, qnaContent);
			
			service.create(teamNo, dto);
			
			try {
				String originFileName=attach.getFile("file").get(0).getName();
				attach.upload("file", loginedId+"_qnaboard_"+originFileName);
			} catch(Exception e) {
			
			} // try-catch
			
			map.put("status", 1);
			map.put("msg", "게시글이 등록되었습니다.");
			
		} catch (Exception e) {
			
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", e.getMessage());
			
		} // try-catch
		
		// JSON문자열 응답
		String jsonStr = mapper.writeValueAsString(map);
		out.print(jsonStr);

		return null;
	} // execute
} // end class
