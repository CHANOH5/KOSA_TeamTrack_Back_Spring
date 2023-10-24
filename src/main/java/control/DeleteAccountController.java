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
import com.my.exception.ModifyException;

public class DeleteAccountController extends CustomerController{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		
		String id = request.getParameter("id");
		Integer status = Integer.parseInt(request.getParameter("status"));
		System.out.println(id);
		System.out.println(status);
		
		HttpSession session = request.getSession();
		
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();

		Map<String, Object> map = new HashMap<>();

		try {
			if(status == 1) {
				service.deleteAccount(id);
				map.put("status", 1);
				map.put("msg", "탈퇴되었습니다");
				session.removeAttribute("loginedId");
				session.invalidate();
			}else {
				map.put("status", 0);
				map.put("msg", "동의가 필요합니다");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", e.getMessage());
		}
		String jsonStr = mapper.writeValueAsString(map);
		out.print(jsonStr);

		return null;
	}
}
