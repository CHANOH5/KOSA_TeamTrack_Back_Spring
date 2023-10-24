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
import com.my.exception.ModifyException;

public class ReviewTaskController extends TaskController {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map=new HashMap<>();
		HttpSession session=request.getSession();
		String loginedId=(String)session.getAttribute("loginedId");
		//Integer teamNo=9999;
		Integer teamNo=Integer.parseInt(request.getParameter("teamNo"));
		Integer taskNo=Integer.parseInt(request.getParameter("taskNo"));
		int reviewScore=Integer.parseInt(request.getParameter("reviewScore"));
		//String loginedId=request.getParameter("id");
		try {
			service.setReviewScore(teamNo, taskNo, loginedId, reviewScore);
			service.setAvgReviewScore(teamNo, taskNo);
			
			map.put("status", 1);
			map.put("msg", "평가 완료!");
		} catch (ModifyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", "평가 실패");
		}
		
		out.print(mapper.writeValueAsString(map));
		return null;
	}

}
