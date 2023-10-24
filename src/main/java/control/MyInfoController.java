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
import com.my.customer.dto.CustomerDTO;
import com.my.exception.FindException;


public class MyInfoController extends CustomerController{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
//		response.setHeader("Access-Control-Allow-Origin", "http://localhost:5500");
		
		String loginedId=request.getParameter("loginedId");

		
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();

		try {
			CustomerDTO customer = service.findById(loginedId);
			String jsonStr = mapper.writeValueAsString(customer);
			out.print(jsonStr);
		} catch (FindException e) {
			e.printStackTrace();
		}

		return null;
	}
}
