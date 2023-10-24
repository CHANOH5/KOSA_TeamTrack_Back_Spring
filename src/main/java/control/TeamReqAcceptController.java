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
import com.my.team.dto.TeamDTO;


public class TeamReqAcceptController extends TeamController {

    // 가입 승인&거절용 컨트롤러
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
           
           // 가입 요청자 확인
           List<Map<String, Object>> reqList = service.selectRequestInfo(teamNo);
          methodMap.put("reqList", reqList);
           
           // 가입 승인
           if ("reqApprove".equals(action)) {
              paramsMap.put("teamNo", teamNo);
              paramsMap.put("id", id);
              
              TeamDTO teamDTO = service.selectByTeamNo(teamNo);
              System.out.println(teamDTO);
              
              System.out.println("teamDTO.getJoinMember()= " + teamDTO.getJoinMember());
              System.out.println("teamDTO.getMaxMember()= " + teamDTO.getMaxMember());
              
              if(teamDTO.getJoinMember() < teamDTO.getMaxMember()) { // 현재 팀원이 최대 팀원을 넘지 않은 경우
                 service.approveRequest(paramsMap);                 
              } else {                                    // 현재 팀원이 최대 팀원을 초과한 경우
            	  methodMap.put("status", 3);
            	  methodMap.put("msg", "팀 인원이 꽉 찼습니다. 더이상 팀원을 추가할 수 없습니다.");
              } // if-else
              
              statusMap.put("status", 1);
              statusMap.put("msg", "가입 요청 승인 성공");
           } else {
              statusMap.put("status", 0);
              statusMap.put("msg", "가입 요청 승인 실패");
           } // if-else
           
           // 가입 거절
           if ("reqReject".equals(action)) {
               paramsMap.put("teamNo", teamNo);
               paramsMap.put("id", id);
   
               service.updateRequestInfoReject(paramsMap);
               
               statusMap.put("status", 1);
               statusMap.put("msg", "가입 요청 거절 성공");
           } else {
              statusMap.put("status", 0);
              statusMap.put("msg", "가입 요청 거절 실패");
           } // if-else

        } catch (ModifyException e) {
            e.printStackTrace();

            statusMap.put("status", 0);
            statusMap.put("msg", "ModifyException입니다.");
        } catch (Exception e) {
            e.printStackTrace();

            statusMap.put("status", 0);
            statusMap.put("msg", "가입 요청 컨트롤러에서 에러 발생");
        } // try-catch

        // JSON문자열 응답
        String jsonStr = mapper.writeValueAsString(methodMap);
        out.print(jsonStr);

        return null;
    } // execute()
} // end class