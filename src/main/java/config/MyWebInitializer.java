package config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


/**
 * web.xml 문서를 대신할 클래스
 */
public class MyWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	// 부모 클래스의 메소드들 오버라이딩
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {MyApplicationContext.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {MyMVCContext.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"}; // String 배열 타입 객체 생성 후 요청
	} 
	
} // end class
