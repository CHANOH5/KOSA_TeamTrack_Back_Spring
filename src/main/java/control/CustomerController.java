package control;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.my.customer.dto.CustomerDTO;
import com.my.customer.service.CustomerServiceImpl;
import com.my.exception.AddException;
import com.my.exception.FindException;

@RestController
@RequestMapping()
public abstract class CustomerController {
	
	@Autowired
	protected CustomerServiceImpl impl;
	
	@PostMapping("/signup")
	public void singup(CustomerDTO dto, MultipartFile file) throws IOException {
		Map<String, Object> map = new HashMap<>();

		try {

//			Attach attach = new Attach(req);

			// 요청전달 데이터 담을 dto 객체 생성
//			CustomerDTO dto = new CustomerDTO(id, pwd, nickname, name, birthday, phone, email, null);

			impl.singup(dto);

//			try {
//				String originProfileFileName = attach.getFile("file1").get(0).getName();
//				attach.upload("file1", id + "_userprofile_" + originProfileFileName);
//			}catch(Exception e) {
//
//			} // try-catch
//			
			if(file != null && file.getSize() > 0) {
				
				
				File targetFile = new File("C:\\KOSA202307\\attaches", dto.getId() + "_profile_" + file.getOriginalFilename());
				FileCopyUtils.copy(file.getBytes(), targetFile);

			}
			

			map.put("status", 1);
			map.put("msg", dto.getName() +"회원님 환영합니다");

		} catch (AddException e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", "회원가입 실패");
		} // try-catch
		
	} // singup
	
	@PostMapping("/iddupcheck")
	public void idDupCheck(String id) {
		
		Map<String, Object> map = new HashMap<>();

		try {
			impl.idDubCheck(id);
			// 고객이 있는 경우
			map.put("status", 0);
		} catch (FindException e) {
			// 고객이 없는 경우
			map.put("status", 1);
		} // try-catch
		
	} // idDupCheck
	
	@PostMapping("/login")
	public void login(String id, String pwd, HttpServletRequest req, HttpServletResponse res) {
		
	      Map<String, Object> map = new HashMap<>();

	      
	      HttpSession session = req.getSession();
	      // attribute가 있으면 제거함 
	      session.removeAttribute("loginedId");

			try {
				impl.login(id, pwd);

				CustomerDTO dto = impl.selectNickName(id);
				
				if(dto != null ) {
					String dbPwd = dto.getPwd();

					if(pwd.equals(dbPwd)) {
						int memberstatus = dto.getStatus();
						
						if (memberstatus == 1) {
							map.put("id", id);
							map.put("status", 0);
							map.put("msg", "로그인 되었습니다");
							session.setAttribute("loginedId", id);
						} else {
							map.put("status", 2); // 탈퇴된 회원 상태
							map.put("msg", "탈퇴된 회원입니다");
						}
						if (memberstatus == 1) {
							map.put("id", id);
							session.setAttribute("loginedId", id);
						}
						
						System.out.println("Session ID: " + session.getId());
						System.out.println(session.getAttribute("loginedId"));
						//서현 추가(로그인 시 닉네임도 저장)
						CustomerDTO customerDTO = impl.selectNickName(id);
						String nickname = customerDTO.getNickname();
						map.put("nickname", nickname);
						//
					} else {
						map.put("status", 1);
						map.put("msg", "다시 로그인 해주세요.");
					}
				} // if	
		        
					System.out.println(session);
				} catch (FindException e) {
	         e.printStackTrace();
	         map.put("status", 1);
	         map.put("msg", "다시 로그인해주세요.");
	      }
		
	}

//	@GetMapping("/logout")
//	public void logout(HttpSession session) {
//			session.removeAttribute("loginedId");
//			session.invalidate();
//	}
		
	

} // end class