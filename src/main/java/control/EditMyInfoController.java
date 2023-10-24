package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.customer.dto.CustomerDTO;
import com.my.notice.dto.NoticeDTO;
import com.my.util.Attach;

public class EditMyInfoController extends CustomerController{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		
		
		String id = URLDecoder.decode(request.getParameter("id"), "UTF-8");
		String name =  URLDecoder.decode(request.getParameter("username"), "UTF-8");
		String birthday =  request.getParameter("birthday");
		String phone =  request.getParameter("phone");
		String email = URLDecoder.decode(request.getParameter("email"));
		
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		
		Map<String, Object> map = new HashMap<>();; 
		
		try {
			
			CustomerDTO customer = new CustomerDTO(name, birthday, phone, email);
			service.modifyMyInfo(id, customer);
			map.put("status", 1);
			map.put("msg", "정보가 수정되었습니다");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", e.getMessage());
		}
		
		out.print(mapper.writeValueAsString(map));

		return null;
	}
}
