package control;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet("/upload")
public class UploadServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		// CORS 문제 해결
		res.setHeader("Access-Control-Allow-Origin", "http://192.168.1.20:5500");
		res.setHeader("Access-Control-Allow-Credentials", "true");

		String tempDir = "C:\\KOSA202307\\temp"; 			// 임시 파일 저장 경로
		String attachesDir = "C:\\KOSA202307\\attaches";    // 첨부 파일이 실제로 저장될 경로

		// DiskFileItemFactory 클래스 사용 -> 파일 업로드 시 임시 파일 생성, 디스크에 저장하는데 사용
		DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();

		File repository = new File(tempDir); 				// 해당 경로에 해당하는 파일 객체를 생성

		if(!repository.exists()) {
			if(repository.mkdir()) {						// 폴더 없으면 생성
				System.out.println(tempDir + " 폴더 생성" );
			} else {
				System.out.println(tempDir + " 폴더 생성 안됨");
				return;
			} // if-else
		} // if

		if(!new File(attachesDir).exists()) {
			if(new File(attachesDir).mkdir()) {
				System.out.println(attachesDir + " 폴더 생성" );
			} else {
				System.out.println(attachesDir + " 폴더 생성 안됨");
				return;
			} // if-else
		} // if

		fileItemFactory.setRepository(repository); // 업로드 경로 설정
		fileItemFactory.setSizeThreshold(10*1024);

		// ServletFileUpload 클래스 사용 -> 웹 애플리케이션에서 HTTP 요청으로 업로드된 파일 처리
		ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);

		try {
			// 받아올 파일들 list에 저장
			List<FileItem> items = fileUpload.parseRequest(req);

			for(FileItem item : items) {

				if(item.isFormField()) { // 요청시 데이터가 전달될 경우
					System.out.println(item.getFieldName() + " : " + item.getString());
					// 요청 file이름 : 요청 전달데이터 이름
				} else { // 요청시 첨부파일일 경우
					System.out.println(item.getName() + " : " + item.getSize());

					// 요청 파일 용량 설정
					if(item.getSize() > 0) {
						UUID uuid = UUID.randomUUID(); 	// 중복되지 않는 일련 번호

						// attachesDir 경로에 uuid_파일이름 으로 저장
						File attacheFile = new File(attachesDir, uuid + "_" + item.getName());

						try {
							item.write(repository);		// 첨부파일을 서버 경로에 저장
						} catch (Exception e) {
							e.printStackTrace();
						} // try-catch

					} // inner if
				} // if-else

			} // for


		} catch (FileUploadException e) {
			e.printStackTrace();
		} // try-catch

	} // doPost

} // end class
