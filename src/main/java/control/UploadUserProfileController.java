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

public class UploadUserProfileController implements Controller{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
//		response.setHeader("Access-Control-Allow-Origin", "http://localhost:5500");
//		response.setHeader("Access-Control-Allow-Credentials", "true");


		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();

		Map<String, Object> map = new HashMap<>();
		String attachesDir = "C:\\KOSA202307\\attaches";
		File dir = new File(attachesDir);

		try {
			Attach attach=new Attach(request);
			String loginedId = attach.getParameter("loginedId");
			System.out.println(loginedId);
			String fileName = loginedId +"_userprofile_";
			System.out.println(fileName);
			try {
				String originFileName=attach.getFile("f1").get(0).getName();
				for(File file : dir.listFiles()) {
					String existFileName = file.getName();
					if(existFileName.startsWith(fileName)) {
						file.delete();
					}
				}
				attach.upload("f1", loginedId+"_userprofile_"+originFileName);
			} catch(Exception e) {
				
			}
			map.put("status", 1);
			map.put("msg", "프로필이 업로드되었습니다");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", e.getMessage());
		}

		out.print(mapper.writeValueAsString(map));

		return null;
	}
}
