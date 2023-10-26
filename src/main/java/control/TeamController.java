package control;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.RemoveException;
import com.my.notice.dto.NoticeDTO;
import com.my.rank.service.RankServiceImpl;
import com.my.team.dto.AttendanceDTO;
import com.my.team.dto.SignupTeamDTO;
import com.my.team.dto.TeamDTO;
import com.my.team.dto.TeamHashtagDTO;
import com.my.team.service.TeamServiceImpl;
import com.my.util.Attach;
import com.my.util.MainPageGroup;


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


	@GetMapping("/teamhashtag")
	public Map<String, Object> teamhashtag(String currentPage, String data){
		int cp = 1;
		if(currentPage != null && !currentPage.equals("")) {
			cp = Integer.parseInt(currentPage);
		}
		Map<String, Object> map = new HashMap<>();

		try {
			MainPageGroup<TeamDTO> pg = service.selectHashtag(cp, data);
			System.out.println(pg);
			List<TeamHashtagDTO> hashlist = new ArrayList<>();
			for(TeamDTO team : pg.getList()){
				hashlist.addAll(service.selectTeamHashtag(team.getTeamNo()));
				System.out.println(team.getTeamNo());
				System.out.println("해시태그" + service.selectTeamHashtag(team.getTeamNo()));
			}
			map.put("status", 1);
			map.put("list", pg.getList());
			map.put("hashlist", hashlist);
			map.put("startPage", pg.getStartPage());
			map.put("endPage", pg.getEndPage());
			System.out.println(map);
		}catch (FindException e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", e.getMessage());
		}

		return map;
	}


	@GetMapping("/teamsearch")
	public Map<String, Object> teamsearch(String table, String column,
			String currentPage, String data){
		int cp = 1;
		if(currentPage != null && !currentPage.equals("")) {
			cp = Integer.parseInt(currentPage);
		}
		Map<String, Object> map = new HashMap<>();

		try {
			MainPageGroup<TeamDTO> pg = service.selectByData(cp, table, column, data);
			List<TeamHashtagDTO> hashlist = new ArrayList<>();
			for(TeamDTO team : pg.getList()){
				hashlist.addAll(service.selectTeamHashtag(team.getTeamNo()));
				System.out.println(team.getTeamNo());
				System.out.println("해시태그" + service.selectTeamHashtag(team.getTeamNo()));
			}
			map.put("status", 1);
			map.put("list", pg.getList());
			map.put("hashlist", hashlist);
			map.put("startPage", pg.getStartPage());
			map.put("endPage", pg.getEndPage());
			System.out.println(map);
		}catch (FindException e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", e.getMessage());
		}
		return map;
	}


	@GetMapping("/teamfilter")
	public Map<String, Object> teamfilter(String column, String startDate,
			String endDate, String currentPage){

		String curTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		if(startDate == "") {
			startDate = curTime;
		}
		if(endDate =="") {
			endDate = "2050-12-31";
		}

		int cp = 1;
		if(currentPage != null && !currentPage.equals("")) {
			cp = Integer.parseInt(currentPage);
		}
		Map<String, Object> map = new HashMap<>();

		try {
			MainPageGroup<TeamDTO> pg = service.selectByDate(cp, column, startDate, endDate);
			List<TeamHashtagDTO> hashlist = new ArrayList<>();
			for(TeamDTO team : pg.getList()){
				hashlist.addAll(service.selectTeamHashtag(team.getTeamNo()));
				System.out.println(team.getTeamNo());
				System.out.println("해시태그" + service.selectTeamHashtag(team.getTeamNo()));
			}
			map.put("status", 1);
			map.put("list", pg.getList());
			map.put("hashlist", hashlist);
			map.put("startPage", pg.getStartPage());
			map.put("endPage", pg.getEndPage());
			System.out.println(map);
		}catch (FindException e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", e.getMessage());
		}
		return map;
	}


	@GetMapping("/teamdelete")
	public Map<String, Object> teamdelete(String gubun, int teamNo){
		Map<String, Object> map = new HashMap<>();
		if(gubun.equals("delete")) {
			try {
				service.deleteTeam(teamNo);
				//성공적으로 삭제한 경우
				map.put("status", 0);
			} catch (RemoveException e) {
				//삭제하지 못한 경우
				e.printStackTrace();
				map.put("status", 1);
			}
			System.out.println(map);
			return map;

		}else if(gubun.equals("select")) {

			ObjectMapper mapper = new ObjectMapper();
			//			HttpSession session = request.getSession();
			try {
				TeamDTO team = new TeamDTO();
				team = service.selectByTeamNo(teamNo);
				List<TeamHashtagDTO> hashlist = new ArrayList<>();
				hashlist.addAll(service.selectTeamHashtag(teamNo));

				//팀이 있는 경우
				map.put("status", 0);
				map.put("team", team);
				map.put("hashtag", hashlist);
			} catch (FindException e) {
				//팀이 없는 경우
				e.printStackTrace();
				map.put("status", 1);
			}
			System.out.println(map);
			return map;
		}
		return map;
	}
	
	@PostMapping("/teammanage")
	public Map<String, Object> teammanage(Attach attach){
		Map<String, Object> map = new HashMap<>();
		try {
			String gubun = attach.getParameter("gubun");

			if(gubun.equals("create")) {
				HashMap<String, Object> param = new HashMap<>();

				try {

					param.put("I_TEAM_NAME", attach.getParameter("teamName").trim());
					param.put("I_LEADER_ID", attach.getParameter("leaderId").trim());
					param.put("I_STUDY_TYPE", attach.getParameter("studyType").trim());
					param.put("I_ONOFFLINE", attach.getParameter("onOffLine").trim());
					param.put("I_MAX_MEMBER", attach.getParameter("maxMember").trim());
					param.put("I_STARTDATE", attach.getParameter("startDate").trim());
					param.put("I_ENDDATE", attach.getParameter("endDate").trim());
					param.put("I_BRIEF_INFO", attach.getParameter("briefInfo").trim());
					param.put("I_TEAM_INFO", attach.getParameter("teamInfo").trim());
					param.put("I_HASHTAG_NAME1", attach.getParameter("hashtag1").trim());
					param.put("I_HASHTAG_NAME2", attach.getParameter("hashtag2").trim());
					param.put("I_HASHTAG_NAME3", attach.getParameter("hashtag3").trim());
					param.put("I_HASHTAG_NAME4", attach.getParameter("hashtag4").trim());
					param.put("I_HASHTAG_NAME5", attach.getParameter("hashtag5").trim());

					service.createTeam(param);

					try {
						String teamName = attach.getParameter("teamName").trim();
						int teamNo = service.teamNameDupChk(teamName);
						String originFileName=attach.getFile("f1").get(0).getName();
						String format = originFileName.substring(originFileName.lastIndexOf(".")+1);
						System.out.println(format);
						attach.upload("f1", teamNo + "_profile_t.png");
					} catch(Exception e) {

					}


					map.put("status", 1);
					map.put("msg", "팀생성 성공");

				} catch (Exception e) {
					e.printStackTrace();
					map.put("status", 0);
					map.put("msg", "팀생성 실패");
				}
				return map;
			}

			else if(gubun.equals("update")) {
				HashMap<String, Object> param = new HashMap<>();

				try {

					String teamName = attach.getParameter("teamName").trim();
					int teamNo = Integer.parseInt(attach.getParameter("teamNo").trim());
					String studyType = attach.getParameter("studyType").trim();
					String onOffLine = attach.getParameter("onOffLine").trim();
					int maxMember = Integer.parseInt(attach.getParameter("maxMember").trim());
					String startDate = attach.getParameter("startDate").trim();
					String endDate = attach.getParameter("endDate").trim();
					String briefInfo = attach.getParameter("briefInfo").trim();
					String teamInfo = attach.getParameter("teamInfo").trim();
					String hashtag1 = attach.getParameter("hashtag1").trim();
					String hashtag2 = attach.getParameter("hashtag2").trim();
					String hashtag3 = attach.getParameter("hashtag3").trim();
					String hashtag4 = attach.getParameter("hashtag4").trim();
					String hashtag5 = attach.getParameter("hashtag5").trim();


					TeamDTO t = new TeamDTO();

					t.setTeamNo(teamNo);
					t.setTeamName(teamName);
					t.setStudyType(studyType);
					t.setOnOffLine(onOffLine);
					t.setMaxMember(maxMember);
					t.setStartDate(startDate);
					t.setEndDate(endDate);
					t.setBriefInfo(briefInfo);
					t.setTeamInfo(teamInfo);
					System.out.println(t);


					List<Map> list = new ArrayList<>();
					if(hashtag1 != null) {
						Map<String, Object> teamHashtag1 = new HashMap<>();
						teamHashtag1.put("teamNo", teamNo);
						teamHashtag1.put("hashtag" , hashtag1);
						list.add(teamHashtag1);
					}
					if(hashtag2 != null) {
						Map<String, Object> teamHashtag2 = new HashMap<>();
						teamHashtag2.put("teamNo", teamNo);
						teamHashtag2.put("hashtag" , hashtag2);
						list.add(teamHashtag2);
					}
					if(hashtag3 != null) {
						Map<String, Object> teamHashtag3 = new HashMap<>();
						teamHashtag3.put("teamNo", teamNo);
						teamHashtag3.put("hashtag" , hashtag3);
						list.add(teamHashtag3);
					}
					if(hashtag4 != null) {
						Map<String, Object> teamHashtag4 = new HashMap<>();
						teamHashtag4.put("teamNo", teamNo);
						teamHashtag4.put("hashtag" , hashtag4);
						list.add(teamHashtag4);
					}
					if(hashtag5 != null) {
						Map<String, Object> teamHashtag5 = new HashMap<>();
						teamHashtag5.put("teamNo", teamNo);
						teamHashtag5.put("hashtag" , hashtag5);
						list.add(teamHashtag5);
					}
					HashMap<String, Object> params = new HashMap<>();
					params.put("list", list);
					System.out.println(params);
					service.updateTeam(t);
					if(!(hashtag1 == null & hashtag2 == null & hashtag3 == null
							& hashtag4 == null & hashtag5 == null)) {
						service.deleteHashtag(teamNo);
						service.updateHashtag(params);
					}

					try {
						String originFileName=attach.getFile("f1").get(0).getName();
						//attach.upload("f1", teamNo + "_profile"+originFileName);
						String format = originFileName.substring(originFileName.lastIndexOf(".")+1);
						System.out.println(format);
						attach.upload("f1", teamNo + "_profile.png");
					} catch(Exception e) {

					}
					map.put("status", 1);
					map.put("msg", "팀수정 성공");
				} catch (Exception e) {
					e.printStackTrace();
					map.put("status", 0);
					map.put("msg", "팀수정 실패");
				}
				return map;

			}
		}catch (FileUploadException e) {
			e.printStackTrace();
		}
		return map;
	}

	@GetMapping("/main")
	public Map<String, Object> main(String column, String currentPage){
		int cp = 1;
		if(currentPage != null && !currentPage.equals("")) {
			cp = Integer.parseInt(currentPage);
		}
		Map<String, Object> map = new HashMap<>();
		
			try {
				MainPageGroup<TeamDTO> pg = service.findAll(cp, column);
				List<TeamHashtagDTO> hashlist = new ArrayList<>();
				for(TeamDTO team : pg.getList()){
					hashlist.addAll(service.selectTeamHashtag(team.getTeamNo()));
					System.out.println(team.getTeamNo());
					System.out.println("해시태그" + service.selectTeamHashtag(team.getTeamNo()));
				}
				map.put("status", 1);
				map.put("list", pg.getList());
				map.put("hashlist", hashlist);
				map.put("startPage", pg.getStartPage());
				map.put("endPage", pg.getEndPage());
				System.out.println(map);
			}catch (FindException e) {
				e.printStackTrace();
				map.put("status", 0);
				map.put("msg", e.getMessage());
			}
		return map;
	}
	
	



	/* 워니자리 */
	
	
	
	
	/* 셍나 */
	@GetMapping("/teammain")
	public Map teamMain(int teamNo, String id) throws Exception {

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
	public Map teamJoin(int teamNo, String id, String introduction, int teamMemberStatus) throws Exception {
		







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
				rankservice.addRankInfo(teamNo, id);

				map.put("status", 1);
				map.put("msg", "팀 가입 요청 성공");
			} // if-else
			
		} catch (AddException | NumberFormatException e) {

		} catch (AddException | NumberFormatException | FindException e) {
			e.printStackTrace();

			map.put("status", 0);
			map.put("msg", "팀 가입요청 실패");
		} // try-catch
		
		return map;
		
	} // teamJoin()
	
	@GetMapping("/teamleave")
	public Map teamLeave(int teamNo, String id) throws Exception {
		
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
	public Map teamReqAccept(int teamNo, String id, String action) throws Exception {

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
	public Map teamDismiss(int teamNo, String id, String action) throws Exception {

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
	
	@GetMapping("/teamselectexaminer")
	public Map selectExaminer(int teamNo, String id, String action, String duedate1, String duedate2, String enddate) 
			throws Exception {

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
	

		// JSON문자열 응답
		String jsonStr = mapper.writeValueAsString(map);
		out.print(jsonStr);

		System.out.println(map);

		return null;

	} // teamJoin()

} // end class