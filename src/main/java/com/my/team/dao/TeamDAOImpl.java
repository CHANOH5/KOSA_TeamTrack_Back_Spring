package com.my.team.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

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

public class TeamDAOImpl implements TeamDAO {

	private SqlSessionFactory sqlSessionFactory;

	public TeamDAOImpl() {

		String resource = "com/my/sql/mybatis-config.xml";
		InputStream inputStream;

		try {

			inputStream = Resources.getResourceAsStream(resource);

			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

		} catch (IOException e) {
			e.printStackTrace();
		} // try-catch

	} // constructor

	//	---------------------------------------------------------------------------------

	// 서현웅니

	@Override
	public int selectCount() throws FindException{

		SqlSession session = null;

		try {
			session = sqlSessionFactory.openSession(); //Connection
			int count = session.selectOne("com.my.team.TeamMapper.selectCount");
			return count;
		}catch(Exception e) {
			throw new FindException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}
	}

	@Override
	public int selectCountOfSelectHashtag(String hashtag) throws FindException {
		SqlSession session = null;

		try {
			session = sqlSessionFactory.openSession(); //Connection
			int count = session.selectOne("com.my.team.TeamMapper.selectCountOfSelectHashtag", hashtag);
			return count;
		}catch(Exception e) {
			throw new FindException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}
	}

	@Override
	public int selectCountOfSelectDate(String column, String startDate, String endDate) throws FindException {
		SqlSession session = null;

		try {
			session = sqlSessionFactory.openSession(); //Connection
			Map<String, Object> map = new HashMap<>();
			map.put("column", column);
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			int count = session.selectOne("com.my.team.TeamMapper.selectCountOfSelectDate", map);
			return count;
		}catch(Exception e) {
			throw new FindException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}
	}

	@Override
	public int selectCountOfSelectData(String table, String column, String data) throws FindException {
		SqlSession session = null;

		try {
			session = sqlSessionFactory.openSession(); //Connection
			Map<String, Object> map = new HashMap<>();
			map.put("table", table);
			map.put("column", column);
			map.put("data", data);
			int count = session.selectOne("com.my.team.TeamMapper.selectCountOfSelectData", map);
			return count;
		}catch(Exception e) {
			throw new FindException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}
	}

	@Override
	public TeamDTO selectByTeamNo(int teamNo) throws FindException {
		SqlSession session = null;

		try {
			session = sqlSessionFactory.openSession(); //Connection
			TeamDTO team = session.selectOne("com.my.team.TeamMapper.selectByTeamNo", teamNo);
			if(team != null) {
				return team;
			}else {
				throw new FindException("해당하는 팀이 없습니다");
			}
		}catch(Exception e) {
			throw new FindException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}
	}

	@Override
	public int selectByTeamName(String teamName) throws FindException {
		SqlSession session = null;

		try {
			session = sqlSessionFactory.openSession(); //Connection
			int teamNo = session.selectOne("com.my.team.TeamMapper.selectByTeamName", teamName);
			if(teamNo != 0) {
				return teamNo;
			}else {
				throw new FindException("해당하는 팀이 없습니다");
			}
		}catch(Exception e) {
			throw new FindException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}
	}

	
	@Override
	public List<TeamDTO> selectByData(String table, String column, String data, int startRow, int endRow) throws FindException {
		SqlSession session = null;
		List<TeamDTO> list = new ArrayList<>();

		try {
			session = sqlSessionFactory.openSession(); //Connection
			Map<String, Object> map = new HashMap<>();
			map.put("table", table);
			map.put("column", column);
			map.put("data", data);
			map.put("start", startRow);
			map.put("end", endRow);
			list = session.selectList("com.my.team.TeamMapper.selectByData", map);
			return list;
		}catch(Exception e) {
			throw new FindException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<TeamDTO> selectByCondition(String column, int startRow, int endRow) throws FindException {
		SqlSession session = null;
		List<TeamDTO> list = new ArrayList<>();

		try {
			session = sqlSessionFactory.openSession(); //Connection
			Map<String, Object> map = new HashMap<>();
			map.put("column", column);
			map.put("start", startRow);
			map.put("end", endRow);
			list = session.selectList("com.my.team.TeamMapper.selectByCondition", map);
			return list;
		}catch(Exception e) {
			throw new FindException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}
	}

	public List<TeamDTO> selectByDate(String column, String startDate, String endDate, int startRow, int endRow) throws FindException{
		SqlSession session = null;
		List<TeamDTO> list = new ArrayList<>();

		try {
			session = sqlSessionFactory.openSession(); //Connection
			Map<String, Object> map = new HashMap<>();
			map.put("column", column);
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("start", startRow);
			map.put("end", endRow);
			list = session.selectList("com.my.team.TeamMapper.selectByDate", map);
			return list;
		}catch(Exception e) {
			throw new FindException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}
	}		

	
	
	@Override
	public void createTeam(Map<String, Object> params) throws AddException {

		SqlSession session = null;

		try {
			session = sqlSessionFactory.openSession();
			session.selectOne("com.my.team.TeamMapper.createTeam", params);
			session.commit();
		} catch(Exception e){
			session.rollback();
			throw new AddException(e.getMessage());
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}

	@Override
	public void updateTeam(TeamDTO team) throws ModifyException {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			session.update("com.my.team.TeamMapper.updateTeam", team);
			session.commit();
		} catch(Exception e){
			session.rollback();
			throw new ModifyException(e.getMessage());
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}

	@Override
	public void deleteTeam(int teamNo) throws RemoveException {
		SqlSession session = null;

		try {
			session = sqlSessionFactory.openSession();
			session.delete("com.my.team.TeamMapper.deleteTeam", teamNo);
			session.commit();
		} catch(Exception e){
			session.rollback();
			throw new RemoveException(e.getMessage());
		} finally {
			if(session != null) {
				session.close();
			}
		}

	}

	@Override
	public List<TeamHashtagDTO> selectTeamHashtag(int teamNo) throws FindException {
		SqlSession session = null;
		List<TeamHashtagDTO> list = new ArrayList<>();

		try {
			session = sqlSessionFactory.openSession(); //Connection
			list = session.selectList("com.my.team.TeamMapper.selectTeamHashtag", teamNo);
			return list;
		}catch(Exception e) {
			throw new FindException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}
	}
		
	
	
	@Override
	public void deleteHashtag(int teamNo) throws RemoveException {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			session.delete("com.my.team.TeamMapper.deleteHashtag", teamNo);
			session.commit();
		} catch(Exception e){
			session.rollback();
			throw new RemoveException(e.getMessage());
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}

	@Override
	public void updateHashtag(Map<String, Object> params) throws ModifyException {
		SqlSession session = null;

		try {
			session = sqlSessionFactory.openSession(); //Connection
			session.update("com.my.team.TeamMapper.insertHashtag", params);
			session.commit();
		}catch(Exception e) {
			session.rollback();
			throw new ModifyException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}
	}

	

	@Override
	public List<TeamDTO> selectHashtag(String hashtag, int startRow, int endRow) throws FindException {
		SqlSession session = null;
		List<TeamDTO> list = new ArrayList<>();

		try {
			session = sqlSessionFactory.openSession(); //Connection
			Map<String, Object> map = new HashMap<>();
			map.put("data", hashtag);
			map.put("start", startRow);
			map.put("end", endRow);
			list = session.selectList("com.my.team.TeamMapper.selectHashtag", map);
			return list;
		}catch(Exception e) {
			throw new FindException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}
	}






		

	//	---------------------------------------------------------------------------------

	//워니 침입

	@Override
	public String selectLeaderId(Integer teamNo) throws FindException{
		SqlSession session = null;

		try {
			session = sqlSessionFactory.openSession();
			String leaderId = session.selectOne("com.my.team.TeamMapper.selectLeaderId", teamNo);
			return leaderId;
		} catch (Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			if(session!=null) {
				session.close();
			}
		}		
	}
	
	@Override
	public List<SignupTeamDTO> selectMyTeam(int startRow, int endRow, String id) throws FindException{
		SqlSession session = null;
		List<SignupTeamDTO> teamList = new ArrayList<>();

		try{
			session = sqlSessionFactory.openSession();
			Map map = new HashMap<>();
			map.put("start", startRow);
			map.put("end", endRow);
			map.put("id", id);
			teamList = session.selectList("com.my.team.TeamMapper.selectMyTeam", map);
			return teamList;
		}catch(Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			if(session!=null) {
				session.close();
			}
		}	
	}
	
	@Override
	public int selectMyTeamCount(String id) throws FindException{
		SqlSession session = null;

		try {
			session = sqlSessionFactory.openSession();
			int count = session.selectOne("com.my.team.TeamMapper.selectMyTeamCount", id);
			return count;
		}catch(Exception e) {
			throw new FindException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}
	}
	
	@Override
	public List<SignupTeamDTO> selectEndTeam(int startRow, int endRow, String id) throws FindException{
		SqlSession session = null;
		List<SignupTeamDTO> teamList = new ArrayList<>();

		try{
			session = sqlSessionFactory.openSession();
			Map map = new HashMap<>();
			map.put("start", startRow);
			map.put("end", endRow);
			map.put("id", id);
			teamList = session.selectList("com.my.team.TeamMapper.selectEndTeam", map);
			return teamList;
		}catch(Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			if(session!=null) {
				session.close();
			}
		}	
	}
	
	@Override
	public int selectEndTeamCount(String id) throws FindException{
		SqlSession session = null;

		try {
			session = sqlSessionFactory.openSession();
			int count = session.selectOne("com.my.team.TeamMapper.selectEndTeamCount", id);
			return count;
		}catch(Exception e) {
			throw new FindException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}
	}
	
	@Override
	public List<SignupTeamDTO> selectWaitingTeam(int startRow, int endRow, String id, Integer status) throws FindException{
		SqlSession session = null;
		List<SignupTeamDTO> teamList = new ArrayList<>();

		try{
			session = sqlSessionFactory.openSession();
			Map map = new HashMap<>();
			map.put("start", startRow);
			map.put("end", endRow);
			map.put("id", id);
			map.put("status", status);
			teamList = session.selectList("com.my.team.TeamMapper.selectWaitingTeam", map);
			return teamList;
		}catch(Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			if(session!=null) {
				session.close();
			}
		}	
	}
	
	@Override
	public int selectWaitingTeamCount(String id, Integer status) throws FindException{
		SqlSession session = null;
		Map map = new HashMap<>();

		try {
			session = sqlSessionFactory.openSession();
			map.put("id", id);
			map.put("status", status);
			int count = session.selectOne("com.my.team.TeamMapper.selectWaitingTeamCount", map);
			return count;
		}catch(Exception e) {
			throw new FindException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}
	}
	
	@Override
	public void deleteSignupTeamByTeamNo(String id, Integer teamNo) throws RemoveException{
		SqlSession session = null;
		Map map = new HashMap<>();

		try {
			session = sqlSessionFactory.openSession();
			map.put("id", id);
			map.put("teamNo", teamNo);
			session.delete("com.my.team.TeamMapper.deleteSignupTeam", map);
			session.commit();
		}catch(Exception e) {
			session.rollback();
			throw new RemoveException(e.getMessage());
		}finally {
			if(session!=null) {
				session.close();
			}
		}
	}
	
	@Override
	public TeamMemberDTO selectTeamMember(String id, Integer teamNo) throws FindException{
		SqlSession session = null;
		String tableName = "TEAMMEMBER_"+ String.valueOf(teamNo);
		Map map = new HashMap<>();

		try {
			session = sqlSessionFactory.openSession(); 
			map.put("tableName", tableName);
			map.put("id", id);			
			TeamMemberDTO teammember = session.selectOne("com.my.team.TeamMapper.selectTeamMember", map);
			return teammember;
		}catch(Exception e) {
			throw new FindException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}
	}
	
	@Override
	public List selectSignupTeam(String id) throws FindException{
		SqlSession session = null;

		try {
			session = sqlSessionFactory.openSession(); 	
			List<SignupTeamDTO> list = session.selectList("com.my.team.TeamMapper.selectSignupTeam", id);
			return list;
		}catch(Exception e) {
			throw new FindException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}
	}
	
	//	---------------------------------------------------------------------------------

	// 셍나
	
	// 팀 멤버인지 확인 1 아니면 일반 회원
	@Override
	public Integer selectTeamMemberStatus(String id, Integer teamNo) throws FindException {
		SqlSession session = null;
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("id", id);
		map.put("teamNo", teamNo);

		try {
			session = sqlSessionFactory.openSession();

			int selectedTeamMemberStatus = session.selectOne("com.my.team.TeamMapper.selectTeamMemberStatus", map);

				return selectedTeamMemberStatus;
			
		} catch(Exception e) {
			throw new FindException(e.getMessage());
		} finally {
			if(session != null) {
				session.close();
			} // if
		} // try-catch-finally
	} // selectTeamMemberStatus()

	// 팀 메인 페이지 - 팀 소개글 보여주기
	@Override
	public String selectTeamInfoByTeamNo(int teamNo) throws FindException {
		SqlSession session = null;

		try {
			session = sqlSessionFactory.openSession();

			String selectedTeamInfo = session.selectOne("com.my.team.TeamMapper.selectTeamInfoByTeamNo", teamNo);

			if(selectedTeamInfo != null) {
				return selectedTeamInfo;
			} else {
				throw new FindException("선택하신 팀의 소개글이 존재하지 않습니다.");
			} // if-else
		} catch(Exception e) {
			throw new FindException(e.getMessage());
		} finally {
			if(session != null) {
				session.close();
			} // if
		} // try-catch-finally
	} // selectByTeamInfo()
	
	// 팀 메인 페이지 - 정보들 다 가져오기
	@Override
	public List<TeamDTO> selectAllTeamInfo(int teamNo) throws FindException {
		SqlSession session = null;

		try {
			session = sqlSessionFactory.openSession();

			List<TeamDTO> teamInfoList = session.selectList("com.my.team.TeamMapper.selectAllTeamInfo", teamNo);

			if(teamInfoList != null) {
				return teamInfoList;
			} else {
				throw new FindException("선택하신 팀의 정보가 존재하지 않습니다.");
			}
		} catch(Exception e) {
			throw new FindException(e.getMessage());
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}

	// 팀 메인 페이지 - 팀 공지사항 보여주기
	@Override
	public List<NoticeDTO> selectNoticeListByTeamNo(int teamNo) throws FindException {
		SqlSession session = null;

		try {
			session = sqlSessionFactory.openSession();

			List<NoticeDTO> noticeList = session.selectList("com.my.team.TeamMapper.selectNoticeListByNoticeNo", teamNo);

			if(noticeList != null) {
				return noticeList;
			} else {
				throw new FindException("선택하신 팀의 공지사항이 존재하지 않습니다.");
			} // if-else

		} catch (Exception e) {
			throw new FindException(e.getMessage());
		} finally {
			if(session != null) {
				session.close();
			} // if
		} // try-catch-finally
	} // selectNoticeListByNoticeNo

	// 팀 메인 페이지 - 팀 가입 전 방출회원 판단
	@Override
	public Integer selectAllTeammember(int teamNo, String id) throws FindException {
		SqlSession session = null;

		Map<String, Object> map = new HashMap<>();
		
		map.put("id", id);
		map.put("teamNo", teamNo);
		
		try {
			session = sqlSessionFactory.openSession();

			Integer teamMemberStatus = session.selectOne("com.my.team.TeamMapper.selectAllTeammember", map);
			
			System.out.println(teamMemberStatus);

			if(teamMemberStatus != null) {
				return teamMemberStatus;
			} else {
				throw new FindException("선택하신 팀의 멤버가 없습니다.");
			} // if-else

		} catch (Exception e) {
			throw new FindException(e.getMessage());
		} finally {
			if(session != null) {
				session.close();
			} // if
		} // try-catch-finally
	}
	
	// 팀 메인 페이지 - 팀 가입하기
	@Override
	public void insertSignUpTeam(SignupTeamDTO signupTeamDTO) throws AddException {
		SqlSession session = null;

		try {
			session = sqlSessionFactory.openSession();

			session.insert("com.my.team.TeamMapper.insertIntoSignupTeam", signupTeamDTO);
			session.commit();
		} catch(Exception e){
			session.rollback();
			throw new AddException(e.getMessage());
		} finally {
			if(session != null) {
				session.close();
			} // if
		} // try-catch-finally
	} // insertSignUpTeam()

	// 팀 메인 페이지 - 팀 나가기 #1
	@Override
	public void updateTeamMemberStatusResign(Integer teamNo, String id) throws ModifyException {
		SqlSession session = null;

		try {
			session = sqlSessionFactory.openSession();
			
	        Map<String, Object> map = new HashMap<>();
	        
	        map.put("teamNo", teamNo);
	        map.put("id", id);

			session.update("com.my.team.TeamMapper.updateTeamMemberStatusResign", map);
			session.commit();
		} catch(Exception e) {
			session.rollback();
			throw new ModifyException(e.getMessage());
		} finally {
			if(session != null) {
				session.close();
			} // if
		} // try-catch-finally
	} // updateTeamMemberStatus()

	// 팀 메인 페이지 - 팀 나가기 #2
	@Override
	public void deleteSignupTeam(String id, Integer teamNo) throws RemoveException {
		SqlSession session = null;
		Map map = new HashMap<>();

		try {
			session = sqlSessionFactory.openSession();
			map.put("id", id);
			map.put("teamNo", teamNo);

			session.delete("com.my.team.TeamMapper.deleteSignupTeam", map);
			session.commit();
		} catch(Exception e){
			session.rollback();
			throw new RemoveException(e.getMessage());
		} finally {
			if(session != null) {
				session.close();
			} // if
		} // try-catch-finally
	} // deleteSignupTeam()

	// 팀 메인 페이지 -  팀 나가기 -> (두 작업을 하나의 트랜잭션으로 처리)
	@Override
	public void leaveTeam(Integer teamNo, String id) throws Exception {
	    SqlSession session = null;

	    try {
	        session = sqlSessionFactory.openSession();

	        // 팀 나가기 #1
	        Map<String, Object> map = new HashMap<>();
	        
	        map.put("teamNo", teamNo);
	        map.put("id", id);
	        session.update("com.my.team.TeamMapper.updateTeamMemberStatusResign", map);

	        // 팀 나가기 #2
	        session.delete("com.my.team.TeamMapper.deleteSignupTeam", map);

	        // 두 작업 모두 성공하면 커밋
	        session.commit();

	    } catch (Exception e) {
	        // 어떤 작업이든 실패하면 롤백
	        if (session != null) {
	            session.rollback();
	        }
	        throw new Exception("팀 나가기 실패: " + e.getMessage());
	    } finally {
	        if (session != null) {
	            session.close();
	        } // if
	    } // try-catch-finally
	} // leaveTeam()


	// 팀 메인 페이지 - 팀 멤버 출력하기
	@Override
	public List<String> selectNicknameByTeamNo(int teamNo) throws FindException {
		SqlSession session = null;

		try {
			session = sqlSessionFactory.openSession();

			List<String> selectedNickname = session.selectList("com.my.team.TeamMapper.selectNicknameByTeamNo", teamNo);

			if (selectedNickname != null) {
				return selectedNickname;
			} else {
				throw new FindException("선택하신 팀의 팀 멤버가 존재하지 않습니다.");
			} // if-else
		} catch(Exception e) {
			throw new FindException(e.getMessage());
		} finally {
			if(session != null) {
				session.close();
			} // if
		} // try-catch-finally
	} // selectNicknameByTeamNo()

	// 팀 메인 페이지 - 조회수 카운트
	@Override
	public void updateViewCnt(int teamNo) throws ModifyException {
		SqlSession session = null;

		try {
			session = sqlSessionFactory.openSession();

			session.update("com.my.team.TeamMapper.updateViewCnt", teamNo);
			session.commit();
		} catch(Exception e) {
			session.rollback();
			throw new ModifyException(e.getMessage());
		} finally {
			if(session != null) {
				session.close();
			} // if
		} // try-catch-finally
	} // updateTeamMemberStatus()

	// 팀 메인 페이지 - 조회수 출력
	@Override
	public int selectViewCnt(int teamNo) throws FindException {
	    SqlSession session = null;
	    
	    try {
	        session = sqlSessionFactory.openSession();
	        
	        int viewCount = session.selectOne("com.my.team.TeamMapper.selectViewCnt", teamNo);

	        return viewCount;
	    } catch(Exception e) {
	        throw new FindException(e.getMessage());
	    } finally {
	        if(session != null) {
	            session.close();
	        } // if
	    } // try-catch-finally
	} //selectViewCnt()
	
	//	---------------------------
	
	// 팀 출석부 페이지 - 출석 여부 확인
	@Override
	public String selectAttendanceDate(Map<String, Object> map) throws FindException {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			
			String dateChk = session.selectOne("com.my.team.TeamMapper.selectAttendanceDate", map);
			return dateChk;
		} catch (Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			} // if
		} // try-catch-finally
	} // selectAttendanceDate()

	// 팀 출석부 페이지 - 출석하기#1
	@Override
	public void insertAttendanceById(Map<String, Object> map) throws AddException {
		SqlSession session = null;

		try {
			session = sqlSessionFactory.openSession();
			
			session.update("com.my.team.TeamMapper.insertAttendanceById", map);
			session.commit();
		} catch(Exception e) {
			session.rollback();
			throw new AddException(e.getMessage());
		} finally {
			if(session != null) {
				session.close();
			} // if
		} // try-catch-finally
	} // insertAttendance 
	
	// 팀 출석부 페이지 - 출석하기#2
	public void updateAttendanceCnt(Map<String, Object> map) throws ModifyException {
		SqlSession session = null;

		try {
			session = sqlSessionFactory.openSession();

			session.update("com.my.team.TeamMapper.updateAttendanceCnt", map);
			session.commit();
		} catch(Exception e) {
			session.rollback();
			throw new ModifyException(e.getMessage());
		} finally {
			if(session != null) {
				session.close();
			} // if
		} // try-catch-finally
	} // updateAttendanceCnt()
	
	// 팀 출석부 페이지 - 출석하기(트랜잭션)
	public void increaseAttendanceCnt(Map<String, Object> map) throws Exception {
		SqlSession session = null;
		
		try {
			session = sqlSessionFactory.openSession();
			
			session.insert("com.my.team.TeamMapper.insertAttendanceById", map);
	        session.update("com.my.team.TeamMapper.updateAttendanceCnt", map);
	        
	        session.commit();

		} catch (Exception e) {
			session.rollback();
		} finally {
			if(session != null) {
				session.close();
			} // if
		} // try-catch-finally
		
	} // increaseAttendanceCnt()

	// 팀 출석부 페이지 - 출석 내역 조회
	@Override
	public List<AttendanceDTO> selectAttendanceById(Integer teamNo, String id) throws FindException {
		SqlSession session = null;

		List<AttendanceDTO> attendanceList = new ArrayList<>();

		try {
			session = sqlSessionFactory.openSession();

			Map<String, Object> map = new HashMap<>();

			map.put("teamNo", teamNo);
			map.put("id", id);

			attendanceList = session.selectList("com.my.team.TeamMapper.selectAttendanceById", map);

			return attendanceList;
		} catch (Exception e) {
			throw new FindException("선택하신 팀에 대한 출석 내역이 존재하지 않습니다.");
		} finally {
			if(session != null) {
				session.close();
			} // if
		} // try-catch-finally
	} //selectAttendanceById()

	//	---------------------------

	// 팀 관리 페이지(현재 팀원 관리) - 현재 팀원들 정보 확인 (아이디, 닉네임, 자기소개)
	@Override
	public List<Map<String, Object>> selectMemberInfo(Integer teamNo) throws FindException {
		SqlSession session = null;

		try {
			session = sqlSessionFactory.openSession();

			// map 객체에 teamNo 값을 넣어서 전달
			Map<String, Object> map = new HashMap<>();
			map.put("teamNo", teamNo);

			List<Map<String, Object>> selectedMemberInfo = session.selectList("com.my.team.TeamMapper.selectMemberInfo", map);

			return selectedMemberInfo;
		} catch (Exception e) {
			throw new FindException("선택하신 팀에 팀원 내역이 존재하지 않습니다.");
		} finally {
			if(session != null) {
				session.close();
			} // if
		} // try-catch-finally
	} // selectMemberInfo()
	
	@Override
	public List<Map<String, Object>> selectTeamMemberInfo(Integer teamNo) throws FindException {
		SqlSession session = null;

		try {
			session = sqlSessionFactory.openSession();

			// map 객체에 teamNo 값을 넣어서 전달
			Map<String, Object> map = new HashMap<>();
			map.put("teamNo", teamNo);

			List<Map<String, Object>> selectedMemberInfo = session.selectList("com.my.team.TeamMapper.selectTeamMemberInfo", map);

			return selectedMemberInfo;
		} catch (Exception e) {
			throw new FindException("선택하신 팀에 팀원 내역이 존재하지 않습니다.");
		} finally {
			if(session != null) {
				session.close();
			} // if
		} // try-catch-finally
	} // selectTeamMemberInfo()

	// 팀 관리 페이지(현재 팀원 관리) - 팀원 방출#1
	@Override
	public void updateTeamMemberStatusDismiss(Map<String, Object> map) throws ModifyException {
		SqlSession session = null;

		try {
			session = sqlSessionFactory.openSession();

			session.update("com.my.team.TeamMapper.updateTeamMemberStatusDismiss", map);
			session.commit();
		} catch(Exception e) {
			session.rollback();
			throw new ModifyException(e.getMessage());
		} finally {
			if(session != null) {
				session.close();
			} // if
		} // try-catch-finally
	} // updateTeamMemberStatus()
	
	// 팀 관리 페이지(현재 팀원 관리) - 팀원 방출#2
	public void deleteTeamMemberInSignupTeam(Map<String, Object> map) throws RemoveException {
		SqlSession session = null;

		try {
			session = sqlSessionFactory.openSession();

			session.delete("com.my.team.TeamMapper.deleteTeamMemberInSignupTeam", map);
			session.commit();
		} catch(Exception e){
			session.rollback();
			throw new RemoveException(e.getMessage());
		} finally {
			if(session != null) {
				session.close();
			} // if
		} // try-catch-finally
	}
	
	// 팀 관리 페이지(현재 팀원 관리) - 팀원 방출 트랜잭션
	public void dismissTeamMember(Map<String, Object> map) throws Exception {
		SqlSession session = null;

	    try {
	        session = sqlSessionFactory.openSession();

	        session.update("com.my.team.TeamMapper.updateTeamMemberStatusDismiss", map);
	        session.insert("com.my.team.TeamMapper.deleteTeamMemberInSignupTeam", map);

	        // 두 작업 모두 성공하면 커밋
	        session.commit();

	    } catch (Exception e) {
	        // 어떤 작업이든 실패하면 롤백
	        if (session != null) {
	            session.rollback();
	        }
	        throw new Exception("팀원 방출 실패" + e.getMessage());
	    } finally {
	        if (session != null) {
	            session.close();
	        } // if
	    } // try-catch-finally
	} // dismissTeamMember()

	// 팀 관리 페이지(가입 요청 관리) - 팀 가입 요청 확인
	@Override
	public List<Map<String, Object>> selectRequestInfo(Integer teamNo) throws FindException {
		SqlSession session = null;

		try {
			session = sqlSessionFactory.openSession();

			List<Map<String, Object>> requestInfo = session.selectList("com.my.team.TeamMapper.selectRequestInfo", teamNo);

			return requestInfo;
		} catch (Exception e) {
			throw new FindException(e.getMessage());
		} finally {
			if(session != null) {
				session.close();
			} // if
		} // try-catch-finally
	} // selectRequestInfo()

	// 팀 관리 페이지(가입 요청 관리) - 팀 가입 요청 승인1
	@Override
	public void updateRequestInfoApprove(Map<String, Object> map) throws ModifyException {
		SqlSession session = null;

		try {
			session = sqlSessionFactory.openSession();

			session.update("com.my.team.TeamMapper.updateRequestInfoApprove", map);
			session.commit();
		} catch(Exception e) {
			session.rollback();
			throw new ModifyException(e.getMessage());
		} finally {
			if(session != null) {
				session.close();
			} // if
		} // try-catch-finally
	} // updateRequestInfoApprove()
	
	// 팀 관리 페이지(가입 요청 관리) - 팀 가입 요청 승인2
	@Override
	public void insertRequestInfoApprove(Map<String, Object> map) throws AddException {
		SqlSession session = null;

		try {
			session = sqlSessionFactory.openSession();

			session.insert("com.my.team.TeamMapper.insertRequestInfoApprove", map);
			session.commit();
		} catch(Exception e) {
			session.rollback();
			throw new AddException(e.getMessage());
		} finally {
			if(session != null) {
				session.close();
			} // if
		} // try-catch-finally
	} // updateRequestInfoApprove()
	
	// 팀 관리 페이지(가입 요청 관리) - 팀 가입 요청 승인 -> (두 작업을 하나의 트랜잭션으로 처리)
	@Override
	public void approveRequest(Map<String, Object> map) throws Exception {
	    SqlSession session = null;

	    try {
	        session = sqlSessionFactory.openSession();

	        // 얘가 탈퇴했는지 안했는지 먼저 확인
	        Integer status = session.selectOne("com.my.team.TeamMapper.selectLeaveTeamMemberStatus", map);

	        // 사용자의 데이터가 없거나 status가 0 또는 3인 경우에만 삽입을 진행
	        if (status == null || status == 0 || status == 3) {
	            session.update("com.my.team.TeamMapper.updateRequestInfoApprove", map);

	            if (status != null && (status == 0 || status == 3)) {
	                // 이미 데이터가 있는데 status가 0 또는 3인 경우, 데이터를 업데이트!
	                session.update("com.my.team.TeamMapper.updateMemberStatus", map);
	            } else {
	                session.insert("com.my.team.TeamMapper.insertRequestInfoApprove", map);
	            } // if-else
	        } else {
	            // 사용자의 데이터가 이미 있고 status가 1이나 2인 경우
	            throw new Exception("이미 팀에 가입 중이거나 탈퇴 상태가 아닌 사용자입니다.");
	        } // if-else

	        session.commit();
	    } catch (Exception e) {
	        if (session != null) {
	            session.rollback();
	        }
	        throw new Exception("팀 가입 요청 승인 실패: " + e.getMessage());
	    } finally {
	        if (session != null) {
	            session.close();
	        }
	    }
	} // approveRequest()

	// 팀 관리 페이지(가입 요청 관리) - 팀 가입 요청 거절
	@Override
	public void updateRequestInfoReject(Map<String, Object> map) throws ModifyException {
		SqlSession session = null;

		try {
			session = sqlSessionFactory.openSession();

			session.update("com.my.team.TeamMapper.updateRequestInfoReject", map);
			session.commit();
		} catch(Exception e) {
			session.rollback();
			throw new ModifyException(e.getMessage());
		} finally {
			if(session != null) {
				session.close();
			} // if
		} // try-catch-finally
	} // updateRequestInfoReject()

	// 팀 관리 페이지(출제자 선정) - 출제자 선정
	@Override
	public void insertExaminer(TaskDTO taskDTO, Integer teamNo) throws ModifyException {
		SqlSession session = null;

		try {
			session = sqlSessionFactory.openSession();
			
			Map map = new HashMap<>();
			map.put("TaskDTO", taskDTO);
			map.put("teamNo", teamNo);
			
			session.insert("com.my.team.TeamMapper.insertExaminer", map);
			session.commit();
		} catch(Exception e) {
			session.rollback();
			throw new ModifyException(e.getMessage());
		} finally {
			if(session != null) {
				session.close();
			} // if
		} // try-catch-finally
	} // insertExaminer()
	
	@Override
	public List<Map<String, Object>> selectExaminer(Integer teamNo) throws FindException {
		SqlSession session = null;

		try {
			session = sqlSessionFactory.openSession();

			List<Map<String, Object>> examinerInfo = session.selectList("com.my.team.TeamMapper.selectExaminer", teamNo);

			return examinerInfo;
		} catch (Exception e) {
			throw new FindException(e.getMessage());
		} finally {
			if(session != null) {
				session.close();
			} // if
		} // try-catch-finally
	} // selectExaminer

//	################ Test ################
	
	public static void main(String[] args) throws FindException {
		TeamDAOImpl t=new TeamDAOImpl();
		// 근데 생각해보면 나 메소드 넘 많은데 이거 테스트 언제 다 해바...? 눈물 줄줄
		Map<String, Object> map=new HashMap<>();
		map.put("teamNo", 9999);
		map.put("id", "psh2023");

		String s=t.selectAttendanceDate(map);
	}
	
} // end class
