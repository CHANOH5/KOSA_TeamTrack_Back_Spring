package control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutController extends CustomerController {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
//		System.out.println("in logout:" + session.getId());
		session.removeAttribute("loginedId");
		session.invalidate();
		
		response.setContentType("application/json;charset=utf-8");
        response.getWriter().write("{}");
		return null;
	}

}