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

public class QnaBoardCommentModifyController extends QnaController {

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
		
		Integer teamNo = Integer.parseInt(req.getParameter("teamNo"));
		Integer qnaNo = Integer.parseInt(req.getParameter("qnaNo"));
		Integer commentNo = Integer.parseInt(req.getParameter("commentNo"));
		String content = req.getParameter("content");
		
		String commentNoStr = req.getParameter("commentNo");
		if (commentNoStr != null && !commentNoStr.isEmpty()) {
			try {
				commentNo = Integer.parseInt(commentNoStr);
			} catch (NumberFormatException e) {
				e.printStackTrace();

				return null;
			}
		}
		try {
			
			QnaBoardCommentDTO dto = new QnaBoardCommentDTO();

			dto.setQnaNo(qnaNo);
			dto.setCommentNo(commentNo);
			dto.setContent(content);
	
			commentService.update(teamNo, dto);

			map.put("status", 1);
			map.put("msg", "댓글이 수정되었습니다");
			
		} catch (Exception e) {
			
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", e.getMessage());
			
		} // try-catch

		out.print(mapper.writeValueAsString(map));

		return null;
		
		
	} // execute

} // end class
