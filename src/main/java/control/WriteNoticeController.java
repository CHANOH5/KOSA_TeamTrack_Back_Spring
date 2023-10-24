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
import com.my.notice.dto.NoticeDTO;
import com.my.util.Attach;

public class WriteNoticeController extends NoticeController {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();

		Map<String, Object> map = new HashMap<>();
		Date regDate = Date.from(Instant.now());
		Integer noticeNo;
		String attachesDir = "C:\\KOSA202307\\attaches";
		File dir = new File(attachesDir);

		try {
			Attach attach=new Attach(request);
			Integer teamNo = Integer.parseInt(attach.getParameter("teamNo"));
			System.out.println(teamNo);

			String noticeTitle=attach.getParameter("title");
			String noticeContent=attach.getParameter("content");
			Integer mainStatus = 0; //메인공지 체크여부
			Integer mainChk = 0; //메인공지 등록 정상 여부
			String fileName ="";
			
			if(attach.getParameter("status") != null) {
				mainStatus = 1;
				NoticeDTO mainNotice = service.findMainNotice(teamNo);
				if(mainNotice!=null) {
					map.put("mainmsg", "이미 메인공지 등록에는 실패하였습니다. 기존 메인공지를 내린 후 등록하세요!");
					mainStatus=0;
					mainChk=0;
				}else {
					mainChk=1;
				}
			}else {
				mainChk=1;
			}
			NoticeDTO notice = new NoticeDTO(noticeTitle, regDate, noticeContent, mainStatus);
			noticeNo = service.writeNotice(teamNo, notice);
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
			map.put("mainstatus",mainChk);
			map.put("status", 1);
			map.put("msg", "게시글이 업로드되었습니다");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", e.getMessage());
		}

		out.print(mapper.writeValueAsString(map));

		return null;
	}
}
