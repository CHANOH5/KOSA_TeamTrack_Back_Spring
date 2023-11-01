package control;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.customer.service.CustomerServiceImpl;

@RestController
@RequestMapping("/main")
public abstract class CustomerController {
	
	@Autowired
	protected CustomerServiceImpl impl;



	@GetMapping("/logout")
	public void logout(HttpSession session) {
			session.removeAttribute("loginedId");
			session.invalidate();
	}
		
	

} // end class
