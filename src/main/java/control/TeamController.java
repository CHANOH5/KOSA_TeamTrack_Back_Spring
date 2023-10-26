package control;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.exception.FindException;
import com.my.rank.service.RankServiceImpl;
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
	
	
	
	
} // end class