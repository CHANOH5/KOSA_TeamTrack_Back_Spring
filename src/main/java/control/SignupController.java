package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.customer.dto.CustomerDTO;
import com.my.exception.AddException;
import com.my.util.Attach;

public class SignupController extends CustomerController {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		// CORS 문제 해결
//		res.setHeader("Access-Control-Allow-Origin", "http://localhost:5500");
////		res.setHeader("Access-Control-Allow-Origin", "http://192.168.1.105:5500");
//		res.setHeader("Access-Control-Allow-Credentials", "true");

		res.setContentType("application/json;charset=utf-8");

		// 응답 출력스트림 얻기
		PrintWriter out = res.getWriter();

		// jackson 라이브러리에서 제공하는 ObjectMapper 클래스 사용하기
		ObjectMapper mapper = new ObjectMapper(); // JSON 문자열 만드는 API

		Map<String, Object> map = new HashMap<>();

		try {

			Attach attach = new Attach(req);

			String id = attach.getParameter("id");
			String pwd = attach.getParameter("pwd");
			String nickname = attach.getParameter("nickname");
			String name = attach.getParameter("name");
			String birthday = attach.getParameter("birthday");
			String phone = attach.getParameter("phone");
			String email = attach.getParameter("email");

			// 요청전달 데이터 담을 dto 객체 생성
			CustomerDTO dto = new CustomerDTO(id, pwd, nickname, name, birthday, phone, email, null);

			service.singup(dto);

			try {
				String originProfileFileName = attach.getFile("file1").get(0).getName();
				attach.upload("file1", id + "_userprofile_" + originProfileFileName);
			}catch(Exception e) {

			} // try-catch

			map.put("status", 1);
			map.put("msg", name +"회원님 환영합니다");

		} catch (AddException | FileUploadException e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", "회원가입 실패");
		} // try-catch

		// JSON문자열 응답
		String jsonStr = mapper.writeValueAsString(map);
		out.print(jsonStr);

		return null;

	} // execute

} // end class
