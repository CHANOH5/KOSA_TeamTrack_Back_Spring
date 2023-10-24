package com.my.task.dao;

import java.io.InputStream;
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
import com.my.task.dto.MemberTaskDTO;
import com.my.task.dto.TaskDTO;

public class TaskDAOImpl implements TaskDAO {
	private SqlSessionFactory sqlSessionFactory;

	public TaskDAOImpl() {
		String resource="com/my/sql/mybatis-config.xml";
		InputStream inputStream;

		try {
			inputStream=Resources.getResourceAsStream(resource);
			sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<TaskDTO> selectMainTaskList(Integer teamNo, String id) throws FindException {
		SqlSession session=null;

		try {
			session=sqlSessionFactory.openSession();
			Map<String, Object> map=new HashMap<>();
			map.put("tableName1", "task_"+teamNo);
			map.put("tableName2", "memberscore_"+teamNo);
			map.put("id", id);
			List<TaskDTO> list=session.selectList("com.my.task.TaskMapper.selectMainTaskList", map);
			return list;
		} catch(Exception e) {
			//e.printStackTrace();
			throw new FindException("메인 과제 리스트 페이지 조회 실패");
		} finally {
			if(session!=null) session.close();
		}
	}

	@Override
	public List<TaskDTO> selectAllTaskList(Integer teamNo, int startRow, int endRow, boolean desc) throws FindException {
		SqlSession session=null;

		try {
			session=sqlSessionFactory.openSession();
			Map<String, Object> map=new HashMap<>();
			map.put("tableName", "task_"+teamNo);
			map.put("desc", desc);
			map.put("start", startRow);
			map.put("end", endRow);
			List<TaskDTO> list=session.selectList("com.my.task.TaskMapper.selectAllTaskList", map);
			return list;
		} catch(Exception e) {
			throw new FindException("전체 과제 리스트 페이지 조회 실패");
		} finally {
			if(session!=null) session.close();
		}
	}

	@Override
	public int selectAllTaskCount(Integer teamNo) throws FindException {
		SqlSession session=null;

		try {
			session=sqlSessionFactory.openSession();
			String tableName="task_"+teamNo;
			int count=session.selectOne("com.my.task.TaskMapper.selectAllTaskCount", tableName);
			return count;
		} catch(Exception e) {
			throw new FindException("전체 과제 개수 조회 실패");
		} finally {
			if(session!=null) session.close();
		}
	}

	@Override
	public List<MemberTaskDTO> selectCompleteTaskList(Integer teamNo, String id, int startRow, int endRow, boolean desc) throws FindException {
		SqlSession session=null;

		try {
			session=sqlSessionFactory.openSession();
			Map<String, Object> map=new HashMap<>();
			map.put("tableName1", "memberscore_"+teamNo);
			map.put("tableName2", "task_"+teamNo);
			map.put("id", id);
			map.put("desc", desc);
			map.put("start", startRow);
			map.put("end", endRow);
			List<MemberTaskDTO> list=session.selectList("com.my.task.TaskMapper.selectCompleteTaskList", map);
			return list;
		} catch(Exception e) {
			throw new FindException("완료한 과제 리스트 페이지 조회 실패");
		} finally {
			if(session!=null) session.close();
		}
	}

	@Override
	public int selectCompleteTaskCount(Integer teamNo, String id) throws FindException {
		SqlSession session=null;

		try {
			session=sqlSessionFactory.openSession();
			Map<String, Object> map=new HashMap<>();
			map.put("tableName", "memberscore_"+teamNo);
			map.put("id", id);
			int count=session.selectOne("com.my.task.TaskMapper.selectCompleteTaskCount", map);
			return count;
		} catch(Exception e) {
			throw new FindException("완료한 과제 개수 조회 실패");
		} finally {
			if(session!=null) session.close();
		}
	}

	@Override
	public List<TaskDTO> selectMyTaskList(Integer teamNo, String id, int startRow, int endRow, boolean desc) throws FindException {
		SqlSession session=null;

		try {
			session=sqlSessionFactory.openSession();
			Map<String, Object> map=new HashMap<>();
			map.put("tableName", "task_"+teamNo);
			map.put("id", id);
			map.put("desc", desc);
			map.put("start", startRow);
			map.put("end", endRow);
			List<TaskDTO> list=session.selectList("com.my.task.TaskMapper.selectMyTaskList", map);
			return list;
		} catch(Exception e) {
			throw new FindException("출제한 과제 리스트 페이지 조회 실패");
		} finally {
			if(session!=null) session.close();
		}
	}

	@Override
	public int selectMyTaskCount(Integer teamNo, String id) throws FindException {
		SqlSession session=null;

		try {
			session=sqlSessionFactory.openSession();
			Map<String, Object> map=new HashMap<>();
			map.put("tableName", "task_"+teamNo);
			map.put("id", id);
			int count=session.selectOne("com.my.task.TaskMapper.selectMyTaskCount", map);
			return count;
		} catch(Exception e) {
			throw new FindException("출제한 과제 개수 조회 실패");
		} finally {
			if(session!=null) session.close();
		}
	}

	@Override
	public TaskDTO selectTaskInfo(Integer teamNo, Integer taskNo) throws FindException {
		SqlSession session=null;

		try {
			session=sqlSessionFactory.openSession();
			Map<String, Object> map=new HashMap<>();
			map.put("tableName", "task_"+teamNo);
			map.put("taskNo", taskNo);
			TaskDTO task=session.selectOne("com.my.task.TaskMapper.selectTaskInfo", map);
			return task;
		} catch(Exception e) {
			throw new FindException("과제 정보 조회 실패");
		} finally {
			if(session!=null) session.close();
		}
	}

	@Override
	public List<Integer> selectQuizAnswer(Integer teamNo, Integer taskNo) throws FindException {
		SqlSession session=null;

		try {
			session=sqlSessionFactory.openSession();
			Map<String, Object> map=new HashMap<>();
			map.put("tableName", "quizanswer_"+teamNo);
			map.put("taskNo", taskNo);
			List<Integer> answer=session.selectList("com.my.task.TaskMapper.selectQuizAnswer", map);
			return answer;
		} catch(Exception e) {
			throw new FindException("과제 답안 조회 실패");
		} finally {
			if(session!=null) session.close();
		}
	}

	@Override
	public List<Integer> selectMemberAnswer(Integer teamNo, Integer taskNo, String id) throws FindException {
		SqlSession session=null;

		try {
			session=sqlSessionFactory.openSession();
			Map<String, Object> map=new HashMap<>();
			map.put("tableName", "memberanswer_"+teamNo);
			map.put("taskNo", taskNo);
			map.put("id", id);
			List<Integer> answer=session.selectList("com.my.task.TaskMapper.selectMemberAnswer", map);
			return answer;
		} catch(Exception e) {
			throw new FindException("팀원 답안 조회 실패");
		} finally {
			if(session!=null) session.close();
		}
	}

	@Override
	public int selectMemberScore(Integer teamNo, Integer taskNo, String id) throws FindException {
		SqlSession session=null;

		try {
			session=sqlSessionFactory.openSession();
			Map<String, Object> map=new HashMap<>();
			map.put("tableName", "memberscore_"+teamNo);
			map.put("taskNo", taskNo);
			map.put("id", id);
			int score=session.selectOne("com.my.task.TaskMapper.selectMemberScore", map);
			return score;
		} catch(Exception e) {
			throw new FindException("과제 점수 조회 실패");
		} finally {
			if(session!=null) session.close();
		}
	}

	@Override
	public void updateTask(Integer teamNo, String title, Integer taskNo) throws ModifyException {
		SqlSession session=null;

		try {
			session=sqlSessionFactory.openSession();
			Map<String, Object> map=new HashMap<>();
			map.put("tableName", "task_"+teamNo);
			map.put("title", title);
			map.put("taskNo", taskNo);
			session.update("com.my.task.TaskMapper.updateTask", map);
			session.commit();
		} catch(Exception e) {
			session.rollback();
			e.printStackTrace();
			throw new ModifyException("과제 업데이트 실패");
		} finally {
			if(session!=null) session.close();
		}
	}

	@Override
	public void insertQuizAnswer(Integer teamNo, Integer questionNo, Integer taskNo, int answer) throws AddException {
		SqlSession session=null;

		try {
			session=sqlSessionFactory.openSession();
			Map<String, Object> map=new HashMap<>();
			map.put("tableName", "quizanswer_"+teamNo);
			map.put("questionNo", questionNo);
			map.put("taskNo", taskNo);
			map.put("answer", answer);
			session.insert("com.my.task.TaskMapper.insertQuizAnswer", map);
			session.commit();
		} catch(Exception e) {
			session.rollback();
			throw new AddException("답안 생성 실패");
		} finally {
			if(session!=null) session.close();
		}
	}

	public List<TaskDTO> selectTaskId(Integer teamNo) throws FindException {
		SqlSession session=null;

		try {
			session=sqlSessionFactory.openSession();
			List<TaskDTO> list=session.selectList("com.my.task.TaskMapper.selectTaskId", "task_"+teamNo);
			return list;
		} catch(Exception e) {
			//e.printStackTrace();
			throw new FindException("아이디 조회 실패");
		} finally {
			if(session!=null) session.close();
		}
	}

	public int selectAnswerCount(Integer teamNo, Integer taskNo) throws FindException {
		SqlSession session=null;

		try {
			session=sqlSessionFactory.openSession();
			Map<String, Object> map=new HashMap<>();
			map.put("tableName", "quizanswer_"+teamNo);
			map.put("taskNo", taskNo);
			int cnt=session.selectOne("com.my.task.TaskMapper.selectAnswerCount", map);
			return cnt;
		} catch(Exception e) {
			//e.printStackTrace();
			throw new FindException("답 개수 조회 실패");
		} finally {
			if(session!=null) session.close();
		}
	}

	public void insertMemberAnswer(Integer teamNo, Integer questionNo, Integer taskNo, String id, int answer) throws AddException {
		SqlSession session=null;

		try {
			session=sqlSessionFactory.openSession();
			Map<String, Object> map=new HashMap<>();
			map.put("tableName", "memberanswer_"+teamNo);
			map.put("questionNo", questionNo);
			map.put("taskNo", taskNo);
			map.put("id", id);
			map.put("answer", answer);
			session.insert("com.my.task.TaskMapper.insertMemberAnswer", map);
			session.commit();
		} catch(Exception e) {
			session.rollback();
			e.printStackTrace();
			throw new AddException("답안 생성 실패");
		} finally {
			if(session!=null) session.close();
		}
	}

	public void insertMemberScore(Integer teamNo, Integer taskNo, String id, int hwscore, int reviewScore) throws AddException {
		SqlSession session=null;

		try {
			session=sqlSessionFactory.openSession();
			Map<String, Object> map=new HashMap<>();
			map.put("tableName", "memberscore_"+teamNo);
			map.put("taskNo", taskNo);
			map.put("id", id);
			map.put("hwscore", hwscore);
			map.put("reviewScore", reviewScore);
			session.insert("com.my.task.TaskMapper.insertMemberScore", map);
			session.commit();
		} catch(Exception e) {
			session.rollback();
			e.printStackTrace();
			throw new AddException("과제 정보 생성 실패");
		} finally {
			if(session!=null) session.close();
		}
	}
	
	public void updateReviewScore(Integer teamNo, Integer taskNo, String id, int reviewScore) throws ModifyException {
		SqlSession session=null;

		try {
			session=sqlSessionFactory.openSession();
			Map<String, Object> map=new HashMap<>();
			map.put("tableName", "memberscore_"+teamNo);
			map.put("taskNo", taskNo);
			map.put("id", id);
			map.put("reviewScore", reviewScore);
			session.update("com.my.task.TaskMapper.updateReviewScore", map);
			session.commit();
		} catch(Exception e) {
			session.rollback();
			e.printStackTrace();
			throw new ModifyException("평점 업데이트 실패");
		} finally {
			if(session!=null) session.close();
		}
	}
	
	public void updateAvgReviewScore(Integer teamNo, Integer taskNo) throws ModifyException {
		SqlSession session=null;

		try {
			session=sqlSessionFactory.openSession();
			Map<String, Object> map=new HashMap<>();
			map.put("tableName1", "task_"+teamNo);
			map.put("tableName2", "memberscore_"+teamNo);
			map.put("taskNo", taskNo);
			session.update("com.my.task.TaskMapper.updateAvgReviewScore", map);
			session.commit();
		} catch(Exception e) {
			session.rollback();
			e.printStackTrace();
			throw new ModifyException("평균 평점 업데이트 실패");
		} finally {
			if(session!=null) session.close();
		}
	}


	// main test
	//	public static void main(String[] args) throws FindException, AddException {
	//		TaskDAOImpl t=new TaskDAOImpl();
	//		try {
	//			System.out.println("======================\n메인과제리스트");
	//			List<TaskDTO> list4=t.selectMainTaskList(9999, "");
	//			for(int i=0;i<list4.size();i++) {
	//				System.out.print("과제 타이틀 : "+list4.get(i).getTitle()+" | ");
	//				System.out.print("출제자 : "+list4.get(i).getId()+" | ");
	//				System.out.print("과제 생성일 : "+list4.get(i).getRegdate()+" | ");
	//				System.out.println("과제 마감일 : "+list4.get(i).getEnddate());
	//			}
	//			System.out.print("======================\n전체과제리스트 - ");
	//			int cnt=t.selectAllTaskCount(9999);
	//			System.out.println(cnt);
	//			List<TaskDTO> list=t.selectAllTaskList(9999, 1, 4, true);
	//			for(int i=0;i<list.size();i++) {
	//				System.out.print("과제 타이틀 : "+list.get(i).getTitle()+" | ");
	//				System.out.print("출제자 : "+list.get(i).getId()+" | ");
	//				System.out.println("과제 생성일 : "+list.get(i).getRegdate());
	//			}
	//			System.out.print("======================\n완료과제리스트 - ");
	//			int cnt2=t.selectCompleteTaskCount(9999, "nwh2023");
	//			System.out.println(cnt2);
	//			List<MemberTaskDTO> list2=t.selectCompleteTaskList(9999, "nwh2023", 1, 2, true);
	//			for(int i=0;i<list2.size();i++) {
	//				System.out.print("과제 타이틀 : "+list2.get(i).getTitle()+" | ");
	//				System.out.print("출제자 : "+list2.get(i).getId()+" | ");
	//				System.out.println("과제 제출일 : "+list2.get(i).getSubmitDate());
	//			}
	//			System.out.print("======================\n출제과제리스트 - ");
	//			int cnt3=t.selectMyTaskCount(9999, "khb2023");
	//			System.out.println(cnt3);
	//			List<TaskDTO> list3=t.selectMyTaskList(9999, "khb2023", 1, 2, true);
	//			for(int i=0;i<list3.size();i++) {
	//				System.out.print("과제 타이틀 : "+list3.get(i).getTitle()+" | ");
	//				System.out.println("평점 : "+list3.get(i).getAvgReviewscore());
	//			}
	//			System.out.println("======================\n선택한 과제");
	//			TaskDTO task=t.selectTaskInfo(9999, 1);
	//			List<Integer> qa=t.selectQuizAnswer(9999, 1);
	//			List<Integer> ma=t.selectMemberAnswer(9999, 1, "nwh2023");
	//			System.out.println(task.getTitle()+"	출제자 : "+task.getId()+" | 과제생성일 : "+task.getRegdate());
	//			for(int i=0;i<qa.size();i++) {
	//				System.out.println("과제 답 : "+qa.get(i)+" / 팀원 답 : "+ma.get(i));
	//			}
	//			int score=t.selectMemberScore(9999, 1, "nwh2023");
	//			System.out.println("점수 : "+score); //풀지 않았을 경우 예외처리
	////			System.out.println("======================\\n과제 업데이트");
	////			t.updateTask(9999, "test exam1", "2023-10-20", 10);
	////			System.out.println("======================\\n답안 생성");
	////			//t.insertQuizAnswer(9999, 1, 10, 4);
	////			System.out.println("======================\\n답안 수정");
	////			//t.updateQuizAnswer(9999, 1, 10, 1);
	////			System.out.println("======================\\n답안 삭제");
	////			//t.deleteQuizAnswer(9999, 1, 10);
	//			System.out.println("======================\n출제자 아이디");
	//			List<String> id=t.selectTaskId(9999);
	//			for(int i=0;i<id.size();i++) System.out.print(id.get(i)+" ");
	//		} catch (Exception e) {
	//			//e.printStackTrace();
	//			System.out.println(e.getMessage());
	//		}
	//	}

	public Integer selectJoinAfterTaskCount(String id, Integer teamNo) throws FindException{
		SqlSession session = null;
		Map map = new HashMap<>();
		String tableName = "TASK_"+ String.valueOf(teamNo);
		String tableName2 = "TEAMMEMBER_"+ String.valueOf(teamNo);
		Integer count =0;

		try {
			session = sqlSessionFactory.openSession();
			map.put("id", id);
			map.put("tableName", tableName);
			map.put("tableName2", tableName2);
			count = session.selectOne("com.my.task.TaskMapper.selectJoinAfterTaskCount", map);
			if(count == null) { 
				count = 0;
				return count;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(session != null) {
				session.close();
			}
		}
		return count;
	}
}
