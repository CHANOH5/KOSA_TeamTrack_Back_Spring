package com.my.rank.service;

import java.util.List;
import java.util.Map;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.rank.dto.RankDTO;
import com.my.rank.dto.RankDetailDTO;
import com.my.task.dto.MemberTaskDTO;
import com.my.task.dto.TaskDTO;
import com.my.team.dto.AttendanceDTO;
import com.my.team.dto.TeamMemberDTO;

public interface RankService {
	
	/**
	 * 월별 팀 내의 개인 랭킹을 조회한다. 랭킹이 없으면 FindException이 발생한다
	 * @param teamNo
	 * @param rankDate
	 * @return
	 * @throws FindException
	 */
	public List<RankDTO> findByMonth(Integer teamNo, Integer month) throws FindException;
	
	/**
	 * 팀별 모든 랭킹 정보를 조회한다. 랭킹이 없으면 FindException이 발생한다
	 * @param teamNo
	 * @return
	 * @throws FindException
	 */
	public List<RankDTO> findAllRank(Integer teamNo) throws FindException;
	
	/**
	 * 팀별 멤버 id를 조회한다. 팀 멤버가 없으면 FindException이 발생한다 
	 * @param teamNo
	 * @return
	 * @throws FindException
	 */
	public List<TeamMemberDTO> findMemberId(Integer teamNo, Integer month) throws FindException;
	
	/**
	 * 각 팀 멤버의 총 점수를 계산한다. 멤버의 점수가 없으면 FindException이 발생한다 
	 * @param teamNo
	 * @param month
	 * @return
	 * @throws FindException
	 */
	public Map<String, Object> calculateTotalScore(Integer teamNo, String attendanceDate, Integer month) throws FindException;
	
//	/**
//	 * 출제자의 과제 점수를 계산한다. 출제 내역이 없으면 FindException이 발생한다 
//	 * @param teamNo
//	 * @param month
//	 * @return
//	 * @throws FindException
//	 */
//	public Map<String, Integer> calculateSubmitScore(Integer teamNo, Integer month) throws FindException;
	
	/**
	 * 새롭게 가입한 멤버의 랭킹 정보를 저장한다. 저장이 불가하면 AddException이 발생한다 
	 * @param teamNo
	 * @param id
	 * @throws AddException
	 */
	public void addRankInfo(Integer teamNo, String id) throws AddException;
	
	/**
	 * 팀별 랭킹 정보를 실시간 업데이트 한다. 업데이트가 불가하면 ModifyException이 발생한다
	 * @param teamNo
	 * @param rankDate
	 * @param rank
	 * @param totalScore
	 * @param id
	 * @param month
	 * @throws ModifyException
	 */
	public void modifyRankInfo(Integer teamNo, String rankDate, Integer rank, Double totalScore, String id, Integer month) throws ModifyException;

}
