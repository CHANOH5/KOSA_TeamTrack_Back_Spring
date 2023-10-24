package com.my.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class Attach {

	private String tempDir = "C:\\KOSA202307\\temp"; //임시파일 저장경로
	private String attachesDir = "C:\\KOSA202307\\attaches"; //첨부경로

//	//안녕하세요 아래는 저(혜빈)의 경로입니다.. ^^ㅜ
//	private String tempDir = "/Users/qqllzs/filetest"; //임시파일 저장경로
//	private String attachesDir = "/Users/qqllzs/filetest"; //첨부경로

	private ServletFileUpload fileUpload;
	private Map<String, List<FileItem>> requestMap;

	public Attach(HttpServletRequest request) throws FileUploadException {
		DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
		File repository = new File(tempDir);
		if(!repository.exists()) {
			if(!repository.mkdir()) {
				throw new FileUploadException(tempDir+"폴더생성 안됨");
			}
		}

		if(!new File(attachesDir).exists()) {
			if(!new File(attachesDir).mkdir()) {
				System.out.println(attachesDir+"폴더 생성");
				throw new FileUploadException(attachesDir+"폴더생성안됨");
			}
		}

		fileItemFactory.setRepository(repository); //업로드경로설정
		fileItemFactory.setSizeThreshold(10*1024); //10*1024byte이상인 경우 임시파일이 만들어짐
		fileUpload = new ServletFileUpload(fileItemFactory);
		requestMap =  fileUpload.parseParameterMap(request);
	}

	// 요청전달데이터가 올 때
	// ex) String id = attach.getParameter("id");
	// ex) attach.getParameter("pwd");
	// ex) attach.getParameter("name");
	// 파일은 여러개 선택해서 보내지만 아이디,비번,이름은 하나씩만 보내게 된다
	/**
	 * 요청전달데이터에 해당하는 값을 반환한다
	 * @param name 요청전달데이터이름
	 * @return 요청전달데이터값, 이름에 해당하는 값이 없으면 null을 반환한다.
	 */
	public String getParameter(String name) {

//		return requestMap.get(name).get(0).getString();
		// 위아래 동일 코드
		List<FileItem> items = requestMap.get(name);
		if(items == null ) {
			return null;
		} // if
		FileItem item = items.get(0);

		String value = "";
		try {
			value = item.getString("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * 요청전달데이터에 해당하는 값들을 모두 반환한다
	 * @param name 요청전달데이터 이름
	 * @return 요청전달데이터값들, 이름에 해당하는 값이 없으면 null을 반환한다.
	 */
	// 요청 전달데이터가 c=c111&c=c222&c=c333
	public String[] getParameterValues(String name){
		List<FileItem> list = requestMap.get(name);

		String[] arr = new String[list.size()];
		int i=0;
		for(FileItem item: requestMap.get(name)) {
			try {
				arr[i] = item.getString("UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			i++;
		}
		return arr;
	}

	/**
	 * 요청전달데이터 이름에 해당하는 첨부파일들의 정보를 반환한다.
	 * @param name 요청전달데이터이름
	 * @return 첨부된 파일들의 정보
	 * @throws Exception
	 */
	public List<FileItem> getFile(String name) throws Exception{
		return requestMap.get(name);
	}

	/**
	 * 첨부파일을 서버에 저장한다(서버에 원본파일 이름으로 저장됨)
	 * @param name 요청전달데이터 이름
	 * @throws Exception
	 */
	public void upload(String name) throws Exception{
		FileItem fileItem = requestMap.get(name).get(0);
		if(fileItem == null || fileItem.getSize() == 0){
			String fileName = fileItem.getName();
			File profileFile = new File(fileName);
			fileItem.write(profileFile);
			
		}
	}

	/**
	 * 첨부파일을 서버에 저장한다(서버에 저장될 파일 이름도 매개변수 받아옴)
	 * @param name 요청전달데이터 이름
	 * @param fileName 파일이름
	 * @throws Exception
	 */
	public void upload(String name, String fileName) throws Exception {
		FileItem fileItem = requestMap.get(name).get(0);
		System.out.println("fileItem=" + fileItem);
		if(fileItem == null || fileItem.getSize() == 0){
			throw new Exception("첨부할 파일이 없습니다");
		}
		//String fileName = fileItem.getName();
		File file = new File(attachesDir, fileName);
		fileItem.write(file);
	}
	

	
} //end class
