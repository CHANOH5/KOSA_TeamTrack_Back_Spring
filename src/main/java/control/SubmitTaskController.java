package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.exception.AddException;
import com.my.exception.FindException;

public class SubmitTaskController extends TaskController {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		//HttpSession session=request.getSession();
		
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map=new HashMap<>();
		
		//Integer teamNo=9999;
		Integer teamNo=Integer.parseInt(request.getParameter("teamNo"));
		Integer taskNo=Integer.parseInt(request.getParameter("taskNo"));
		System.out.println(taskNo);
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("loginedId");
//		String id="khb2023";
		//String id="cjs1231";
		//String id=request.getParameter("id");
		String answer=request.getParameter("answerlist");
		int answerCnt=Integer.parseInt(request.getParameter("answerCnt"));
		System.out.println(answerCnt);
		
		if(answer.isEmpty()) {
			map.put("status", 0);
			map.put("msg", "답안을 올바르게 입력하세요");
			out.print(mapper.writeValueAsString(map));
			return null;
		}
		
		String[] answerList=answer.split(",");
		if(answerList.length!=answerCnt) {
			map.put("status", 0);
			map.put("msg", "모든 답안을 작성하세요");
			out.print(mapper.writeValueAsString(map));
			return null;
		}
		

		try {
			service.addMemberAnswer(teamNo, taskNo, id, answer);
			
			int hwscore=0;
			try {
				hwscore=service.chkHwscore(teamNo, taskNo, id);
			} catch (FindException e) {
				map.put("status", 0);
				map.put("msg", "모든 답안을 작성하세요");
			}
			System.out.println(hwscore);
			
			service.addMemberScore(teamNo, taskNo, id, hwscore, 5);
			
			map.put("status", 1);
			map.put("msg", "과제 제출에 성공하였습니다");
		} catch (AddException e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", "과제 제출에 실패하였습니다");
		}
		
		out.print(mapper.writeValueAsString(map));
		
		return null;
	}

}
