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
import com.my.exception.RemoveException;

public class CancelWaitingController extends TeamController{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		response.setHeader("Access-Control-Allow-Origin", "http://localhost:5500");
		
		String loginedId = request.getParameter("id");

		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();

		Map<String, Object> map = new HashMap<>();

		Integer teamNo = Integer.parseInt(request.getParameter("teamNo"));

		try {
			service.cancelWaiting(loginedId, teamNo);
			map.put("status", 1);
			map.put("msg", "승인대기 취소되었습니다");
		} catch (RemoveException e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", e.getMessage());
		}
		String jsonStr = mapper.writeValueAsString(map);
		out.print(jsonStr);

		return null;
	}
}
