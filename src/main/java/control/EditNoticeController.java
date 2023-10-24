package control;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.exception.FindException;
import com.my.notice.dto.NoticeDTO;
import com.my.util.Attach;

public class EditNoticeController extends NoticeController{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");

		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();

		Map<String, Object> map = new HashMap<>();
		
		String attachesDir = "C:\\KOSA202307\\attaches";
		File dir = new File(attachesDir);

		try {
			Attach attach=new Attach(request);
			Integer teamNo = Integer.parseInt(attach.getParameter("teamNo"));
			Integer noticeNo = Integer.parseInt(attach.getParameter("noticeNo"));
			
			String noticeTitle = attach.getParameter("title");
			String noticeContent=attach.getParameter("content");
			Integer mainStatus = 0;
			Integer mainChk = 0;
			String fileName ="";
			
			if(attach.getParameter("status") != null) {
				mainStatus = 1;
				NoticeDTO mainNotice = service.findMainNotice(teamNo);
				if(mainNotice!=null) {
					if(mainNotice.getNoticeNo()==noticeNo) {
						map.put("mainmsg", "이미 메인공지로 등록된 게시글입니다");
						mainStatus=0;
					}else {
						map.put("mainmsg", "이미 메인공지가 존재합니다\n기존 메인공지를 내린 후 시도해주세요");
						mainStatus=0;
					}
				}else {
					mainChk = 1;
				}
			}else {
				mainChk=1;
			}
			
			NoticeDTO notice = new NoticeDTO(noticeNo, noticeTitle, noticeContent, mainStatus);
			service.modifyNotice(teamNo, notice);
			String findName = teamNo+"_"+noticeNo+"_notice_";
			
			try {
				if(attach.getFile("f1")!=null){
					String originFileName=attach.getFile("f1").get(0).getName();
					for(File file : dir.listFiles()) {
						String existFileName = file.getName();
						if(existFileName.startsWith(findName)) {
							file.delete();
						}
					}
					attach.upload("f1", teamNo+"_"+noticeNo+"_notice_"+originFileName);
				}
			} catch(Exception e) {

			}
			map.put("mainstatus", mainChk);
			map.put("status", 1);
			map.put("msg", "게시글이 수정되었습니다");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", e.getMessage());
		}

		out.print(mapper.writeValueAsString(map));

		return null;
	}
}
