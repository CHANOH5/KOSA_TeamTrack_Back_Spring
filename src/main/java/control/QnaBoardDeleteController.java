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

public class QnaBoardDeleteController extends QnaController {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		res.setContentType("application/json;charset=utf-8");
		
//		res.setHeader("Access-Control-Allow-Origin", "http://localhost:5500");
//		res.setHeader("Access-Control-Allow-Credentials", "true");
		
		HttpSession session = req.getSession();
		String loginedId = (String)session.getAttribute("loginedId");
		
		PrintWriter out = res.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		
		Map<String, Object> map = new HashMap<>();
		

		try {
			
			Integer teamNo = Integer.parseInt(req.getParameter("teamNo"));
			Integer qnaNo = Integer.parseInt(req.getParameter("qnaNo"));

			service.delete(teamNo, qnaNo);

			map.put("status", 1);
			map.put("msg", "게시글이 삭제되었습니다");
			
		} catch (Exception e) {
			
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", e.getMessage());
			
		} // try-catch

		out.print(mapper.writeValueAsString(map));

		return null;
	} // execute

} // end class
