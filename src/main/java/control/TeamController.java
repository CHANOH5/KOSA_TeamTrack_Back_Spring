package control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
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
import com.my.exception.RemoveException;
import com.my.rank.service.RankServiceImpl;
import com.my.team.dto.SignupTeamDTO;
import com.my.team.dto.TeamDTO;
import com.my.team.dto.TeamHashtagDTO;
import com.my.team.service.TeamServiceImpl;
import com.my.util.MainPageGroup;

import net.coobird.thumbnailator.Thumbnailator;


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