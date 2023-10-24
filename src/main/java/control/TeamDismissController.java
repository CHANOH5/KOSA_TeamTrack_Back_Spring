package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.exception.ModifyException;


public class TeamDismissController extends TeamController {

    // 현재 팀원 확인 + 방출 컨트롤러
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NumberFormatException {

        response.setContentType("application/json;charset=utf-8");
//        response.setHeader("Access-Control-Allow-Origin", "http://localhost:5500");

        PrintWriter out = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();

        Map paramsMap = new HashMap<>();
        Map statusMap = new HashMap<>(); 
        Map methodMap = new HashMap<>();

        String id = request.getParameter("id");
        Integer teamNo = Integer.parseInt(request.getParameter("teamNo"));
        String action = request.getParameter("action");
        
        try {
        	
        	// 현재 팀원 목록 확인
        	List<Map<String, Object>> currMemberList = service.selectTeamMemberInfo(teamNo);
	    	methodMap.put("currMemberList", currMemberList);
	    	
        	// 방출
        	if ("memberDismiss".equals(action)) {
        		paramsMap.put("teamNo", teamNo);
        		paramsMap.put("id", id);
        		
        		service.dismissTeamMember(paramsMap);
        		
        		statusMap.put("status", 1);
        		statusMap.put("msg", "방출 성공");
        	} else {
        		statusMap.put("status", 0);
        		statusMap.put("msg", "방출 실패");
        	} // if-else

        } catch (ModifyException e) {
            e.printStackTrace();

            statusMap.put("status", 0);
            statusMap.put("msg", "ModifyException입니다.");
        } catch (Exception e) {
            e.printStackTrace();

            statusMap.put("status", 0);
            statusMap.put("msg", "방출 요청 컨트롤러에서 에러 발생");
        } // try-catch

        // JSON문자열 응답
        String jsonStr = mapper.writeValueAsString(methodMap);
        out.print(jsonStr);

        return null;
    } // execute()
} // end class
