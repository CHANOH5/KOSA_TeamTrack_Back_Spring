package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.team.dto.SignupTeamDTO;

public class TeamJoinController extends TeamController {

//	팀 가입용 컨트롤러
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json;charset=utf-8");
//		response.setHeader("Access-Control-Allow-Origin", "http://localhost:5500");

		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		
		Map<String, Object> map = new HashMap<>();
		
		try {

			SignupTeamDTO signupTeamDTO = new SignupTeamDTO();
			
            signupTeamDTO.setTeamNo(Integer.parseInt(request.getParameter("teamNo")));
            signupTeamDTO.setId(request.getParameter("id"));
            signupTeamDTO.setIntroduction(request.getParameter("introduction"));

            Integer teamMemberStatus = service.selectAllTeammember(Integer.parseInt(request.getParameter("teamNo")), request.getParameter("id"));
            
            if (teamMemberStatus == 2) {
            	map.put("status", 2);
            	map.put("msg", "해당 팀에서 방출되셨습니다. 팀 재가입이 불가능합니다.");
            } else {
            	
            	service.insertSignUpTeam(signupTeamDTO);            	
            	//팀 가입시 랭킹 정보도 업데이트하게 만들기
            	Integer teamNo = Integer.parseInt(request.getParameter("teamNo"));
            	String id = request.getParameter("id");
            	rankservice.addRankInfo(teamNo, id);
            	
            	map.put("status", 1);
            	map.put("msg", "팀 가입 요청 성공");
            } // if-else

		} catch (AddException | NumberFormatException | FindException e) {
			e.printStackTrace();
			
			map.put("status", 0);
			map.put("msg", "팀 가입요청 실패");
		} // try-catch

		// JSON문자열 응답
		String jsonStr = mapper.writeValueAsString(map);
		out.print(jsonStr);
		
		System.out.println(map);

		return null;

	} // execute()

} // end class
