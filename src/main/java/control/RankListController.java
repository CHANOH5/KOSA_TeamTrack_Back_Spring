package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.rank.dto.RankDTO;
import com.my.team.dto.TeamMemberDTO;
import com.my.util.ValueComparator;

public class RankListController extends RankController {
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json; charset=utf-8");
//		response.setHeader("Access-Control-Allow-Origin", "http://localhost:5500"); //http://127.0.0.1:5500
		response.setHeader("Access-Control-Allow-Credentials", "true");
		
		//응답 출력 스트림 얻기
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		
		//팀번호
		Integer teamNo = Integer.parseInt(request.getParameter("teamNo"));
		//현재날짜
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date nowdate = Date.from(Instant.now());
		String rankDate = formatter.format(nowdate);
		//현재날짜의 월
		LocalDate now = LocalDate.now();
		Integer month = now.getMonthValue(); 
		
		//개인 랭킹을 실시간으로 계산하여 반환해준다
		List<Map<String, Object>> ranklist = new ArrayList<>();		
		Map<String, Object> map=new HashMap<>();
		
		try {
			List<RankDTO> list = service.findByMonth(teamNo, month);
			System.out.println(list);

			//랭킹 DB에 담겨져 있지 않았던 id는 추가해준다
			List<RankDTO> rankall = service.findAllRank(teamNo);

			for (RankDTO dto : list) {
				boolean add = true;
				for (RankDTO dto1 : rankall) {
					if (dto.getId().equals(dto1.getId()) && dto.getMonth().equals(dto1.getMonth())) {
						add = false;
						break;
					}
				}
				if (add) {
					service.addRankInfo(teamNo, dto.getId());
					service.modifyRankInfo(teamNo, rankDate, dto.getRank(), dto.getTotalScore(), dto.getId(), month);
					System.out.println("추가 성공");
				}
			}
			
			//id별 총점 가져오기
			Map<String, Object> scoremap = service.calculateTotalScore(teamNo, rankDate, month);
			System.out.println(scoremap);
			
			// calculate 점수 -> 데이터 전달하기 
			Map<String, Object> rankmap = new LinkedHashMap();
			List<RankDTO> dtolist = new ArrayList();

			for (RankDTO dto : list) {
				dtolist.add(dto);
				
				// service에서 점수 계산하여 다시 세팅
				for (String key : scoremap.keySet()) {
					if (key.equals(dto.getId())) {
						dto.setTotalScore((Double)scoremap.get(key));
					}
				}
				Collections.sort(dtolist, new ValueComparator()); // 총점 기준 내림차순 정렬 
			}
			
			// Rank 순위를 TotalScore 기준으로 새롭게 부여
			int currrank = 1;
			for (int i = 0; i < dtolist.size(); i++) {
				if (i>0 && (dtolist.get(i).getTotalScore() < dtolist.get(i-1).getTotalScore())) {
					currrank++; //total score 이전보다 작으면 rank 하나 증가
					dtolist.get(i).setRank(currrank);
				} else {		//이외의 경우 rank 유지
					dtolist.get(i).setRank(currrank);
				}
				rankmap.put(dtolist.get(i).getId(), dtolist.get(i));
			}
			ranklist.add(rankmap);
			System.out.println("ranklist" + ranklist);
			
			// 기존에 있는 멤버에는 업데이트한 정보를 Rank DB에 저장한다
			Integer rank = null;
			Double totalScore = null;
			String rankid = null;
			
			for (RankDTO dto : dtolist) {
				rank = dto.getRank();
				totalScore = dto.getTotalScore();
				rankid = dto.getId();
				service.modifyRankInfo(teamNo, rankDate, rank, totalScore, rankid, month);
			}
			System.out.println("modify 성공");
			String jsonStr = mapper.writeValueAsString(ranklist);
			out.print(jsonStr);
			
		} catch (FindException e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", "랭킹 정보 조회에 실패하였습니다");
			String jsonStr = mapper.writeValueAsString(map);
			out.print(jsonStr);
		} catch (ModifyException e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", "랭킹 정보 업데이트에 실패하였습니다");
			String jsonStr = mapper.writeValueAsString(map);
			out.print(jsonStr);
		} catch (AddException e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", "랭킹 정보 추가에 실패하였습니다");
			String jsonStr = mapper.writeValueAsString(map);
			out.print(jsonStr);
		}
		
		return null;
	}
}
