package control;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;
import com.my.notice.dto.NoticeDTO;
import com.my.rank.service.RankServiceImpl;
import com.my.task.dto.TaskDTO;
import com.my.team.dto.AttendanceDTO;
import com.my.team.dto.SignupTeamDTO;
import com.my.team.dto.TeamDTO;
import com.my.team.service.TeamServiceImpl;


@RestController
@RequestMapping("/team")
public class TeamController {
	
	@Autowired protected TeamServiceImpl service;
	@Autowired protected RankServiceImpl rankservice;
	@Autowired protected SignupTeamDTO signupTeamDTO;
	@Autowired protected AttendanceDTO attendanceDTO;
	@Autowired protected TeamDTO teamDTO;
	@Autowired protected NoticeDTO noticeDTO;

	/* 서현웅니 */
	
	
	
	
	
	/* 워니자리 */
	
	
	
	
	/* 셍나 */
	@GetMapping("/main")
	public Map teamMain(int teamNo, String id) {

		Map<String, Object> methodMap = new HashMap<>();
		Map<String, Object> statusMap = new HashMap<>();

		try {

			// 사용자 역할 판별
			String userRole = service.determineUserRole(id, teamNo);
			methodMap.put("userRole", userRole);

			// 팀 정보 다가져오기
			List<TeamDTO> teamList = service.selectAllTeamInfo(teamNo);
			methodMap.put("teamList", teamList.get(0)); // List로 가져오지 말고 그냥 TeamDTO로 가져왓어야 햇는데,,, 8ㅅ8

			// 팀 소개글 가져오기
			String teamInfo = service.selectTeamInfoByTeamNo(teamNo);
			methodMap.put("teamInfo", teamInfo);

			// 공지사항 가져오기
			List<NoticeDTO> noticeList = service.selectNoticeListByTeamNo(teamNo);
			methodMap.put("noticeList", noticeList);

			// 팀 멤버 닉네임 가져오기
			List<String> nicknameList = service.selectNicknameByTeamNo(teamNo);
			methodMap.put("nicknameList", nicknameList);

			// 팀 조회수 카운트 업데이트
			service.updateViewCnt(teamNo);

			// 팀 조회수 가져오기
			int teamViewCnt = service.selectViewCnt(teamNo);
			methodMap.put("teamViewCnt", teamViewCnt);

			statusMap.put("status", 1);
			statusMap.put("msg", "팀 메인 불러오기 성공");

		} catch (Exception e) {
			e.printStackTrace();

			statusMap.put("status", 0);
			statusMap.put("msg", "팀 메인 불러오기 실패");
		} // try-catch
		
		return methodMap;
		
	} // teamMain()
	
	@GetMapping("/teamjoin")
	public Map teamJoin(int teamNo, String id, String introduction, int teamMemberStatus) throws FindException {
		
		Map<String, Object> map = new HashMap<>();
		
		try {
			
			if (teamMemberStatus == 2) {
				map.put("status", 2);
				map.put("msg", "해당 팀에서 방출되셨습니다. 팀 재가입이 불가능합니다.");
			} else {
				
				service.insertSignUpTeam(signupTeamDTO);            	
				rankservice.addRankInfo(teamNo, id);
				
				map.put("status", 1);
				map.put("msg", "팀 가입 요청 성공");
			} // if-else
			
		} catch (AddException | NumberFormatException e) {
			e.printStackTrace();
			
			map.put("status", 0);
			map.put("msg", "팀 가입요청 실패");
		} // try-catch
		
		return map;
		
	} // teamJoin()
	
	@GetMapping("/teamleave")
	public Map teamLeave(int teamNo, String id) throws RemoveException {
		
		Map<String, Object> map = new HashMap<>();
		
		try {

			// 트랜잭션 메소드
			service.leaveTeam(teamNo, id);

			map.put("status", 1);
			map.put("msg", "팀 나가기 성공");

		} catch (ModifyException | RemoveException e) {
			e.printStackTrace();
			
			map.put("status", 0);
			map.put("msg", "팀 나가기 실패");
		} catch (Exception e) {
			e.printStackTrace();
			
			map.put("status", 0);
			map.put("msg", "팀 나가기 실패");
		} // try-catch
		
		return map;

	} // teamLeave()
	
	@GetMapping("/teamattendance")
	public Map teamAttendance(int teamNo, String id, String action) throws Exception {
		
		Map statusMap = new HashMap<>();
		Map methodMap = new HashMap<>();
		Map paramsMap = new HashMap<>();
		Map resultMap = new HashMap<>();
	    
	    paramsMap.put("teamNo", teamNo);
	    paramsMap.put("id", id);
	    
	    try {
	    	if ("attendChk".equals(action)) {
	    	    String existingDate = service.selectAttendanceDate(paramsMap);
	    	    
	    	    if (existingDate != null) {
	    	    	statusMap.put("status", 2);
//	    	    	statusMap.put("msg", "이미 오늘 출석했습니다.");
	    	    } else {
	    	    	statusMap.put("status", 1);
	    	    	statusMap.put("msg", "오늘 출석 가능합니다.");
	    	    } // else-if
	    	    
	    	} else if ("attend".equals(action)) {
	    		String existingDate = service.selectAttendanceDate(paramsMap);
	    		
	    		if (existingDate != null) {
	    	    	statusMap.put("status", 2);
//	    	    	statusMap.put("msg", "이미 오늘 출석했습니다.");
	    	    } else {
	    	        service.increaseAttendanceCnt(paramsMap);
	    	        statusMap.put("status", 1);
	    	        statusMap.put("msg", "팀 출석 성공");
	    	    } // if-else
	    		
	    	} // if-else

	    	// 출석내역확인
	    	List<AttendanceDTO> attendanceList = service.selectAttendanceById(teamNo, id);
	    	methodMap.put("attendanceList", attendanceList);

	    } catch (Exception e) {
	    	e.printStackTrace();
	    	statusMap.put("status", 0);
	    	statusMap.put("msg", "팀 출석 실패");
	    } // try-catch
	    
	    resultMap.put("statusMap", statusMap);
	    resultMap.put("method", methodMap);

		return resultMap;
		
	} // teamAttendance()
	
	@GetMapping("/teamreqaccept")
	public Map teamReqAccept(int teamNo, String id, String action) {

      Map paramsMap = new HashMap<>();
      Map statusMap = new HashMap<>(); 
      Map methodMap = new HashMap<>();

      try {
         
         // 가입 요청자 확인
         List<Map<String, Object>> reqList = service.selectRequestInfo(teamNo);
         methodMap.put("reqList", reqList);
         
         // 가입 승인
         if ("reqApprove".equals(action)) {
            paramsMap.put("teamNo", teamNo);
            paramsMap.put("id", id);
            
            TeamDTO teamDTO = service.selectByTeamNo(teamNo);
            System.out.println(teamDTO);
            
            System.out.println("teamDTO.getJoinMember()= " + teamDTO.getJoinMember());
            System.out.println("teamDTO.getMaxMember()= " + teamDTO.getMaxMember());
            
            if(teamDTO.getJoinMember() < teamDTO.getMaxMember()) { // 현재 팀원이 최대 팀원을 넘지 않은 경우
               service.approveRequest(paramsMap);                 
            } else {                                    // 현재 팀원이 최대 팀원을 초과한 경우
          	  methodMap.put("status", 3);
          	  methodMap.put("msg", "팀 인원이 꽉 찼습니다. 더이상 팀원을 추가할 수 없습니다.");
            } // if-else
            
            statusMap.put("status", 1);
            statusMap.put("msg", "가입 요청 승인 성공");
         } else {
            statusMap.put("status", 0);
            statusMap.put("msg", "가입 요청 승인 실패");
         } // if-else
         
         // 가입 거절
         if ("reqReject".equals(action)) {
             paramsMap.put("teamNo", teamNo);
             paramsMap.put("id", id);
 
             service.updateRequestInfoReject(paramsMap);
             
             statusMap.put("status", 1);
             statusMap.put("msg", "가입 요청 거절 성공");
         } else {
            statusMap.put("status", 0);
            statusMap.put("msg", "가입 요청 거절 실패");
         } // if-else

      } catch (ModifyException e) {
          e.printStackTrace();

          statusMap.put("status", 0);
          statusMap.put("msg", "ModifyException입니다.");
      } catch (Exception e) {
          e.printStackTrace();

          statusMap.put("status", 0);
          statusMap.put("msg", "가입 요청 컨트롤러에서 에러 발생");
      } // try-catch

      return methodMap;
		
	} // teamReqAccept()
	
	@GetMapping("/teamdismiss")
	public Map teamDismiss(int teamNo, String id, String action) {

      Map paramsMap = new HashMap<>();
      Map statusMap = new HashMap<>(); 
      Map methodMap = new HashMap<>();

      try {
      	
      	// 현재 팀원 목록 확인
      	List<Map<String, Object>> currMemberList = service.selectTeamMemberInfo(teamNo);
	    	methodMap.put("currMemberList", currMemberList);
	    	
      	// 방출
      	if ("memberDismiss".equals(action)) {
      		paramsMap.put("teamNo", teamNo);
      		paramsMap.put("id", id);
      		
      		service.dismissTeamMember(paramsMap);
      		
      		statusMap.put("status", 1);
      		statusMap.put("msg", "방출 성공");
      	} else {
      		statusMap.put("status", 0);
      		statusMap.put("msg", "방출 실패");
      	} // if-else

      } catch (ModifyException e) {
          e.printStackTrace();

          statusMap.put("status", 0);
          statusMap.put("msg", "ModifyException입니다.");
      } catch (Exception e) {
          e.printStackTrace();

          statusMap.put("status", 0);
          statusMap.put("msg", "방출 요청 컨트롤러에서 에러 발생");
      } // try-catch
      
      return methodMap;

	} // teamDismiss()
	
	@GetMapping("/selectexaminer")
	public Map selectExaminer(int teamNo, String id, String action, String duedate1, String duedate2, String enddate) {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		Map<String, Object> statusMap = new HashMap<>();
		Map<String, Object> methodMap = new HashMap<>();
		Map resultMap = new HashMap<>();

		try {

			// 출제자 선정을 위한 팀원 목록 보여줌!!!!
			if ("getMember".equals(action)) {
				List<Map<String, Object>> teamInfo = service.selectMemberInfo(teamNo);
				methodMap.put("teamInfo", teamInfo);

				statusMap.put("status", 1);
				statusMap.put("msg", "출제자 선정 성공");
			} else {
				statusMap.put("status", 0);
				statusMap.put("msg", "팀원 목록 조회 실패");
			} // if-else
			
			// 출제자 조회 목록
			if ("getExaminer".equals(action)) {
				List<Map<String, Object>> examinerInfo = service.selectExaminer(teamNo);
				methodMap.put("examinerInfo", examinerInfo);

				statusMap.put("status", 1);
				statusMap.put("msg", "출제자 조회 성공");
			} else {
				statusMap.put("status", 0);
				statusMap.put("msg", "출제자 조회 실패");
			} // if-else

			// 출제자 선정 !!!!
			if ("selectExaminer".equals(action)) {

				TaskDTO taskDTO = new TaskDTO();

//				Date formatDueDate1 = formatter.parse(request.getParameter("duedate1"));
//				Date formatDueDate2 = formatter.parse(request.getParameter("duedate2"));
//				Date formatEndDate = formatter.parse(request.getParameter("enddate"));
				
				Date formatDueDate1 = formatter.parse(duedate1);
				Date formatDueDate2 = formatter.parse(duedate2);
				Date formatEndDate = formatter.parse(enddate);
				
				//                taskDTO.setId(request.getParameter("id"));
				taskDTO.setId(id);
				taskDTO.setDuedate1(formatDueDate1);
				taskDTO.setDuedate2(formatDueDate2);
				taskDTO.setEnddate(formatEndDate);

				service.insertExaminer(taskDTO, teamNo);

				statusMap.put("status", 1);
				statusMap.put("msg", "출제자 선정 성공");
			} else {
				statusMap.put("status", 0);
				statusMap.put("msg", "출제자 선정 실패");
			} // if-else

		} catch (FindException e) {
			e.printStackTrace();
			statusMap.put("status", 0);
			statusMap.put("msg", "팀원 목록 조회 실패: " + e.getMessage());
		} catch (ModifyException e) {
			e.printStackTrace();
			statusMap.put("status", 0);
			statusMap.put("msg", "출제자 선정 실패: " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			statusMap.put("status", 0);
			statusMap.put("msg", "date 타입 파싱 실패: " + e.getMessage());
		} // try-catch

		resultMap.put("statusMap", statusMap);
		resultMap.put("methodMap", methodMap);

		return resultMap;
		
	} // selectExaminer()
	
	
} // end class