package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.exception.AddException;
import com.my.exception.ModifyException;
import com.my.util.Attach;

public class SetTaskController extends TaskController {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();

		Map<String, Object> map=new HashMap<>();
		
		try {
			Attach attach=new Attach(request);
			String title=attach.getParameter("title");
			String titletest=title.trim();
			String answer=attach.getParameter("answerlist");
			
			//Integer teamNo=9999;
			Integer teamNo=Integer.parseInt(attach.getParameter("teamNo"));
			Integer taskNo=Integer.parseInt(attach.getParameter("taskNo"));
			
			if(titletest.isEmpty()) {
				map.put("status", 0);
				map.put("msg", "타이틀을 올바르게 입력하세요");
				out.print(mapper.writeValueAsString(map));
				return null;
			}
			
			if(titletest.length()>30) {
				map.put("status", 0);
				map.put("msg", "타이틀을 30자 이내로 입력하세요");
				out.print(mapper.writeValueAsString(map));
				return null;
			}
			
			if(answer.isEmpty()) {
				map.put("status", 0);
				map.put("msg", "답안을 올바르게 입력하세요");
				out.print(mapper.writeValueAsString(map));
				return null;
			}
			
			if(attach.getFile("taskfile")==null) {
				map.put("status", 0);
				map.put("msg", "과제 파일을 첨부하세요");
				out.print(mapper.writeValueAsString(map));
				return null;
			}
			
			int answerCnt=Integer.parseInt(attach.getParameter("answerCnt"));
			String[] answerList=answer.split(",");
			if(answerList.length!=answerCnt) {
				map.put("status", 0);
				map.put("msg", "모든 답안을 작성하세요");
				out.print(mapper.writeValueAsString(map));
				return null;
			}
			
			service.addQuizAnswer(teamNo, taskNo, answer);
			service.modifyTask(teamNo, title, taskNo);
			
			try {
				//for(int i=0;i<attach.getFile("taskfile").size();i++) {
				
					String file=attach.getFile("taskfile").get(0).getName();
					System.out.println(file);
					String[] filetype=file.split("\\.");
					System.out.println(filetype[filetype.length-1]);
					attach.upload("taskfile", "과제"+teamNo+"_"+taskNo+"."+filetype[filetype.length-1]);
					
					
				//}
			} catch (Exception e) {
				e.printStackTrace();
				map.put("status", 0);
				map.put("msg", "출제에 실패하였습니다");
			} 
			
			map.put("status", 1);
			map.put("msg", "출제에 성공하였습니다");
			
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", "출제에 실패하였습니다");
		} 
		
		out.print(mapper.writeValueAsString(map));
		return null;
	}


}
