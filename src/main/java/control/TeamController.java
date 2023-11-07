package control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
import com.my.team.dto.TeamHashtagDTO;
import com.my.team.service.TeamServiceImpl;
import com.my.util.MainPageGroup;
import com.my.util.PageGroup;

import net.coobird.thumbnailator.Thumbnailator;


@RestController
@RequestMapping("/team")
public class TeamController {

	@Autowired protected TeamServiceImpl service;
	@Autowired protected RankServiceImpl rankservice;

//	@Autowired protected SignupTeamDTO signupTeamDTO;
//	@Autowired protected AttendanceDTO attendanceDTO;
//	@Autowired protected TeamDTO teamDTO;
//	@Autowired protected NoticeDTO noticeDTO;

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
		if(gubun.equals("delete")) {
			Map<String, Object> map = new HashMap<>();
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
			//         HttpSession session = request.getSession();
			Map<String, Object> map = new HashMap<>();
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
		return null;
	}

	@PostMapping("/teammanage")
	public Map<String, Object> teammanage(String gubun, TeamDTO team, List<TeamHashtagDTO> hashlist, MultipartFile f1){

		if(gubun.equals("create")) {
			Map<String, Object> map = new HashMap<>();
			HashMap<String, Object> param = new HashMap<>();

			try {

				param.put("I_TEAM_NAME", team.getTeamName().trim());
				param.put("I_LEADER_ID", team.getLeaderId().trim());
				param.put("I_STUDY_TYPE", team.getStudyType().trim());
				param.put("I_ONOFFLINE", team.getOnOffLine().trim());
				param.put("I_MAX_MEMBER", team.getMaxMember());
				param.put("I_STARTDATE", team.getStartDate().trim());
				param.put("I_ENDDATE", team.getEndDate().trim());
				param.put("I_BRIEF_INFO", team.getBriefInfo().trim());
				param.put("I_TEAM_INFO", team.getTeamInfo().trim());
				for (int i=1; i<=5; i++) {
					param.put("I_HASHTAG_NAME1"+i, hashlist.get(i).getHashtagName());
				}

				service.createTeam(param);


				String teamName = team.getTeamName().trim();
				int teamNo = service.teamNameDupChk(teamName);
				String attachesDir = "C:\\KOSA202307\\attaches"; //첨부경로

				if(f1 != null && f1.getSize() > 0) {
					String targetFileName = teamNo + "_profile.png";
					File targetFile = new File(attachesDir, targetFileName);
					FileCopyUtils.copy(f1.getBytes(), targetFile);

					//----섬네일파일 만들기 START----
					int width=100;
					int height=100;            

					String thumbFileName = teamNo + "_profile_t.png"; //섬네일파일명
					File thumbFile = new File(attachesDir, thumbFileName);
					FileOutputStream thumbnailOS = new FileOutputStream(thumbFile);//출력스트림
					InputStream thumbnailIS = f1.getInputStream(); //첨부파일 입력스트림            
					Thumbnailator.createThumbnail(thumbnailIS, thumbnailOS, width, height);
					//-----섬네일파일 만들기 END------
				}else {
					throw new Exception("프로필 파일이 없습니다");
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
			Map<String, Object> map = new HashMap<>();
			HashMap<String, Object> param = new HashMap<>();

			try {

				service.updateTeam(team);


				service.deleteHashtag(team.getTeamNo());
				for (int i=1; i<=5; i++) {
					param.put("I_HASHTAG_NAME1"+i, hashlist.get(i).getHashtagName());
				}
				service.updateHashtag(param);

				String teamName = team.getTeamName().trim();
				int teamNo = service.teamNameDupChk(teamName);
				String attachesDir = "C:\\KOSA202307\\attaches"; //첨부경로

				if(f1 != null && f1.getSize() > 0) {
					String targetFileName = teamNo + "_profile.png";
					File targetFile = new File(attachesDir, targetFileName);
					FileCopyUtils.copy(f1.getBytes(), targetFile);

					//----섬네일파일 만들기 START----
					int width=100;
					int height=100;            

					String thumbFileName = teamNo + "_profile_t.png"; //섬네일파일명
					File thumbFile = new File(attachesDir, thumbFileName);
					FileOutputStream thumbnailOS = new FileOutputStream(thumbFile);//출력스트림
					InputStream thumbnailIS = f1.getInputStream(); //첨부파일 입력스트림            
					Thumbnailator.createThumbnail(thumbnailIS, thumbnailOS, width, height);
					//-----섬네일파일 만들기 END------
				}else {
					throw new Exception("프로필 파일이 없습니다");
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
		return null;
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
	@GetMapping("/cancelwaiting")
	public Map<String,Object> cancelWaiting(String id, Integer teamNo) {	
		Map<String, Object> map = new HashMap<>();

		try {
			service.cancelWaiting(id, teamNo);
			map.put("status", 1);
			map.put("msg", "승인대기 취소되었습니다");
		} catch (RemoveException e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", e.getMessage());
		}

		return map;
	}
	
	@GetMapping("/myactivity")
	public Map myActivity(String id, Integer teamNo) {
		Map map = new HashMap<>();
		
		try {
			map = service.myActivity(id, teamNo);
			System.out.println(map.get("team"));
			System.out.println(map.get("teammember"));
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@GetMapping("/myteamlist")
	public Map myTeamList(String id, Integer menuStatus, String currentPage) {
		Map map = new HashMap<>();
		int cp = 1;
		if (currentPage != null && !currentPage.equals("")) {
			cp = Integer.parseInt(currentPage);
		}

		try {
			PageGroup<SignupTeamDTO> pg = service.findMyTeam(cp, id, menuStatus);
			return map;
		} catch (FindException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@GetMapping("/rejectcheck")
	public Map<String, Object> rejectCheck(String id, Integer teamNo) {
		Map<String, Object> map = new HashMap<>();
		
		try {
			service.rejectCheck(id, teamNo);
			map.put("status", 1);
		} catch (RemoveException e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", e.getMessage());
		}
		return map;
	}
	
	@GetMapping("/rejectedteam")
	public Map rejectedTeam(String id, String currentPage) {

		Map map = new HashMap<>();
		int cp = 1;
		if (currentPage != null && !currentPage.equals("")) {
			cp = Integer.parseInt(currentPage);
		}

		try {
			PageGroup<SignupTeamDTO> pg = service.findRejectedTeam(cp, id);
			return map;
		} catch (FindException e) {
			e.printStackTrace();
			return null;
		}
	}



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
	public Map teamJoin(int teamNo, String id, String introduction) throws Exception {

		Map<String, Object> map = new HashMap<>();

		try {

			SignupTeamDTO signupTeamDTO = new SignupTeamDTO();

			signupTeamDTO.setTeamNo(teamNo);
			signupTeamDTO.setId(id);
			signupTeamDTO.setIntroduction(introduction);

			int teamMemberStatus = service.selectAllTeammember(teamNo, id);

			if (teamMemberStatus == 2) {
				map.put("status", 2);
				map.put("msg", "해당 팀에서 방출되셨습니다. 팀 재가입이 불가능합니다.");
			} else {

				service.insertSignUpTeam(signupTeamDTO);            	
				//팀 가입시 랭킹 정보도 업데이트하게 만들기
				rankservice.addRankInfo(teamNo, id);

				map.put("status", 1);
				map.put("msg", "팀 가입 요청 성공");
			} // if-else

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

				Date formatDueDate1 = formatter.parse(duedate1);
				Date formatDueDate2 = formatter.parse(duedate2);
				Date formatEndDate = formatter.parse(enddate);

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