package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.exception.FindException;
import com.my.exception.RemoveException;
import com.my.team.dto.TeamDTO;
import com.my.team.dto.TeamHashtagDTO;

public class TeamDeleteController extends TeamController {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");

		String gubun = request.getParameter("gubun");
		if(gubun.equals("delete")) {
			PrintWriter out = response.getWriter();
			ObjectMapper mapper = new ObjectMapper();
			int teamNo = Integer.parseInt(request.getParameter("teamNo"));

			Map<String, Integer> map = new HashMap<>();
			try {
				service.deleteTeam(teamNo);
				//성공적으로 삭제한 경우
				map.put("status", 0);
			} catch (RemoveException e) {
				//삭제하지 못한 경우
				e.printStackTrace();
				map.put("status", 1);
			}
			System.out.println(map);
			out.print(mapper.writeValueAsString(map));
			return null;

		}else if(gubun.equals("select")) {

			PrintWriter out = response.getWriter();
			ObjectMapper mapper = new ObjectMapper();
			//			HttpSession session = request.getSession();
			int teamNo = Integer.parseInt(request.getParameter("teamNo"));
			Map<String, Object> map = new HashMap<>();
			try {
				TeamDTO team = new TeamDTO();
				team = service.selectByTeamNo(teamNo);
				List<TeamHashtagDTO> hashlist = new ArrayList<>();
				hashlist.addAll(service.selectTeamHashtag(teamNo));

				//팀이 있는 경우
				map.put("status", 0);
				map.put("team", team);
				map.put("hashtag", hashlist);
			} catch (FindException e) {
				//팀이 없는 경우
				e.printStackTrace();
				map.put("status", 1);
			}
			System.out.println(map);
			out.print(mapper.writeValueAsString(map));
			return null;

		}return null;
	}
}
