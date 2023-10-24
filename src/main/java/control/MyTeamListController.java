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
import com.my.team.dto.SignupTeamDTO;
import com.my.team.dto.TeamDTO;
import com.my.util.PageGroup;

public class MyTeamListController extends TeamController {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
//		response.setHeader("Access-Control-Allow-Origin", "http://localhost:5500");

		String id = request.getParameter("id");
		
		ObjectMapper mapper = new ObjectMapper();
		PrintWriter out = response.getWriter();
		
		Map map = new HashMap<>();
		
		Integer menuStatus = Integer.parseInt(request.getParameter("menustatus"));
		String currentPage = request.getParameter("currentPage");
		int cp = 1;
		if (currentPage != null && !currentPage.equals("")) {
			cp = Integer.parseInt(currentPage);
		}

		try {
			PageGroup<SignupTeamDTO> pg = service.findMyTeam(cp, id, menuStatus);
			String jsonStr = mapper.writeValueAsString(pg);
			out.print(jsonStr);
		} catch (FindException e) {
			e.printStackTrace();
		}

		return null;
	}

}
