package control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.exception.FindException;
import com.my.task.dto.TaskDTO;
import com.my.util.PageGroup;

public class MyTaskListController extends TaskController {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		HttpSession session=request.getSession();
		String loginedId=(String)session.getAttribute("loginedId");
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
//		HttpSession session=request.getSession();
//
		Integer teamNo=Integer.parseInt(request.getParameter("teamNo"));
//		String loginedId=(String)session.getAttribute("loginedId");
		String currentPage=request.getParameter("currentPage");
		int cp = 1;
		if(currentPage != null && !currentPage.equals("")) {
			cp = Integer.parseInt(currentPage);
		}
//
//		String option=request.getParameter("option");
//		boolean desc=true;
//		if(!option.equals("최신순")) desc=false;

//		Integer teamNo=9999;
		//String loginedId="cjs1231";
//		String loginedId=request.getParameter("id");
		//int cp=1;
		boolean desc=true;

		try {
			if(loginedId==null) throw new FindException("로그인 필요");
 			PageGroup<TaskDTO> pg=service.findMyTaskList(teamNo, loginedId, cp, desc);
 			String jsonStr = mapper.writeValueAsString(pg);
 			out.print(jsonStr);
		} catch (FindException e) {
			e.printStackTrace();
		}

		return null;
	}

}
