package com.my.notice.dao;

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

public class NoticeDAOImpl implements NoticeDAO{
	private SqlSessionFactory sqlSessionFactory;

	public NoticeDAOImpl() {
		String resource = "com/my/sql/mybatis-config.xml";
		InputStream inputStream;
		try{
			inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<NoticeDTO> selectNoticeAll(int startRow, int endRow, Integer teamNo) throws FindException{
		SqlSession session = null;
		List<NoticeDTO> noticeList = new ArrayList<>();
		String tableName = "NOTICEBOARD_"+ String.valueOf(teamNo);

		try{
			session = sqlSessionFactory.openSession();
			Map map = new HashMap<>();
			map.put("start", startRow);
			map.put("end", endRow);
			map.put("tableName", tableName);
			noticeList = session.selectList("com.my.notice.NoticeMapper.selectNoticeAll", map);
			return noticeList;
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
	public int selectNoticeCount(Integer teamNo) throws FindException{
		SqlSession session = null;
		String tableName = "NOTICEBOARD_"+ String.valueOf(teamNo);
		try {
			session = sqlSessionFactory.openSession();
			int count = session.selectOne("com.my.notice.NoticeMapper.selectNoticeCount", tableName);
			return count;
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
	public NoticeDTO selectByNoticeNo(Integer teamNo, Integer noticeNo) throws FindException{
		SqlSession session = null;
		String tableName = "NOTICEBOARD_"+ String.valueOf(teamNo);
		Map map = new HashMap<>();
		NoticeDTO notice = new NoticeDTO();

		try {
			session = sqlSessionFactory.openSession();
			map.put("tableName",tableName);
			map.put("noticeNo", noticeNo);
			notice = session.selectOne("com.my.notice.NoticeMapper.selectByNoticeNo", map);
			return notice;
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
	public Integer insertNotice(Integer teamNo, NoticeDTO notice) throws AddException{
		SqlSession session = null;
		Map map = new HashMap<>();
		Integer noticeNo;

		try {
			session = sqlSessionFactory.openSession();
			map.put("teamNo", teamNo);
			map.put("notice", notice);
			session.insert("com.my.notice.NoticeMapper.insertNotice", map);
			noticeNo = notice.getNoticeNo();			
			session.commit();
			return noticeNo;
		}catch(Exception e) {
			session.rollback();
			throw new AddException(e.getMessage());
		}finally {
			if(session!=null) {
				session.close();
			}
		}
	}

	@Override
	public void deleteNotice(Integer teamNo, Integer noticeNo) throws RemoveException{
		SqlSession session = null;
		Map map = new HashMap<>();
		String tableName = "NOTICEBOARD_"+ String.valueOf(teamNo);

		try {
			session = sqlSessionFactory.openSession();
			map.put("tableName", tableName);
			map.put("noticeNo", noticeNo);
			session.delete("com.my.notice.NoticeMapper.deleteNotice", map);
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
	public void updateNotice(Integer teamNo, NoticeDTO notice) throws ModifyException{
		SqlSession session = null;
		String tableName = "NOTICEBOARD_"+ String.valueOf(teamNo);
		Map map = new HashMap<>();

		try{
			session = sqlSessionFactory.openSession();
			map.put("tableName", tableName);
			map.put("notice", notice);
			session.update("com.my.notice.NoticeMapper.updateNotice", map);
			session.commit();
		}catch(Exception e) {
			session.rollback();
			throw new ModifyException(e.getMessage());
		}finally {
			if(session!=null) {
				session.close();
			}
		}
	}

	@Override
	public NoticeDTO selectMainNotice(Integer teamNo) throws FindException{
		SqlSession session = null;
		String tableName = "NOTICEBOARD_"+ String.valueOf(teamNo);
		Map map = new HashMap<>();
		NoticeDTO notice = new NoticeDTO();

		try {
			session = sqlSessionFactory.openSession();
			notice = session.selectOne("com.my.notice.NoticeMapper.selectMainNotice", tableName);
			return notice;
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
	public void updateMainStatus(Integer teamNo, Integer noticeNo, Integer mainStatus) throws ModifyException{
		SqlSession session = null;
		String tableName = "NOTICEBOARD_"+ String.valueOf(teamNo);
		Map map = new HashMap<>();

		try{
			session = sqlSessionFactory.openSession();
			map.put("tableName", tableName);
			map.put("noticeNo", noticeNo);
			map.put("mainStatus", mainStatus);
			session.update("com.my.notice.NoticeMapper.updateMainStatus", map);
			session.commit();
		}catch(Exception e) {
			session.rollback();
			throw new ModifyException(e.getMessage());
		}finally {
			if(session!=null) {
				session.close();
			}
		}
	}
}
