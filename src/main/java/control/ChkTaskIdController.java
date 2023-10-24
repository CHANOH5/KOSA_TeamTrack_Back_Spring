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

public class ChkTaskIdController extends TaskController {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		HttpSession session=request.getSession();
		String loginedId=(String)session.getAttribute("loginedId");
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		
		Integer teamNo=Integer.parseInt(request.getParameter("teamNo"));
//		String loginedId=request.getParameter("id");
		
		
		//Integer teamNo=9999;
//		String loginedId="cjs1231";
//		String loginedId="ksh0110";
		Map<String, Object> map=new HashMap<>();
		
		try {
			Integer taskNo=service.findTaskId(teamNo, loginedId);
			if(taskNo!=0) {
				map.put("status", 1);
				map.put("loginedId", loginedId);
				map.put("taskNo", taskNo);
			} else {
				map.put("status", 0);
				map.put("msg", "권한이 없습니다");
			}	
		} catch (FindException e) {
			//e.printStackTrace();
			map.put("status", "0");
			map.put("msg", e.getMessage());
		} finally {
			String jsonStr=mapper.writeValueAsString(map);
			out.print(jsonStr);
		}
		
		return null;
	}

}
