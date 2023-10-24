package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.customer.dto.CustomerDTO;
import com.my.exception.FindException;

public class NicknameDupChkController extends CustomerController{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
//		response.setHeader("Access-Control-Allow-Origin", "http://localhost:5500");
		
		String nickname = request.getParameter("nickname");
		
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		
		Map map = new HashMap<>();

		try {
			service.nicknameDupChk(nickname);
			map.put("status", 0);
		} catch (FindException e) {
			map.put("status", 1);
		}
		
		out.print( mapper.writeValueAsString(map));

		return null;
	}
}
