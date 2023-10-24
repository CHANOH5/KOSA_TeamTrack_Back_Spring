package com.my.team.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;
import com.my.notice.dto.NoticeDTO;
import com.my.task.dto.TaskDTO;
import com.my.team.dto.AttendanceDTO;
import com.my.team.dto.SignupTeamDTO;
import com.my.team.dto.TeamDTO;
import com.my.team.dto.TeamHashtagDTO;
import com.my.team.dto.TeamMemberDTO;
import com.my.util.MainPageGroup;
import com.my.util.PageGroup;

public interface TeamService {

	// 서현 웅니
	/**
	 * 전체 팀을 조회한다
	 * @param currentPage 현재 페이지
	 * @param column 조회수, 팀 생성일
	 * @return 팀 객체 그룹
	 * @throws FindException DB와의 연결 실패 시 예외 발생한다
	 */
	MainPageGroup<TeamDTO> findAll(int currentPage, String column) throws FindException;
	
	/**
	 * 해당 검색어를 포함하는 팀들을 조회한다
	 * @param currentPage 현재 페이지
	 * @param table 팀 해시태그, 팀 테이블
	 * @param column 해시태그, 팀명
	 * @param data 해시태그, 팀명
	 * @return 팀 객체 그룹
	 * @throws FindException DB와의 연결 실패 시 예외 발생한다
	 */
	MainPageGroup<TeamDTO> selectByData(int currentPage, String table, String column, String data) throws FindException;
	
	/**
	 * 선택한 해시태그에 해당하는 팀들을 조회한다
	 * @param currentPage 현재 페이지
	 * @param hashtag 해시태그
	 * @return 팀 객체 그룹
	 * @throws FindException DB와의 연결 실패 시 예외 발생한다
	 */
	MainPageGroup<TeamDTO> selectHashtag(int currentPage, String hashtag) throws FindException;
	
	/**
	 * 선택한 스터디 날짜에 해당하는 팀들을 조회한다
	 * @param currentPage 현재 페이지
	 * @param column 인기순, 최신순
	 * @param startDate 시작날짜
	 * @param endDate 종룔날짜
	 * @return 팀 객체 그룹
	 * @throws FindException DB와의 연결 실패 시 예외 발생한다
	 */
	MainPageGroup<TeamDTO> selectByDate(int currentPage, String column, String startDate, String endDate) throws FindException;
	
	/**
	 * 팀을 생성한다
	 * @param params 팀, 팀 해시태그 객체 맵
	 * @throws AddException DB와의 연결 실패 시 예외 발생한다
	 */
	void createTeam(HashMap<String, Object> params) throws AddException;

	/**
	 * 팀명 중복확인을한다
	 * @param teamName 팀명
	 * @throws FindException DB와의 연결 실패 시 예외 발생한다
	 */
	int teamNameDupChk(String teamName) throws FindException;
	
	/**
	 * 팀정보를 수정한다
	 * @param t 팀객체
	 * @throws ModifyException DB와의 연결 실패 시 예외 발생한다
	 */
	void updateTeam(TeamDTO t) throws ModifyException;
	
	/**
	 * 팀 해시태그를 조회한다
	 * @param teamNo 팀번호
	 * @return 팀해시태그 객체
	 * @throws FindException DB와의 연결 실패 시 예외 발생한다
	 */
	List<TeamHashtagDTO> selectTeamHashtag(int teamNo) throws FindException;

	/**
	 * 팀 해시태그를 수정한다
	 * @param params 해시태그 객체 맵
	 * @throws ModifyException DB와의 연결 실패 시 예외 발생한다
	 */
	void updateHashtag(HashMap<String, Object> params) throws ModifyException;

	/**
	 * 팀 해시태그를 삭제한다
	 * @param teamNo 팀번호
	 * @throws RemoveException DB와의 연결 실패 시 예외 발생한다
	 */
	void deleteHashtag(int teamNo) throws RemoveException;
	
	/**
	 * 팀을 삭제한다
	 * @param teamNo 팀번호
	 * @throws RemoveException DB와의 연결 실패 시 예외 발생한다
	 */
	void deleteTeam(int teamNo) throws RemoveException;

	/**
	 * 인기순/최신순 팀 리스트를 조회한다
	 * @param column 조회수, 팀생성일
	 * @return 팀 리스트
	 * @throws FindException DB와의 연결 실패 시 예외 발생한다
	 */
	List<TeamDTO> selectByCondition(String column) throws FindException;
		
	/**
	 * 팀번호에 해당하는 팀정보를 조회한다
	 * @param teamNo 팀번호
	 * @return 팀객체
	 * @throws FindException DB와의 연결 실패 시 예외 발생한다
	 */
	TeamDTO selectByTeamNo(int teamNo) throws FindException;
	
	// void updateViewCnt(int teamNo) throws ModifyException;

	// ------------------------------------------------------------------------
	
	// 워니 침입
	/**
	 * 팀장 여부를 확인한다
	 * @author 나원희
	 * @param id 로그인된 아이디
	 * @param teamNo 팀번호
	 * @return 팀장 여부
	 * @throws FindException DB 연결 실패 시 예외 발생한다
	 */
	Integer leaderChk(String id, Integer teamNo) throws FindException;
	
	/**
	 * 참여중, 활동종료, 승인대기 팀 목록을 조회한다
	 * @author 나원희
	 * @param 현재페이지
	 * @param id 사용자 아이디
	 * @menuStatus 메뉴탭 번호
	 * @return 팀정보
	 * @throws FindException DB 연결 실패 시 예외 발생한다
	 */
	PageGroup<SignupTeamDTO> findMyTeam(int currentPage, String id, int menuStatus) throws FindException;
	
	/**
	 * 승인거절 팀 목록을 조회한다
	 * @author 나원희
	 * @param currentPage 현재페이지
	 * @param id 사용자 아이디
	 * @return 승인거절 팀 목록
	 * @throws FindException DB 연결 실패 시 예외 발생한다
	 */
	PageGroup<SignupTeamDTO> findRejectedTeam(int currentPage, String id) throws FindException;
	
	/**
	 * 승인대기 취소한다
	 * @author 나원희
	 * @param id 사용자 아이디
	 * @param teamNo 팀번호
	 * @throws RemoveException DB 연결 실패 시 예외 발생한다
	 */
	void cancelWaiting(String id, Integer teamNo) throws RemoveException;
	
	/**
	 * 승인거절 확인하여 목록에서 삭제한다
	 * @author 나원희
	 * @param id 사용자 아이디
	 * @param teamNo 팀번호
	 * @throws RemoveException DB 연결 실패 시 예외 발생한다
	 */
	void rejectCheck(String id, Integer teamNo) throws RemoveException;
	
	/**
	 * 팀에서의 나의 활동내역을 조회한다
	 * @author 나원희
	 * @param id 사용자 아이디
	 * @param teamNo 팀번호
	 * @return 해당 팀 정보와 내 활동내역을 조회한다
	 * @throws FindException  DB 연결 실패 시 예외 발생한다
	 * @throws SQLException 
	 */
	Map myActivity(String id, Integer teamNo) throws FindException, SQLException;
	
	
	// ------------------------------------------------------------------------

	// 셍나
	
	/**
	 * 멤버 상태 구별
	 * @param id
	 * @param teamNo
	 * @return
	 * @throws Exception
	 */
	String determineUserRole(String id, int teamNo) throws Exception;
	
	/**
	 * 팀 메인 페이지 - 팀 멤버인지 확인하기
	 * @param teamMemberDTO
	 * @return
	 * @throws FindException
	 */
	Integer selectTeamMemberStatus(String id, Integer teamNo) throws FindException;
	
	/**
	 * 팀 메인 페이지 - 팀 소개글 보여주기
	 * @param teamNo
	 * @return TeamDTO
	 * @throws FindException
	 */
	String selectTeamInfoByTeamNo(int teamNo) throws FindException;

	/**
	 * 팀 메인 페이지 - 팀 정보 다 가져오기
	 * @param teamNo
	 * @return
	 * @throws FindException
	 */
	List<TeamDTO> selectAllTeamInfo(int teamNo) throws FindException;
	
	/**
	 * 팀 메인 페이지 - 공지사항 보여주기
	 * @param teamNo
	 * @return List<NoticeDTO>
	 * @throws FindException
	 */
	List<NoticeDTO> selectNoticeListByTeamNo(int teamNo) throws FindException;

	
	/**
	 * 팀 메인 페이지 - 팀 가입 전 방출 회원인지 판단
	 * @param teamNo
	 * @return
	 * @throws FindException
	 */
	Integer selectAllTeammember(int teamNo, String id) throws FindException;
	
	/**
	 * 팀 메인 페이지 - 팀에 가입하기
	 * @param SignupTeamDTO
	 * @throws AddException
	 */
	void insertSignUpTeam(SignupTeamDTO signupTeamDTO) throws AddException;

	/**
	 * 팀 메인 페이지 - 팀에서 나가기 #1 (팀원 테이블에서 상태값 변경)
	 * @param teamNo
	 * @param id
	 * @throws ModifyException
	 */
	void updateTeamMemberStatusResign(Integer teamNo, String id) throws ModifyException;

	/**
	 * 팀 메인 페이지 - 팀에서 나가기 #2 (가입한 팀 테이블에서 삭제)
	 * @param id
	 * @throws RemoveException
	 */
	void deleteSignupTeam(String id, Integer teamNo) throws RemoveException;

	/**
	 * 팀 메인 페이지 - 팀 나가기(트랜잭션)
	 * @param id 회원 아이디
	 * @throws Exception
	 */
	void leaveTeam(Integer teamNo, String id) throws Exception;

	/**
	 * 팀 메인 페이지 - 팀 멤버 출력
	 * @param teamNo
	 * @throws FindException
	 */
	List<String> selectNicknameByTeamNo(int teamNo) throws FindException;

	/**
	 * 팀 메인 페이지 - 조회수 카운트
	 * @param teamDTO
	 * @throws AddException
	 */
	void updateViewCnt(int teamNo) throws ModifyException;
	
	/**
	 * 팀 메인 페이지 - 조회수 출력
	 * @param teamNo
	 * @return int(조회수)
	 * @throws FindException
	 */
	int selectViewCnt(int teamNo) throws FindException;

//	---------------------------
	
	/**
	 * 팀 출석부 페이지 - 출석 여부 확인
	 * @param map
	 * @return String
	 * @throws FindException
	 */
	String selectAttendanceDate(Map<String, Object> map) throws FindException;
	
	/**
	 * 팀 출석부 페이지 - 출석하기#1 (attendance 테이블)
	 * @param teamNo
	 * @param id
	 * @throws AddException
	 */
	void insertAttendanceById(Map<String, Object> map) throws AddException;

	/**
	 * 팀 출석부 페이지 - 출석하기#2 (teammember 테이블)
	 * @param map
	 * @throws ModifyException
	 */
	void updateAttendanceCnt(Map<String, Object> map) throws ModifyException;
	
	/**
	 * 팀 출석부 페이지 - 출석 트랜잭션
	 * @param map
	 * @throws Exception
	 */
	void increaseAttendanceCnt(Map<String, Object> map) throws Exception;
	
	
	/**
	 * 팀 출석부 페이지 - 조회하기
	 * @param teamNo
	 * @param id
	 * @return
	 * @throws FindException
	 */
	List<AttendanceDTO> selectAttendanceById(Integer teamNo, String id) throws FindException;
	
//	---------------------------
	
	/**
	 * 팀 관리 페이지(현재 팀원 관리) - 현재 팀원들 정보 확인 (아이디, 닉네임, 자기소개)
	 * @param teamNo
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> selectMemberInfo(Integer teamNo) throws FindException;
	
	/**
	 * 팀 관리 페이지(현재 팀원 관리) - 현재 팀원들 정보 확인 (아이디, 닉네임, 자기소개) 팀장뺴고!!!!!!
	 * @param teamNo
	 * @return
	 * @throws FindException
	 */
	List<Map<String, Object>> selectTeamMemberInfo(Integer teamNo) throws FindException;
	
	/**
	 * 팀 관리 페이지(현재 팀원 관리) - 팀원 방출#1
	 * @param map
	 * @throws ModifyException
	 */
	void updateTeamMemberStatusDismiss(Map<String, Object> map) throws ModifyException;
	
	/**
	 * 팀 관리 페이지(현재 팀원 관리) - 팀원 방출#2
	 * @param map
	 * @throws RemoveException
	 */
	void deleteTeamMemberInSignupTeam(Map<String, Object> map) throws RemoveException;
	
	/**
	 * 팀원 방출 트랜잭션
	 * @param map
	 * @throws Exception
	 */
	void dismissTeamMember(Map<String, Object> map) throws Exception;
	
	/**
	 * 팀 관리 페이지(가입 요청 관리) - 팀 가입 요청 확인
	 * @param teamNo
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> selectRequestInfo(Integer teamNo) throws FindException;
	
	/**
	 * 팀 관리 페이지(가입 요청 관리) - 팀 가입 요청 승인1
	 * @param map
	 * @throws ModifyException
	 */
	void updateRequestInfoApprove(Map<String, Object> map) throws ModifyException;
	
	/**
	 * 팀 관리 페이지(가입 요청 관리) - 팀 가입 요청 승인2
	 * @param map
	 * @throws AddException
	 */
	void insertRequestInfoApprove(Map<String, Object> map) throws AddException;
	
	/**
	 * 팀 관리 페이지(가입 요청 관리) - 트랜잭션
	 * @param teamNo
	 * @param id
	 * @throws Exception
	 */
	void approveRequest(Map<String, Object> map) throws Exception;
	
	/**
	 * 팀 관리 페이지(가입 요청 관리) - 팀 가입 요청 거절
	 * @param map
	 * @throws Exception
	 */
	void updateRequestInfoReject(Map<String, Object> map) throws ModifyException;
	
	/**
	 * 팀 관리 페이지(출제자 선정) - 출제자 선정
	 * @param taskDTO
	 * @param teamNo
	 * @throws ModifyException
	 */
	void insertExaminer(TaskDTO taskDTO, Integer teamNo) throws ModifyException;
	
	/**
	 * 조회
	 * @param teamNo
	 * @return
	 * @throws FindException
	 */
	List<Map<String, Object>> selectExaminer(Integer teamNo) throws FindException;

} // end interface