package control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.exception.FindException;
import com.my.task.dto.TaskDTO;
import com.my.util.PageGroup;

public class AllTaskListController extends TaskController {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");

		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();

		Integer teamNo=Integer.parseInt(request.getParameter("teamNo"));
		String currentPage = request.getParameter("currentPage");
//		System.out.println(currentPage);
		//int option=Integer.parseInt(request.getParameter("desc"));
		//System.out.println(option);
//		boolean desc=true;
//		if(option==2) desc=false;
		int cp = 1;
		if (currentPage != null && !currentPage.equals("")) {
			cp = Integer.parseInt(currentPage);
		}

//		Integer teamNo=9999;
//		int cp=1;
		//boolean desc=true;

		try {
			PageGroup<TaskDTO> pg = service.findAllTaskList(teamNo, cp, true);
			String jsonStr = mapper.writeValueAsString(pg);
			out.print(jsonStr);
		} catch (FindException e) {
			e.printStackTrace();
		}

		return null;
	}

}
