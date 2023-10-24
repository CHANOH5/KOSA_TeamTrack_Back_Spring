package com.my.customer.service;

import com.my.customer.dto.CustomerDTO;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;

public interface CustomerService {

	/**
	 * 회원가입을 한다
	 * @param customerDTO 회원이 작성한 고객데이터
	 * @throws AddException db와 연결 실패시 예외 발생
	 */
	public void singup(CustomerDTO customerDTO) throws AddException;

	/**
	 * 로그인을 한다
	 * @param db에 저장된 id값
	 * @param pwd db에 저장된 pwd값
	 * @throws FindException db에 id와 pwd에 해당하는 값이 없을시 예외 발생
	 */
	// 로그인
	public void login(String id, String pwd) throws FindException;

	/**
	 * 아이디 중복확인을 한다
	 * @param id db에 저장된 id값
	 * @throws FindException 아이디에 해당하는 고객이 존재하지 않으면 FindException 발생
	 */
	// 아이디 중복체크
	public void idDubCheck(String id) throws FindException;
	
	//서현 추가
	/**
	 * 로그인 시 닉네임을 조회한다
	 * @param id 로그인 아이디
	 * @return 닉네임
	 * @throws FindException 아이디에 해당하는 닉네임이 존재하지 않으면 FindException 발생
	 */
	public CustomerDTO selectNickName(String id) throws FindException;
	
	
	//----------------- 원희 -----------------------
	
	/**
	 * 회원정보를 조회한다
	 * @author 나원희
	 * @param id
	 * @return
	 * @throws FindException
	 */
	CustomerDTO findById(String id) throws FindException;
	
	/**
	 * 닉네임이 존재하는지 확인한다
	 * @author 나원희
	 * @param nickname 닉네임
	 * @throws FindException 해당되는 닉네임이 존재하지 않는 경우, DB연결 실패 시 예외 발생한다
	 */
	void nicknameDupChk(String nickname) throws FindException;
	
	/**
	 * 닉네임을 변경한다
	 * @author 나원희
	 * @param id 사용자 아이디
	 * @param nickname 닉네임
	 * @throws FindException DB연결 실패 시 예외 발생한다
	 */
	void modifyNickname(String id, String nickname) throws ModifyException;
	
	/**
	 * 아이디, 비밀번호, 닉네임 외 정보를 변경한다
	 * @author 나원희
	 * @param customer 변경할 정보
	 * @throws ModifyException DB연결 실패 시 예외 발생한다
	 */
	void modifyMyInfo(String id, CustomerDTO customer) throws ModifyException;
	
	/**
	 * 회원 탈퇴를 한다
	 * @author 나원희
	 * @param id 사용자 아이디
	 * @throws ModifyException DB연결 실패 시 예외 발생한다
	 * @throws FindException DB연결 실패 시 예외 발생한다
	 * @throws RemoveException DB연결 실패 시 예외 발생한다
	 */
	void deleteAccount(String id) throws ModifyException, FindException, RemoveException;
	
	/**
	 * 회원 아이디에 해당하는 비밀번호가 일치하는지 비교한다
	 * @author 나원희
	 * @param id 사용자 아이디
	 * @param pwd 비밀번호
	 * @return 일치하는지 여부
	 * @throws FindException DB연결 실패 시 예외 발생한다
	 */
	boolean pwdCheck(String id, String pwd) throws FindException;
	
	/**
	 * 비밀번호를 변경한다
	 * @author 나원희
	 * @param id 사용자 아이디
	 * @param pwd 변경할 비밀번호
	 * @throws ModifyException DB연결 실패 시 예외 발생한다
	 */
	void editMyPwd(String id, String pwd) throws ModifyException;

} // end class
