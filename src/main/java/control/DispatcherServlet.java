package control;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/")  // ("/DispatcherServlet")
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String envFileName = "control.properties";
	private Properties env;

    public DispatcherServlet() {
    	super();
    } // constructor

	@Override
	public void init() throws ServletException {
		super.init();

		env = new Properties(); // Properties객체 생성
		//servletContext 객체 찾기
		ServletContext sc = this.getServletContext();

//		String realPath = sc.getRealPath("WEB-INF\\classes\\com\\my\\env\\" + envFileName); //window
		String realPath = sc.getRealPath("WEB-INF/classes/com/my/env/" + envFileName); //mac

		System.out.println("in DispathcerServlet의 init:() realPath = " + realPath+"\n/Users/yoon/Documents/java_workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/KOSA_TeamTrack_Back/WEB-INF/classes/com/my/env/control.properties");
		// control.properties파일의 내용을 Properties객체에 넣기

		try {
			env.load(new FileInputStream(realPath));
		} catch (IOException e) {
			e.printStackTrace();
		} //try-catch
	} // init

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		// 헤더 설정
		//res.setHeader("Access-Control-Allow-Origin", "http://localhost:5500");
		res.setHeader("Access-Control-Allow-Origin", "http://192.168.1.20:5500");
		res.setHeader("Access-Control-Allow-Credentials", "true");
		res.setContentType("application/json;charset=utf-8");
		req.setCharacterEncoding("UTF-8");

		System.out.println("req.getServletPath() = " + req.getServletPath());

		String className = env.getProperty(req.getServletPath());
		System.out.println(className);
		try {
			Class<?> clazz = Class.forName(className);//클래스이름에 해당하는 .class파일 찾아서 JVM으로 로드

			Controller controller;
			try {
				Method method = clazz.getMethod("getInstance");
				controller = (Controller)method.invoke(null);//static인 getInstance()메서드호출
			}catch(NoSuchMethodException e) {
				controller = (Controller)clazz.getDeclaredConstructor().newInstance();
			}
			String path = controller.execute(req, res);

			if(path != null) {
				RequestDispatcher rd = req.getRequestDispatcher(path);
				rd.forward(req, res);
			} // if

		} catch (Exception e) {
			e.printStackTrace();
		} // try-catch

	} // service

} // end class
