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
import com.my.exception.FindException;
import com.my.rank.dto.RankDTO;

public class RankListJsonController extends RankController {
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json; charset=utf-8");
//		response.setHeader("Access-Control-Allow-Origin", "http://localhost:5500"); //http://127.0.0.1:5500
		response.setHeader("Access-Control-Allow-Credentials", "true");
		
		//응답 출력 스트림 얻기
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		
		//요청전달데이터 
		Integer teamNo = Integer.parseInt(request.getParameter("teamNo"));
		//조회한 월
		Integer month = Integer.parseInt(request.getParameter("month"));
		
		try {
			List<RankDTO> list = service.findByMonth(teamNo, month);
			System.out.println(list);
			String jsonStr = mapper.writeValueAsString(list);
			out.print(jsonStr);
		} catch (FindException e) {
			e.printStackTrace();
			
			//정상 처리되지 못했을 때 메세지 띄우기
			Map<String, String> map = new HashMap<>();
			map.put("msg", "랭킹 정보 조회에 실패하였습니다");
			String jsonStr = mapper.writeValueAsString(map);
			out.print(jsonStr);
		}
		return null;
	}
}
