package control;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserProfileDownloadController implements Controller {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		response.setHeader("Access-Control-Allow-Origin", "http://localhost:5500");
		response.setContentType("application/octet-stream;charset=UTF-8");
		
		String loginedId=request.getParameter("loginedId");

		ServletOutputStream sos = response.getOutputStream();
		
		String attachesDir = "C:\\KOSA202307\\attaches";
		String fileName = loginedId +"_userprofile_";
		File dir = new File(attachesDir);
		
		
		for(File file : dir.listFiles()) {
			String existFileName = file.getName();
			if(existFileName.startsWith(fileName)) {
				System.out.println(existFileName+"파일입니다");
				response.setHeader("Content-Disposition",
						"attachment;filename=" + URLEncoder.encode(existFileName, "UTF-8"));
				FileInputStream fis = new FileInputStream(file);
				int readValue = -1;
				while((readValue=fis.read())!=-1) {
					sos.write(readValue);
				}
				sos.close();
				fis.close();
				return null;
			}
		}
		System.out.println(loginedId+"의 프로필 파일이 없습니다");
		return null;
	}
}
