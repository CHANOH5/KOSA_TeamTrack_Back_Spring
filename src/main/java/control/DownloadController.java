package control;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;

import javax.servlet.ServletOutputStream;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public class DownloadController {

	@GetMapping("/download")
	@ResponseBody
	public ResponseEntity<?> download(String teamNo, String opt) throws IOException{
		String attachesDir = "C:\\KOSA202307\\attaches";
		File dir = new File(attachesDir);
		
		String fileName;
		if(opt.equals("profile")) {
			opt+="_t"; //썸네일 파일
			fileName = teamNo + "_"+ opt;
		}else {
			fileName = teamNo + "_"+ opt +"_";
		}
		
		for(File f: dir.listFiles()) {
			String existFileName = f.getName();
			if(existFileName.startsWith(fileName)) {
		
				HttpStatus status = HttpStatus.OK;
				HttpHeaders headers = new HttpHeaders();
				headers.add(HttpHeaders.CONTENT_DISPOSITION,
						    "attachment;filename=" + URLEncoder.encode(existFileName, "UTF-8"));
				
				System.out.println("in download file: " + f + ", file size:" + f.length());
				String contentType = Files.probeContentType(f.toPath());//파일의 형식		
				headers.add(HttpHeaders.CONTENT_TYPE, contentType); //응답형식		
				headers.add(HttpHeaders.CONTENT_LENGTH, ""+f.length());//응답길이
				
				byte[]bArr = FileCopyUtils.copyToByteArray(f);
				ResponseEntity<?> entity = new ResponseEntity<>(bArr, headers, status);//응답상태코드
				return entity;
			}
		}
		HttpStatus status = HttpStatus.NOT_FOUND;
		ResponseEntity<?> entity = new ResponseEntity<>("프로필썸네일파일이 없습니다", status);
		return entity;
		
	}
}