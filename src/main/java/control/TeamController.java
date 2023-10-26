package control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.rank.service.RankServiceImpl;
import com.my.team.dto.SignupTeamDTO;
import com.my.team.service.TeamServiceImpl;


@RestController
public class TeamController {
	
	@Autowired protected TeamServiceImpl service;
	@Autowired protected RankServiceImpl rankservice;

	/* 서현웅니 */
	@GetMapping("/teamnamedupcheck")
	public Map<String, Integer> teamnamedupchk(String teamName){
		Map<String, Integer> map = new HashMap<>();
		try {
				int teamNo = service.teamNameDupChk(teamName);
				//팀이 있는 경우
				map.put("status", 0);
				map.put("teamNo", teamNo);

		} catch (FindException e) {
			//팀이 없는 경우
			if(teamName == "") {
				map.put("status", 2);
			}else {
				map.put("status", 1);				
			}
		}
		System.out.println(map);
		return map;
	}
	
	@GetMapping("/test")
	public String test() {
		String text = "테스트완료";
		System.out.println(text);
		return text;
	}
	
	
	
	
	/* 워니자리 */
	
	
	
	
	
	
	
	/* 셍나 */
	/*
	 /teamjoin=control.TeamJoinController
	/teamleave=control.TeamLeaveController
	/teamattendance=control.TeamAttendanceController
	/teammain=control.TeamMainController
	/teamselectexaminer=control.TeamSelectExaminerController
	/teamdismiss=control.TeamDismissController
	/teamreqaccept=control.TeamReqAcceptController
	 */
	@GetMapping(/teamjoin)
	public List<Map<String,Object>> teamJoin() {
		
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
		
	} // teamJoin()
	
} // end class