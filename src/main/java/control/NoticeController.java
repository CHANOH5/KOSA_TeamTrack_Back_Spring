package control;

import java.io.File;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.exception.FindException;
import com.my.exception.RemoveException;
import com.my.notice.dto.NoticeDTO;
import com.my.notice.service.NoticeService;
import com.my.notice.service.NoticeServiceImpl;
import com.my.team.service.TeamService;
import com.my.util.Attach;
import com.my.util.PageGroup;

@RestController
public abstract class NoticeController {
	
	@Autowired
	private NoticeService noticeService;
	private TeamService teamService;

	@GetMapping("/mainnotice")
	public Map<String, Object> mainnotice(String id, Integer teamNo) throws FindException {
		Map<String, Object> map = new HashMap<>();
		Integer memStatus = 0;
		
		try {			
			memStatus = teamService.leaderChk(id, teamNo);
			NoticeDTO notice = noticeService.findMainNotice(teamNo);
			map.put("memStatus", memStatus);
			map.put("notice", notice);

			return map;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
			
	}
	
	@GetMapping("/noticelist")
	public PageGroup<NoticeDTO> noticelist(Integer teamNo, String currentPage) {
		int cp = 1;
		if (currentPage != null && !currentPage.equals("")) {
			cp = Integer.parseInt(currentPage);
		}

		ObjectMapper mapper = new ObjectMapper();
		try {
			PageGroup<NoticeDTO> pg = noticeService.findNoticeAll(cp, teamNo);
			return pg;
		} catch (FindException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@GetMapping("/setmainnotice")
	public Map<String, Object> setmainnotice(Integer teamNo, Integer noticeNo, Integer mainStatus) {
		Map<String, Object> map = new HashMap<>();

		try {
			NoticeDTO notice = noticeService.findMainNotice(teamNo);
			if(mainStatus==1) {
				if(notice==null) {
					noticeService.setMainNotice(teamNo, noticeNo, mainStatus);
					map.put("status", 1);
					map.put("msg", "메인공지가 등록되었습니다");
				}
				else if(notice.getNoticeNo()==noticeNo) {
					map.put("status", 0);
					map.put("msg", "이미 메인공지로 등록된 게시글입니다");
				}else {
					map.put("status", 0);
					map.put("msg", "이미 메인공지가 존재합니다\n기존 메인공지를 내린 후 시도해주세요");
				}
			}else if(mainStatus==0){
				noticeService.setMainNotice(teamNo, noticeNo, mainStatus);
				map.put("status", 1);
				map.put("msg", "메인공지가 취소되었습니다");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", e.getMessage());
		}
		
		return map;
	}
	
	@PostMapping("/writenotice")
	public Map<String,Object> writenotice(Integer teamNo, NoticeDTO notice, MultipartFile f1) throws Exception{
		Map<String, Object> map = new HashMap<>();
		Date regDate = Date.from(Instant.now());
		Integer noticeNo;
		String attachesDir = "C:\\KOSA202307\\attaches";
		File dir = new File(attachesDir);

		try {
			Integer mainStatus = 0; //메인공지 체크여부
			Integer mainChk = 0; //메인공지 등록 정상 여부
			
			if(notice.getMainStatus()!=null) {
				mainStatus = 1;
				NoticeDTO mainNotice = noticeService.findMainNotice(teamNo);
				if(mainNotice!=null) {
					map.put("mainmsg", "이미 메인공지 등록에는 실패하였습니다. 기존 메인공지를 내린 후 등록하세요!");
					mainStatus=0;
					mainChk=0;
				}else {
					mainChk=1;
				}
			}else {
				mainChk=1;
			}
			
			notice.setRegDate(regDate);
			noticeNo = noticeService.writeNotice(teamNo, notice);
			String findName = teamNo+"_"+noticeNo+"_notice_";

			if(f1 != null && f1.getSize() > 0) {				
				String originFileName = f1.getOriginalFilename();
				for(File file : dir.listFiles()) {
					String existFileName = file.getName();
					if(existFileName.startsWith(findName)) {
						file.delete();
					}
				}
				String targetFileName = teamNo+"_"+noticeNo+"_notice_"+ originFileName;
				File targetFile = new File(attachesDir, targetFileName);
				FileCopyUtils.copy(f1.getBytes(), targetFile);
			}
			
			map.put("mainstatus",mainChk);
			map.put("status", 1);
			map.put("msg", "게시글이 업로드되었습니다");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", e.getMessage());
		}

		return map;
	}
	
	@GetMapping("/noticedetail")
	public Map<String, Object> noticedetail(String id, Integer teamNo, Integer noticeNo) {
		Map map = new HashMap<>();
		Integer memStatus = 0;
		String fileName = "null";
		
		String attachesDir = "C:\\KOSA202307\\attaches";
		File dir = new File(attachesDir);
		
		try {
			memStatus = teamService.leaderChk(id, teamNo);
			NoticeDTO notice = noticeService.findByNoticeNo(teamNo, noticeNo);
			
			String findName = teamNo+"_"+noticeNo+"_notice_";
			
			try {
				for(File file : dir.listFiles()) {
					String existFileName = file.getName();
					if(existFileName.startsWith(findName)) {
						fileName = existFileName.replaceFirst(findName,"");
						break;
					}
				}
				map.put("fileName", fileName);
			} catch(Exception e) {
				
			}
			
			map.put("memStatus", memStatus);
			map.put("notice", notice);
			
			return map;
		} catch (FindException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@GetMapping("/noticefiledownload")
	public ResponseEntity<?> noticefiledownload(Integer teamNo, Integer noticeNo) {
		String attachesDir = "C:\\KOSA202307\\attaches";
		File dir = new File(attachesDir);
		ResponseEntity<?> entity = null;
		
		String fileName = teamNo+"_"+noticeNo+"_notice_";

		for(File f: dir.listFiles()) {
			String existFileName = f.getName();
			if(existFileName.startsWith(fileName)) {
		
				try{
					HttpStatus status = HttpStatus.OK;
					HttpHeaders headers = new HttpHeaders();
					headers.add(HttpHeaders.CONTENT_DISPOSITION,
							    "attachment;filename=" + URLEncoder.encode(existFileName, "UTF-8"));
					
					System.out.println("in download file: " + f + ", file size:" + f.length());
					String contentType = Files.probeContentType(f.toPath());//파일의 형식		
					headers.add(HttpHeaders.CONTENT_TYPE, contentType); //응답형식		
					headers.add(HttpHeaders.CONTENT_LENGTH, ""+f.length());//응답길이
					
					byte[]bArr = FileCopyUtils.copyToByteArray(f);
					entity = new ResponseEntity<>(bArr, headers, status);//응답상태코드
					return entity;
				}catch(Exception e) {
					e.printStackTrace();
					return null;
				}
			}
		}

		return entity;
	}
	
	@GetMapping("/editnotice")
	public Map<String, Object> editnotice(Integer teamNo, NoticeDTO notice, Integer status, MultipartFile f1) {
		Map<String, Object> map = new HashMap<>();
		Integer mainStatus = 0;
		Integer mainChk = 0;
		String attachesDir = "C:\\KOSA202307\\attaches";
		File dir = new File(attachesDir);
		try{
			if(status != null) {
				mainStatus = 1;
				NoticeDTO mainNotice = noticeService.findMainNotice(teamNo);
				if(mainNotice!=null) {
					if(mainNotice.getNoticeNo()==notice.getNoticeNo()) {
						map.put("mainmsg", "이미 메인공지로 등록된 게시글입니다");
						mainStatus=0;
					}else {
						map.put("mainmsg", "이미 메인공지가 존재합니다\n기존 메인공지를 내린 후 시도해주세요");
						mainStatus=0;
					}
				}else {
					mainChk = 1;
				}
			}else {
				mainChk=1;
			}
			
			notice.setMainStatus(mainStatus);
			noticeService.modifyNotice(teamNo, notice);
			
			String findName = teamNo+"_"+notice.getNoticeNo()+"_notice_";
			
			if(f1 != null && f1.getSize() > 0) {				
				String originFileName = f1.getOriginalFilename();
				for(File file : dir.listFiles()) {
					String existFileName = file.getName();
					if(existFileName.startsWith(findName)) {
						file.delete();
					}
				}
				String targetFileName = teamNo+"_"+notice.getNoticeNo()+"_notice_"+ originFileName;
				File targetFile = new File(attachesDir, targetFileName);
				FileCopyUtils.copy(f1.getBytes(), targetFile);
			}
			
			map.put("mainstatus", mainChk);
			map.put("status", 1);
			map.put("msg", "게시글이 수정되었습니다");
			return map;
		}catch (Exception e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", e.getMessage());
			return map;
		}
	}
	
	@GetMapping("/deletenotice")
	public Map<String, Object> deleteNotice(Integer teamNo, Integer noticeNo) {
		Map<String, Object> map = new HashMap<>();

		try {
			noticeService.removeNotice(teamNo, noticeNo);
			map.put("status", 1);
			map.put("msg", "삭제되었습니다");
		} catch (RemoveException e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", e.getMessage());
		}

		return map;
	}
}
