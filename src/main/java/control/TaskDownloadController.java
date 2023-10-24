package control;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TaskDownloadController extends TaskController {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/octet-stream;charset=utf-8");
		
		ServletOutputStream sos=response.getOutputStream();
		
		Integer teamNo=Integer.parseInt(request.getParameter("teamNo"));
		Integer taskNo=Integer.parseInt(request.getParameter("taskNo"));
//		Integer taskNo=7;
		System.out.println(taskNo);
		
		String dir="C:\\KOSA202307\\attaches";
		String fileName="과제"+teamNo+"_"+taskNo+".";
		System.out.println(fileName);
		File fileDir=new File(dir);
		
		
		for(File file:fileDir.listFiles()) {
			String originName=file.getName();
			
			System.out.println(originName);
			if(originName.startsWith(fileName)) {
				System.out.println(originName);
				response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(originName, "UTF-8"));
				FileInputStream fis=new FileInputStream(file);
				int readValue=-1;
				while((readValue=fis.read())!=-1) {
					//sos.print(readValue);
					sos.write(readValue);
				}
				System.out.println("test");
				sos.close();
			}
		}
		
		return null;
	}

}
