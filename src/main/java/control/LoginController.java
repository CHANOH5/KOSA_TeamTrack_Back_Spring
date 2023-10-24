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

public class LoginController extends CustomerController {

   @Override
   public String execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		// CORS 문제 해결
//		res.setHeader("Access-Control-Allow-Origin", "http://localhost:5500");
////		res.setHeader("Access-Control-Allow-Origin", "http://localhost:5500");
//		res.setHeader("Access-Control-Allow-Origin", "http://192.168.1.20:5500");
//		res.setHeader("Access-Control-Allow-Credentials", "true");
//
      res.setContentType("application/json; charset=utf-8");

      // 응답 출력스트림 얻기
      PrintWriter out = res.getWriter();

      // 요청 전달데이터 얻기
      String id = req.getParameter("id");
      String pwd = req.getParameter("pwd");

      // jackson 라이브러리에서 제공하는 ObjectMapper 클래스 사용하기
      ObjectMapper mapper = new ObjectMapper(); // JSON 문자열 만드는 API

      Map<String, Object> map = new HashMap<>();

      
      HttpSession session = req.getSession();
      // attribute가 있으면 제거함 
      session.removeAttribute("loginedId");

		try {
			service.login(id, pwd);

			CustomerDTO dto = service.selectNickName(id);
			
			if(dto != null ) {
				String dbPwd = dto.getPwd();

				if(pwd.equals(dbPwd)) {
					int memberstatus = dto.getStatus();
					
					if (memberstatus == 1) {
						map.put("id", id);
						map.put("status", 0);
						map.put("msg", "로그인 되었습니다");
						session.setAttribute("loginedId", id);
					} else {
						map.put("status", 2); // 탈퇴된 회원 상태
						map.put("msg", "탈퇴된 회원입니다");
					}
					if (memberstatus == 1) {
						map.put("id", id);
						session.setAttribute("loginedId", id);
					}
					
					System.out.println("Session ID: " + session.getId());
					System.out.println(session.getAttribute("loginedId"));
					//서현 추가(로그인 시 닉네임도 저장)
					CustomerDTO customerDTO = service.selectNickName(id);
					String nickname = customerDTO.getNickname();
					map.put("nickname", nickname);
					//
				} else {
					map.put("status", 1);
					map.put("msg", "다시 로그인 해주세요.");
				}
			} // if	
	        
				System.out.println(session);
			} catch (FindException e) {
         e.printStackTrace();
         map.put("status", 1);
         map.put("msg", "다시 로그인해주세요.");
      }

		String jsonStr = mapper.writeValueAsString(map);
		out.print(jsonStr);
		
		System.out.println(jsonStr);

      return null;

   } // execute

} // end class