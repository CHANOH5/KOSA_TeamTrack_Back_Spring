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
import com.my.exception.FindException;
import com.my.notice.dto.NoticeDTO;

public class MyActivityController extends TeamController {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
//		response.setHeader("Access-Control-Allow-Origin", "http://localhost:5500");
		
		String loginedId = request.getParameter("loginedId");
		
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		
		Map map = new HashMap<>();
		Integer teamNo = Integer.parseInt(request.getParameter("teamNo"));
		System.out.println(teamNo);
		
		try {
			map = service.myActivity(loginedId, teamNo);
			System.out.println(map.get("team"));
			System.out.println(map.get("teammember"));
			String jsonStr = mapper.writeValueAsString(map);
			out.print(jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
