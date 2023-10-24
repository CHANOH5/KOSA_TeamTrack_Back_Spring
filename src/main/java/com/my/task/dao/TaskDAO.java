package com.my.task.dao;

import java.sql.SQLException;
import java.util.List;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;
import com.my.task.dto.MemberTaskDTO;
import com.my.task.dto.TaskDTO;

public interface TaskDAO {

	/**
	 * 과제생성순으로 정렬된 메인 과제들을 검색한다.
	 * @param teamNo 팀 번호
	 * @param id 아이디
	 * @return 과제 리스트
	 * @throws FindException
	 */
	List<TaskDTO> selectMainTaskList(Integer teamNo, String id) throws FindException;

	/**
	 * 과제생성순으로 정렬된 전체 과제들 중 시작행부터 끝행까지의 과제들을 검색한다.
	 * @param teamNo 팀 번호
	 * @param startRow 시작행
	 * @param endRow 끝행
	 * @param desc true인 경우 최신순, false인 경우 오래된순 출력
	 * @return 과제 리스트
	 * @throws FindException
	 */
	List<TaskDTO> selectAllTaskList(Integer teamNo, int startRow, int endRow, boolean desc) throws FindException;

	/**
	 * 전체 과제 수를 검색한다.
	 * @param teamNo 팀 번호
	 * @return 전체 과제 수
	 * @throws FindException
	 */
	int selectAllTaskCount(Integer teamNo) throws FindException;


	/**
	 * 과제제출순으로 정렬된 완료한 과제들 중 시작행부터 끝행까지의 과제들을 검색한다.
	 * @param teamNo 팀 번호
	 * @param id 아이디
	 * @param startRow 시작행
	 * @param endRow 끝행
	 * @param desc true인 경우 최신순, false인 경우 오래된순 출력
	 * @return 과제 리스트
	 * @throws FindException
	 */
	List<MemberTaskDTO> selectCompleteTaskList(Integer teamNo, String id, int startRow, int endRow, boolean desc) throws FindException;

	/**
	 * 완료한 과제 수를 검색한다.
	 * @param teamNo 팀 번호
	 * @param id 아이디
	 * @return 푼 과제 수
	 * @throws FindException
	 */
	int selectCompleteTaskCount(Integer teamNo, String id) throws FindException;

	/**
	 * 과제생성순으로 정렬된 출제한 과제들 중 시작행부터 끝행까지의 과제들을 검색한다.
	 * @param teamNo 팀 번호
	 * @param id 아이디
	 * @param startRow 시작행
	 * @param endRow 끝행
	 * @param desc true인 경우 최신순, false인 경우 오래된순 출력
	 * @return 과제 리스트
	 * @throws FindException
	 */
	List<TaskDTO> selectMyTaskList(Integer teamNo, String id, int startRow, int endRow, boolean desc) throws FindException;

	/**
	 * 출제한 과제 수를 검색한다.
	 * @param teamNo 팀 번호
	 * @param id 아이디
	 * @return 과제 리스트
	 * @throws FindException
	 */
	int selectMyTaskCount(Integer teamNo, String id) throws FindException;

	/**
	 * 과제 번호에 해당하는 과제를 불러온다.
	 * @param teamNo 팀 번호
	 * @param taskNo 과제 번호
	 * @return 과제
	 * @throws FindException
	 */
	TaskDTO selectTaskInfo(Integer teamNo, Integer taskNo) throws FindException;

	/**
	 * 과제 번호에 해당하는 과제의 답안을 불러온다.
	 * @param teamNo 팀 번호
	 * @param taskNo 과제 번호
	 * @return 답 리스트
	 * @throws FindException
	 */
	List<Integer> selectQuizAnswer(Integer teamNo, Integer taskNo) throws FindException;

	/**
	 * 과제 번호에 해당하는 과제의 팀원 답안을 불러온다.
	 * @param teamNo 팀 번호
	 * @param taskNo 과제 번호
	 * @param id 아이디
	 * @return 답 리스트
	 * @throws FindException
	 */
	List<Integer> selectMemberAnswer(Integer teamNo, Integer taskNo, String id) throws FindException;

	/**
	 * 팀원이 제출한 과제의 점수를 불러온다.
	 * @param teamNo 팀 번호
	 * @param taskNo 과제 번호
	 * @param id 아이디
	 * @return 팀원의 과제 정보
	 * @throws FindException
	 */
	int selectMemberScore(Integer teamNo, Integer taskNo, String id) throws FindException;

	/**
	 * 과제 내용을 업데이트한다.
	 * @param teamNo 팀 번호
	 * @param title 과제 제목
	 * @param taskNo 과제 번호
	 * @throws ModifyException
	 */
	void updateTask(Integer teamNo, String title, Integer taskNo) throws ModifyException;

	/**
	 * 과제 답안을 생성한다.
	 * @param teamNo 팀 번호
	 * @param questionNo 문제 번호
	 * @param taskNo 과제 번호
	 * @param answer 답
	 * @throws AddException
	 */
	void insertQuizAnswer(Integer teamNo, Integer questionNo, Integer taskNo, int answer) throws AddException;
	
	/**
	 * 출제자 아이디를 검색한다.
	 * @param teamNo 팀 번호
	 * @return 출제자 아이디 리스트
	 * @throws FindException
	 */
	List<TaskDTO> selectTaskId(Integer teamNo) throws FindException;
	
	/**
	 * 가입일 이후 등록된 전체 과제 수를 조회한다
	 * @author 나원희
	 * @param teamNo 팀번호
	 * @return 과제 수
	 * @throws SQLException 
	 */
	Integer selectJoinAfterTaskCount(String id, Integer teamNo) throws FindException, SQLException;
	 
	 /** 과제 번호에 해당하는 답 개수를 가져온다.
	 * @param teamNo 팀 번호
	 * @param taskNo 과제 번호
	 * @return 답 개수
	 * @throws FindException
	 */
	int selectAnswerCount(Integer teamNo, Integer taskNo) throws FindException;
	
	/**
	 * 팀원 답안을 생성한다.
	 * @param teamNo 팀 번호
	 * @param questionNo 문제 번호
	 * @param taskNo 과제 번호
	 * @param id 아이디
	 * @param answer 답
	 * @throws AddException
	 */
	void insertMemberAnswer(Integer teamNo, Integer questionNo, Integer taskNo, String id, int answer) throws AddException;
	
	/**
	 * 팀원 과제 정보를 생성한다.
	 * @param teamNo 팀 번호
	 * @param taskNo 과제 번호
	 * @param id 아이디
	 * @param hwscore 점수
	 * @param reviewScore 평점
	 * @throws AddException
	 */
	void insertMemberScore(Integer teamNo, Integer taskNo, String id, int hwscore, int reviewScore) throws AddException;

	/**
	 * 평점을 업데이트한다.
	 * @param teamNo 팀 번호
	 * @param taskNo 과제 번호
	 * @param id 아이디
	 * @param reviewScore 평점
	 * @throws ModifyException
	 */
	void updateReviewScore(Integer teamNo, Integer taskNo, String id, int reviewScore) throws ModifyException;
	
	/**
	 * 평균평점을 업데이트한다.
	 * @param teamNo 팀 번호
	 * @param taskNo 과제 번호
	 * @throws ModifyException
	 */
	void updateAvgReviewScore(Integer teamNo, Integer taskNo) throws ModifyException;
}
