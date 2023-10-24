package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.notice.dto.NoticeDTO;
import com.my.team.dto.TeamDTO;

public class TeamMainController extends TeamController {

	//	팀 메인용 컨트롤러
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json;charset=utf-8");
//		response.setHeader("Access-Control-Allow-Origin", "http://localhost:5500");
		response.setHeader("Access-Control-Allow-Credentials", "true");

		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();

		// 메인에서 실행하는 모든 서비스 메소드들의 결과값을 map에 넣어서 리턴하기
		Map<String, Object> methodMap = new HashMap<>();
		Map<String, Object> statusMap = new HashMap<>();

		int teamNo = Integer.parseInt(request.getParameter("teamNo"));
		String id = request.getParameter("id");

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

		String jsonStr = mapper.writeValueAsString(methodMap);
		out.print(jsonStr);

		return null;
	} // execute()

} // end class

/*
String viewedCookie = "viewedTeam_" + teamNo; // 팀 별로 고유한 쿠키 만들어줌

Cookie[] cookies = request.getCookies(); // 쿠키 가져와서 배열에 저장해줌
Cookie isViewCookie = null; // 특정 쿠키 존재 확인용

// 쿠키 배열 안비어있으면 특정 쿠키 잇는지 확인!
if (cookies != null) {
	for (Cookie cookie : cookies) {
		if (cookie.getName().equals(viewedCookie)) {
			isViewCookie = cookie;
			break;
		} // if
	} // for
} // outer-if

// 방문 안함
if (isViewCookie == null) {
	// 쿠키가 없다면 조회수를 증가
	service.updateViewCnt(teamNo);

	// 쿠키 생성 및 설정
	Cookie newCookie = new Cookie(viewedCookie, "viewed");	// 방문했다고 나타내줌
	newCookie.setMaxAge(24 * 60 * 60); // 유효시간 하루
	newCookie.setPath("/HTML/");
	//                newCookie.setPath("/");	// 쿠키 경로 = 웹사이트 경로
	response.addCookie(newCookie); // 사용자 웹 브라우저에 저장
} // if

System.out.println(viewedCookie);
*/