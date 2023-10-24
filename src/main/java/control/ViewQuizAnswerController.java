package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.exception.FindException;

public class ViewQuizAnswerController extends TaskController {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
//		HttpSession session=request.getSession();
		
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		
		Integer teamNo=Integer.parseInt(request.getParameter("teamNo"));
		Integer taskNo=Integer.parseInt(request.getParameter("taskNo"));
		System.out.println(taskNo);
//		String loginedId=(String)session.getAttribute("loginedId");
		
		//Integer teamNo=9999;
//		Integer taskNo=1;
//		String loginedId="nwh2023";
		Map<String, Object> map=new HashMap<>();
		
		try {
			List<Integer> list=service.findQuizAnswer(teamNo, taskNo);
			if(list.size()==0) {
				map.put("status", 0);
				map.put("msg", "답안이 존재하지 않습니다");
			} else {
				map.put("status", 1);
				map.put("list", list);
			}
		} catch (FindException e) {
			// e.printStackTrace();
			map.put("status", 0);
			map.put("msg", e.getMessage());
		} finally {
			String jsonStr=mapper.writeValueAsString(map);
			out.print(jsonStr);
		}
		
		
		return null;
	}

}
