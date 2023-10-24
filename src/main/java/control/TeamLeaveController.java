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
import com.my.exception.RemoveException;

public class TeamLeaveController extends TeamController {

//	팀 나가기용 컨트롤러
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json;charset=utf-8");
//		response.setHeader("Access-Control-Allow-Origin", "http://localhost:5500");

		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		
		Map<String, Object> map = new HashMap<>();
		
		try {
            Integer teamNo = Integer.parseInt(request.getParameter("teamNo"));
            String id = request.getParameter("id");

			// 트랜잭션 메소드
			service.leaveTeam(teamNo, id);

			map.put("status", 1);
			map.put("msg", "팀 나가기 성공");

		} catch (ModifyException | RemoveException e) {
			e.printStackTrace();
			
			map.put("status", 0);
			map.put("msg", "팀 나가기 실패");
		} catch (Exception e) {
			e.printStackTrace();
			
			map.put("status", 0);
			map.put("msg", "팀 나가기 실패");
		} // try-catch

		// JSON문자열 응답
		String jsonStr = mapper.writeValueAsString(map);
		out.print(jsonStr);

		return null;

	} // execute()

} // end class
