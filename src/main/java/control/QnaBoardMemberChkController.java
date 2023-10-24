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
import com.my.qna.dto.QnaBoardDTO;
import com.my.util.PageGroup;

public class QnaBoardMemberChkController extends QnaController {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		// CORS 문제 해결
//		res.setHeader("Access-Control-Allow-Origin", "http://localhost:5500");
//		res.setHeader("Access-Control-Allow-Credentials", "true");
		
		res.setContentType("application/json;charset=utf-8");
		
		// 응답출력스트림 얻기
		PrintWriter out = res.getWriter();
		
		// jackson 라이브러리에서 제공하는 ObjectMapper 클래스 사용하기
		ObjectMapper mapper = new ObjectMapper(); // JSON 문자열 만드는 API
		
		// 요청 전달데이터 얻기
		HttpSession session=req.getSession();
		String loginedId=(String)session.getAttribute("loginedId");
		Integer teamNo=Integer.parseInt(req.getParameter("teamNo"));
		
		System.out.println("member  = " + loginedId);
		System.out.println("teamNo  = " + teamNo);

		Map<String, Object> map=new HashMap<>();
		
		try {
			
			Integer memberInfo = service.selectTeamMemberStatus(loginedId, teamNo);

			if(memberInfo != 0) {
				map.put("status", 1);
				map.put("memberInfo", memberInfo);
			} else {
				map.put("status", 0);
				map.put("msg", "회원만 게시글 작성 가능합니다");
			}

		} catch (FindException e) {
			e.printStackTrace();	
			map.put("status", "0");
			map.put("msg", e.getMessage());
		} // try-catch
		
		String jsonStr = mapper.writeValueAsString(map);
		out.print(jsonStr);
		
		
		return null;
		
	} // execute

} // end class
