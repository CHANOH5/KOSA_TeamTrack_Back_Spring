package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.exception.FindException;
import com.my.task.dto.TaskDTO;

public class MainTaskListController extends TaskController {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json;charset=utf-8");

		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		HttpSession session=request.getSession();
		Integer teamNo=Integer.parseInt(request.getParameter("teamNo"));
//		String option=request.getParameter("option");
		String loginedId=(String)session.getAttribute("loginedId");

		
		//Integer teamNo=9999;
		//String loginedId="cjs1231";

		try {
			List<TaskDTO> list=service.findMainTaskList(teamNo, loginedId);
			String jsonStr = mapper.writeValueAsString(list);
			out.print(jsonStr);
		} catch (FindException e) {
			e.printStackTrace();
		}

		return null;
	}

}
