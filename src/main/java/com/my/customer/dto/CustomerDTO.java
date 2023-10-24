	package com.my.customer.dto;
	
	import java.util.Date;
	
	import lombok.AllArgsConstructor;
	import lombok.Getter;
	import lombok.NoArgsConstructor;
	import lombok.Setter;
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public class CustomerDTO {
	
		private String id;				// 유저 아이디
		private String pwd;				// 유저 비밀번호
		private String nickname;		// 유저 닉네임
		private String name;			// 유저 이름
		private String birthday;		// 유저 생년월일
		private String phone;			// 유저 번호
		private String email;			// 유저 이메일
		private Integer status;			// 유저 상태 -> 탈퇴: 0, 가입중: 1
		
		public CustomerDTO(String name, String birthday, String phone, String email){
			this.name = name;
			this.birthday = birthday;
			this.phone = phone;
			this.email = email;
		}
	
	} // end class
