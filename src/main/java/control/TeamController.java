package control;

import java.io.PrintWriter;
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
import com.my.rank.service.RankServiceImpl;
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
			//			HttpSession session = request.getSession();
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
	}
	
	@PostMapping("/teammanage")
	public Map<String, Object> teammanage(Attach attach){
		try {
			String gubun = attach.getParameter("gubun");

			if(gubun.equals("create")) {
				Map<String, Object> map = new HashMap<>();
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
				Map<String, Object> map = new HashMap<>();
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