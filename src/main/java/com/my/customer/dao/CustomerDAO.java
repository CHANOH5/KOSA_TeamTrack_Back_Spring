package com.my.customer.dao;

import com.my.customer.dto.CustomerDTO;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;

public interface CustomerDAO {

	/**
	 * 회원을 추가한다
	 * @param customerDTO 고객객체
	 * @throws AddException DB와의 연결 실패 또는 제약조건에 위반시 예외 발생
	 */
	void create(CustomerDTO customerDTO) throws AddException;

	/**
	 * id에 해당하는 고객을 검색한다
	 * @param id
	 * @return db에 저장된 고객객체를 반환
	 * @throws FindException 아이디에 해당하는 고객 객체가 없거나 DB연결 실패시 예외 발생
	 */
	CustomerDTO selectById(String id) throws FindException;
	
	
	//----------------------------------------------------------------------------------------------
	
	/**
	 * 닉네임을 조회한다
	 * @author 나원희
	 * @param nickname 닉네임
	 * @throws FindException 해당 닉네임이 존재하지 않을 경우와 DB연결 실패 시 예외 발생한다
	 */
	CustomerDTO selectByNickname(String nickname) throws FindException;
	
	/**
	 * 닉네임을 변경한다
	 * @author 나원희
	 * @param id 사용자 아이디
	 * @param nickname 닉네임
	 * @throws FindException DB연결 실패 시 예외 발생한다
	 */
	void updateNickname(String id, String nickname) throws ModifyException;
	
	/**
	 * 아이디, 비밀번호, 닉네임 외 정보를 변경한다
	 * @author 나원희
	 * @param customer 변경할 정보
	 * @throws ModifyException DB연결 실패 시 예외 발생한다
	 */
	void updateCustomerAll(String id, CustomerDTO customer) throws ModifyException;
	
	/**
	 * 회원 상태를 변경한다
	 * @author 나원희
	 * @param id 사용자 아이디
	 * @throws ModifyException DB연결 실패 시 예외 발생한다
	 */
	void updateCustomerStatus(String id) throws ModifyException;
	
	/**
	 * 비밀번호를 변경한다
	 * @author 나원희
	 * @param id 사용자 아이디
	 * @param pwd 변경할 비밀번호
	 * @throws ModifyException DB연결 실패 시 예외 발생한다
	 */
	void updatePwd(String id, String pwd) throws ModifyException;

}
