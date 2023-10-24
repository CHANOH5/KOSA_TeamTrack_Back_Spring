package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.exception.FindException;
import com.my.team.dto.TeamDTO;
import com.my.team.dto.TeamHashtagDTO;
import com.my.util.MainPageGroup;

public class TeamFilterController extends TeamController {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		
		String column = request.getParameter("column");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String currentPage = request.getParameter("currentPage");

		String curTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		if(startDate == "") {
			startDate = curTime;
		}
		if(endDate =="") {
			endDate = "2050-12-31";
		}
		
		int cp = 1;
		if(currentPage != null && !currentPage.equals("")) {
			cp = Integer.parseInt(currentPage);
		}
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<>();
		
			PrintWriter out = response.getWriter();
			try {
				MainPageGroup<TeamDTO> pg = service.selectByDate(cp, column, startDate, endDate);
				List<TeamHashtagDTO> hashlist = new ArrayList<>();
				for(TeamDTO team : pg.getList()){
					hashlist.addAll(service.selectTeamHashtag(team.getTeamNo()));
					System.out.println(team.getTeamNo());
					System.out.println("해시태그" + service.selectTeamHashtag(team.getTeamNo()));
				}
				map.put("status", 1);
				map.put("list", pg.getList());
				map.put("hashlist", hashlist);
				map.put("startPage", pg.getStartPage());
				map.put("endPage", pg.getEndPage());
				System.out.println(map);
			}catch (FindException e) {
				e.printStackTrace();
				map.put("status", 0);
				map.put("msg", e.getMessage());
			}out.print(mapper.writeValueAsString(map));
		
		return null;
	}

}