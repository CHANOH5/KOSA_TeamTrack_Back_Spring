package control;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.exception.FindException;
import com.my.notice.dto.NoticeDTO;
import com.my.team.service.TeamServiceImpl;
import com.my.util.PageGroup;

public class NoticeDetailController extends NoticeController{
	TeamServiceImpl teamService = TeamServiceImpl.getInstance();
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");

		String id=request.getParameter("id");	

		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		
		Map map = new HashMap<>();
		Integer teamNo = Integer.parseInt(request.getParameter("teamNo"));
		Integer noticeNo = Integer.parseInt(request.getParameter("noticeNo"));

		//String loginedId = request.getParameter("loginedId");

		Integer memStatus = 0;
		String fileName = "null";
		
		String attachesDir = "C:\\KOSA202307\\attaches";
		File dir = new File(attachesDir);
		
		try {
			memStatus = teamService.leaderChk(id, teamNo);
			NoticeDTO notice = service.findByNoticeNo(teamNo, noticeNo);
			
			String findName = teamNo+"_"+noticeNo+"_notice_";
			
			try {
				for(File file : dir.listFiles()) {
					String existFileName = file.getName();
					if(existFileName.startsWith(findName)) {
						fileName = existFileName.replaceFirst(findName,"");
						break;
					}
				}
				map.put("fileName", fileName);
			} catch(Exception e) {
				
			}
			
			map.put("memStatus", memStatus);
			map.put("notice", notice);
			System.out.println(notice.getNoticeTitle());
			String jsonStr = mapper.writeValueAsString(map);
			out.print(jsonStr);
		} catch (FindException e) {
			e.printStackTrace();
		}

		return null;
	}
}
