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

public class PwdCheckController extends CustomerController{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
//		response.setHeader("Access-Control-Allow-Origin", "http://localhost:5500");
//		response.setHeader("Access-Control-Allow-Credentials", "true");
		
		String loginedId = request.getParameter("loginedId");

		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();

		Map<String, Object> map = new HashMap<>();

		String pwd = request.getParameter("pwd");

		try {
			if(service.pwdCheck(loginedId, pwd)) {	
				map.put("status", 1);
				map.put("msg", "비밀번호가 일치합니다");
			}else {
				map.put("status", 0);
				map.put("msg", "비밀번호가 일치하지 않습니다\n다시 입력하세요");
			}
		} catch (FindException e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", e.getMessage());
		}
		String jsonStr = mapper.writeValueAsString(map);
		out.print(jsonStr);

		return null;
	}
}
