package control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.exception.FindException;
import com.my.notice.dto.NoticeDTO;
import com.my.util.PageGroup;

public class NoticeListController extends NoticeController{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
//		response.setHeader("Access-Control-Allow-Origin", "http://localhost:5500");
//		response.setHeader("Access-Control-Allow-Credentials", "true");

		PrintWriter out = response.getWriter();

		Integer teamNo = Integer.parseInt(request.getParameter("teamNo"));
		String currentPage = request.getParameter("currentPage");
		int cp = 1;
		if (currentPage != null && !currentPage.equals("")) {
			cp = Integer.parseInt(currentPage);
		}

		ObjectMapper mapper = new ObjectMapper();
		try {
			PageGroup<NoticeDTO> pg = service.findNoticeAll(cp, teamNo);
			String jsonStr = mapper.writeValueAsString(pg);
			out.print(jsonStr);
		} catch (FindException e) {
			e.printStackTrace();
		}

		return null;
	}

}
