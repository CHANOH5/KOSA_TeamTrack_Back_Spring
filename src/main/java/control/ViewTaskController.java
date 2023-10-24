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
import com.my.task.dto.TaskDTO;
import com.my.team.service.TeamServiceImpl;

public class ViewTaskController extends TaskController {
	protected TeamServiceImpl teamService;

	public ViewTaskController() {
		teamService = TeamServiceImpl.getInstance();
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		HttpSession session=request.getSession();

		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();

		Integer teamNo=Integer.parseInt(request.getParameter("teamNo"));
		Integer taskNo=Integer.parseInt(request.getParameter("taskNo"));
		System.out.println(taskNo);
		String loginedId=(String)session.getAttribute("loginedId");
		Map<String, Object> map=new HashMap<>();
		
		try {
			String role=teamService.determineUserRole(loginedId, teamNo);
			if(role.equals("customer")) throw new Exception();
			map.put("role", "member");
		} catch (Exception e) {
			map.put("role", "customer");
			e.printStackTrace();
		}
		
		try {
			int answerCnt=service.findAnswerCount(teamNo, taskNo);
			map.put("answerCnt", answerCnt);
		} catch (FindException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			map.put("status", 0);
			map.put("msg", e.getMessage());
		}

		try {
			TaskDTO taskinfo=service.findTaskInfo(teamNo, taskNo);
			if(taskinfo==null) {
				map.put("status", 0);
				map.put("msg", "과제를 찾을 수 없습니다");
			} else {
				map.put("status", 1);
				map.put("title", taskinfo.getTitle());
				map.put("nickname", taskinfo.getNickname());
			}
		} catch (FindException e) {
			// e.printStackTrace();
			map.put("status", 0);
			map.put("msg", e.getMessage());
		}

		out.print(mapper.writeValueAsString(map));



		return null;
	}

}
