package com.my.team.dao;

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

public interface TeamDAO {

	// 서현 웅니

	/**
	 * 전체 팀 수를 조회한다
	 * @return 팀 수
	 * @throws FindException DB와의 연결실패 또는 제약조건위배일 경우 예외발생한다
	 */
	int selectCount() throws FindException;
	
	/**
	 * 선택한 해시태그를 가진 팀 수를 조회한다
	 * @param hashtag 해시태그
	 * @return 팀 수
	 * @throws FindException DB와의 연결실패 또는 제약조건위배일 경우 예외발생한다
	 */
	int selectCountOfSelectHashtag(String hashtag) throws FindException;
	
	/**
	 * 선택한 스터디 날짜에 해당하는 팀 수를 조회한다
	 * @param column 인기순, 최신순
	 * @param startDate 시작날짜
	 * @param endDate 종료날짜
	 * @return 팀 수
	 * @throws FindException DB와의 연결실패 또는 제약조건위배일 경우 예외발생한다
	 */
	int selectCountOfSelectDate(String column, String startDate, String endDate) throws FindException;
	
	/**
	 * 해당 검색어를 포함하는 팀 수를 조회한다
	 * @param table 팀 해시태그, 팀 테이블
	 * @param column 해시태그, 팀명
	 * @param data 해시태그, 팀명
	 * @return 팀 수
	 * @throws FindException DB와의 연결실패 또는 제약조건위배일 경우 예외발생한다
	 */
	int selectCountOfSelectData(String table, String column, String data) throws FindException;
 	
	/**
	 * 팀번호에 해당하는 팀정보를 조회한다
	 * @param teamNo 팀번호
	 * @return 팀객체
	 * @throws FindException DB와의 연결실패 또는 제약조건위배일 경우 예외발생한다
	 */
	TeamDTO selectByTeamNo(int teamNo) throws FindException;

	/**
	 * 팀이름에 해당하는 팀번호를 검색한다
	 * @param teamName 팀명
	 * @return 팀객체
	 * @throws FindException DB와의 연결실패 또는 제약조건위배일 경우 예외발생한다
	 */
	int selectByTeamName(String teamName) throws FindException;
	
	/**
	 * 선택한 해시태그에 해당하는 팀 리스트를 반환한다
	 * @param hashtag 해시태그
	 * @param startRow 시작행번호
	 * @param endRow 끝행번호
	 * @return 팀 리스트
	 * @throws FindException DB와의 연결실패 또는 제약조건위배일 경우 예외발생한다
	 */
	List<TeamDTO> selectHashtag(String hashtag, int startRow, int endRow) throws FindException;
	
	/**
	 * 해당 검색어를 포함하는 팀 리스트를 반환한다
	 * @param table 팀 해시태그, 팀 테이블
	 * @param column 해시태그, 팀명
	 * @param data 해시태그, 팀명
	 * @param startRow 시작행번호
	 * @param endRow 끝행번호
	 * @return 팀 리스트
	 * @throws FindException DB와의 연결실패 또는 제약조건위배일 경우 예외발생한다
	 */
	List<TeamDTO> selectByData(String table, String column, String data, int startRow, int endRow) throws FindException;
	
	/**
	 * 선택한 스터디 날짜에 해당하는 팀 리스트를 반환한다
	 * @param column 인기순, 최신순
	 * @param startDate 시작날짜
	 * @param endDate 종료날짜
	 * @param startRow 시작행번호
	 * @param endRow 끝행번호
	 * @return 팀 리스트
	 * @throws FindException DB와의 연결실패 또는 제약조건위배일 경우 예외발생한다
	 */
	List<TeamDTO> selectByDate(String column, String startDate, String endDate, int startRow, int endRow) throws FindException;

	/**
	 * 인기순/최신순 전체 팀 리스트를 반환한다
	 * @param column 조회수, 팀 생성일
	 * @param startRow 시작행번호
	 * @param endRow 끝행번호
	 * @return 팀 리스트
	 * @throws FindException DB와의 연결실패 또는 제약조건위배일 경우 예외발생한다
	 */
	List<TeamDTO> selectByCondition(String column, int startRow, int endRow) throws FindException;

	/**
	 * 팀을 생성한다
	 * @param params 팀 객체, 팀 해시태그 객체
	 * @throws AddException DB와의 연결실패 또는 제약조건위배일 경우 예외발생한다
	 */
	void createTeam(Map<String, Object> params) throws AddException;

	/**
	 * 팀 정보를 수정한다
	 * @param team 팀 객체
	 * @throws ModifyException DB와의 연결실패 또는 제약조건위배일 경우 예외발생한다
	 */
	void updateTeam(TeamDTO team) throws ModifyException;

	/**
	 * 팀의 해시태그를 조회한다
	 * @param teamNo 팀번호
	 * @return 팀 해시태그 객체
	 * @throws FindException DB와의 연결실패 또는 제약조건위배일 경우 예외발생한다
	 */
	List<TeamHashtagDTO> selectTeamHashtag(int teamNo) throws FindException;
	
	/**
	 * 팀 해시태그를 삭제한다
	 * @param teamNo 팀번호
	 * @throws RemoveException DB와의 연결실패 또는 제약조건위배일 경우 예외발생한다
	 */
	void deleteHashtag(int teamNo) throws RemoveException;

	/**
	 * 팀 해시태그를 수정한다
	 * @param params 팀 해시태그 객체
	 * @throws ModifyException DB와의 연결실패 또는 제약조건위배일 경우 예외발생한다
	 */
	void updateHashtag(Map<String, Object> params) throws ModifyException;

	/**
	 * 팀을 삭제한다
	 * @param teamNo 팀번호
	 * @throws RemoveException DB와의 연결실패 또는 제약조건위배일 경우 예외발생한다
	 */
	void deleteTeam(int teamNo) throws RemoveException;

	// void updateViewCnt(int teamNo) throws ModifyException;
	
	// ------------------------------------------------------------------------
	
	//워니 침입
	/**
	 * 팀장 아이디를 조회한다
	 * @author 나원희
	 * @param teamNo 팀번호
	 * @return 팀장 아이디
	 * @throws FindException DB와의 연결 실패 시 예외 발생한다
	 */
	String selectLeaderId(Integer teamNo) throws FindException;
	
	/**
	 * 참여중인 팀 목록을 조회한다
	 * @author 나원희
	 * @param startRow 시작행
	 * @param endRow 마지막행
	 * @param id 사용자 아이디
	 * @return 참여중인 팀
	 * @throws FindException DB와의 연결 실패 시 예외 발생한다
	 */
	List<SignupTeamDTO> selectMyTeam(int startRow, int endRow, String id) throws FindException;
	
	/**
	 * 참여중인 팀 개수 조회한다
	 * @author 나원희
	 * @param id 사용자 아이디
	 * @return 참여중인 팀 개수 
	 * @throws FindException DB와의 연결 실패 시 예외 발생한다
	 */
	int selectMyTeamCount(String id) throws FindException;
	
	/**
	 * 활동종료된 팀 목록 조회한다
	 * @author 나원희
	 * @param startRow 시작행
	 * @param endRow 마지막행
	 * @param id 사용자 아이디
	 * @return 활동종료된 팀
	 * @throws FindException DB와의 연결 실패 시 예외 발생한다
	 */
	List<SignupTeamDTO> selectEndTeam(int startRow, int endRow, String id) throws FindException;
	
	/**
	 * 활동종료된 팀 개수 조회한다
	 * @author 나원희
	 * @param id 사용자 아이디
	 * @return 활동종료된 팀 개수
	 * @throws FindException DB와의 연결 실패 시 예외 발생한다
	 */
	int selectEndTeamCount(String id) throws FindException;
	
	/**
	 * 승인대기, 승인거절 팀 목록 조회한다
	 * @author 나원희
	 * @param startRow 시작행
	 * @param endRow 마지막행
	 * @param id 사용자 아이디
	 * @param status 팀 승인여부
	 * @return 승인대기중인 팀
	 * @throws FindException DB와의 연결 실패 시 예외 발생한다
	 */
	List<SignupTeamDTO> selectWaitingTeam(int startRow, int endRow, String id, Integer status) throws FindException;
	
	/**
	 * 승인대기, 승인거절 팀 개수 조회한다
	 * @author 나원희
	 * @param id 사용자 아이디
	 * @param status 팀 승인여부
	 * @return 승인대기중 팀 개수 
	 * @throws FindException DB와의 연결 실패 시 예외 발생한다
	 */
	int selectWaitingTeamCount(String id, Integer status) throws FindException;
	
	/**
	 * 승인대기 팀을 삭제한다
	 * @author 나원희
	 * @param id 사용자 아이디
	 * @param teamNo 팀번호
	 * @throws RemoveException DB와의 연결 실패 시 예외 발생한다
	 */
	void deleteSignupTeamByTeamNo(String id, Integer teamNo) throws RemoveException;
	
	/**
	 * 해당 팀에서의 멤버 정보를 조회한다
	 * @author 나원희
	 * @param id 사용자 아이디
	 * @param teamNo 팀번호
	 * @return 팀에서의 멤버 정보
	 * @throws FindException DB와의 연결 실패 시 예외 발생한다
	 */
	TeamMemberDTO selectTeamMember(String id, Integer teamNo) throws FindException;
	
	/**
	 * 해당되는 회원이 가입한 팀 번호를 모두 조회한다
	 * @author 나원희
	 * @param id 사용자 아이디
	 * @return 팀번호
	 * @throws FindException DB와의 연결 실패 시 예외 발생한다
	 */
	List selectSignupTeam(String id) throws FindException;
	
	
	
	// ------------------------------------------------------------------------

	// 셍나
	/**
	 * 팀 멤버인지 확인 = 결과가 1이면 멤버임
	 * @param teamMemberDTO
	 * @return
	 * @throws FindException
	 */
	Integer selectTeamMemberStatus(String id, Integer teamNo) throws FindException;
	
	/**
	 * 팀 메인 페이지 - 팀 소개글 보여주기
	 * @param teamNo
	 * @return String
	 * @throws FindException
	 */
	String selectTeamInfoByTeamNo(int teamNo) throws FindException;
	
	/**
	 * 팀 메인 페이지 - 정보 보여주기
	 * @param teamDTO
	 * @return TeamDTO
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
	 * 팀 메인 페이지 - 가입하기 버튼 클릭 시 방출 회원인지 아닌지 판단
	 * @param teamNo
	 * @return TeamMemberDTO
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
	 * @return Integer
	 * @throws FindException
	 */
	int selectViewCnt(int teamNo) throws FindException;

//	---------------------------
	
	/**
	 * 팀 출석부 페이지 - 출석 여부 확인
	 * @param params
	 * @return
	 * @throws FindException
	 */
	String selectAttendanceDate(Map<String, Object> map) throws FindException;

	/**
	 * 팀 출석부 페이지 - 출석하기#1
	 * @param teamNo
	 * @param id
	 * @throws AddException
	 */
	void insertAttendanceById(Map<String, Object> map) throws AddException;
	
	/**
	 * 팀 출석부 페이지 - 출석하기#2
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
	 * 팀 관리 페이지(현재 팀원 관리) - 현재 팀원들 정보 확인(팀장 빼고!!!)
	 * @param teamNo
	 * @return
	 * @throws FindException
	 */
	List<Map<String, Object>> selectTeamMemberInfo(Integer teamNo) throws FindException;
	
	/**
	 * 팀 관리 페이지(현재 팀원 관리) - 팀원 방출#1
	 * @param map
	 * @throws Exception
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
	 * 트랜잭션
	 * @param map
	 * @throws Exception
	 */
	void approveRequest(Map<String, Object> map) throws Exception;
	
	/**
	 * 팀 관리 페이지(가입 요청 관리) - 팀 가입 요청 거절
	 * @param map
	 * @throws ModifyException
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
	 * 출제자 조회
	 * @param teamNo
	 * @return
	 * @throws FindException
	 */
	List<Map<String, Object>> selectExaminer(Integer teamNo) throws FindException;

} // end interface
