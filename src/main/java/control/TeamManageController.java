package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.exception.FindException;
import com.my.exception.RemoveException;
import com.my.team.dto.TeamDTO;
import com.my.team.dto.TeamHashtagDTO;
import com.my.util.Attach;

public class TeamManageController extends TeamController {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");


		String gubun = null;
		try {
			Attach attach = new Attach(request);
			gubun = attach.getParameter("gubun");

			if(gubun.equals("create")) {
				PrintWriter out = response.getWriter();
				ObjectMapper mapper = new ObjectMapper();

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
						attach.upload("f1", teamNo + "_profile.png");
					} catch(Exception e) {

					}


					map.put("status", 1);
					map.put("msg", "팀생성 성공");

				} catch (Exception e) {
					e.printStackTrace();
					map.put("status", 0);
					map.put("msg", "팀생성 실패");
				}
				out.print(mapper.writeValueAsString(map));
				return null;
			}

			else if(gubun.equals("update")) {
				PrintWriter out = response.getWriter();
				ObjectMapper mapper = new ObjectMapper();

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
				out.print(mapper.writeValueAsString(map));
				return null;


			}
		}catch (FileUploadException e) {
			e.printStackTrace();
		}return null;
	}
}
