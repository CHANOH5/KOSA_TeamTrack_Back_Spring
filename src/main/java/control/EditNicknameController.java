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
import com.my.notice.dto.NoticeDTO;
import com.my.util.Attach;

public class EditNicknameController extends CustomerController{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		
		HttpSession session = request.getSession();
		
		String loginedId = request.getParameter("loginedId");		
		
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		
		String nickname = request.getParameter("nickname");
		System.out.println(nickname);
		
		Map<String, Object> map = new HashMap<>();; 
		
		try {
			service.modifyNickname(loginedId, nickname);
			map.put("status", 1);
			map.put("msg", "닉네임이 수정되었습니다");
			session.setAttribute("nickname",nickname);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", e.getMessage());
		}
		
		out.print(mapper.writeValueAsString(map));

		return null;
	}
}
