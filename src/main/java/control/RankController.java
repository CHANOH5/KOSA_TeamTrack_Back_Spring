package control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.rank.dto.RankDTO;
import com.my.rank.service.RankService;
import com.my.util.ValueComparator;

@RestController
public class RankController {
	
	@Autowired
	private RankService service;
	
	@GetMapping("/ranklist")
	public Object list(Integer teamNo, String rankDate, Integer month) {
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
			map.put("status", 1);
			return ranklist;
		} catch (FindException e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", "랭킹 정보 조회에 실패하였습니다");
		} catch (ModifyException e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", "랭킹 정보 업데이트에 실패하였습니다");
		} catch (AddException e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", "랭킹 정보 추가에 실패하였습니다");
		}
		return map;
	}
	
	
	@GetMapping("/rankjson")
	public Object list(Integer teamNo, Integer month) {
		Map<String, Object> map = new HashMap<>();
		try {
			List<RankDTO> list = service.findByMonth(teamNo, month);
			map.put("status", 1);
			return list;
		} catch (FindException e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", "랭킹 정보 조회에 실패하였습니다");
		}
		return map;
	}
	
	
	@GetMapping("/rankbymonth")
	public Object list(Integer teamNo) {
		Map<String, Object> map = new HashMap<>();
		try {
			List<RankDTO> list = service.findAllRank(teamNo);
			map.put("status", 1);
			return list;
		} catch (FindException e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", "등록된 월이 없습니다");
		}
		return map;
	}
	
}
