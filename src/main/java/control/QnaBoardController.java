package control;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.my.exception.FindException;
import com.my.qna.dto.QnaBoardDTO;
import com.my.qna.service.QnaBoardServiceImpl;
import com.my.util.PageGroup;

@RestController
public class QnaBoardController {
	
	@Autowired
	private QnaBoardServiceImpl impl;
	
	@GetMapping("/qnaboard")
	public PageGroup<QnaBoardDTO> list(@RequestParam(name="currentPage", required = false, 
									defaultValue = "1")
									int cp, int teamNo) throws FindException {
		try {
			PageGroup<QnaBoardDTO> pg = impl.selectAll(teamNo, cp);
			
			return pg;
		} catch(FindException e) {
			e.printStackTrace();
			return null;
		} // try-catch 
			
	} // list
	
	@GetMapping("/qnaboarddetail")
	public QnaBoardDTO selectByQnaNo(@RequestParam(name = "teamNo") int teamNo,
									 @RequestParam(name = "qnaNo") int qnaNo) {
		
		try {
			
			QnaBoardDTO dto = impl.selectByQnaNo(teamNo, qnaNo);
			
			return dto;
			
		} catch (FindException e) {
			
			e.printStackTrace();
			
		} // try-catch

		return null;
		
	} // selectByQnaNo
	
	@PostMapping("/qnaboardcreate")
	public void create(
			int teamNo,
			String loginedId,
			QnaBoardDTO dto,
			MultipartFile file) {
		
		Map<String, Object> map = new HashMap<>();
		try {
			
//			Attach attach = new Attach(req);

			//QnaBoardDTO dto = new QnaBoardDTO(loginedId, qnaTitle, qnaContent);
			
			impl.create(teamNo, dto);
			
//			try {
////				String originFileName=attach.getFile("file").get(0).getName();
////				attach.upload("file", loginedId+"_qnaboard_"+originFileName);
//
//			} catch(Exception e) {
//			
//			} // try-catch
			
			if(file != null && file.getSize() > 0) {
				
				
				File targetFile = new File("C:\\KOSA202307\\attaches", loginedId + "_profile_" + file.getOriginalFilename());
				FileCopyUtils.copy(file.getBytes(), targetFile);

			}
			
			map.put("status", 1);
			map.put("msg", "게시글이 등록되었습니다.");
			
		} catch (Exception e) {
			
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", e.getMessage());
			
		} // try-catch

	} // create
	
	@PutMapping("/qnaboardmodify")
	public void modify(int teamNo, int qnaNo, QnaBoardDTO dto) {
		
		Map<String, Object> map = new HashMap<>();
		

		try {
//			Attach attach=new Attach(req);

//			QnaBoardDTO dto = new QnaBoardDTO(qnaNo, title, content);
			
			impl.update(teamNo, dto);
			
//			try {
////				String originFileName=attach.getFile("f1").get(0).getName();
////				attach.upload("f1", loginedId+"_notice_"+originFileName);
//			} catch(Exception e) {
//				e.printStackTrace();
//			}
			
			map.put("status", 1);
			map.put("msg", "게시글이 수정되었습니다");
			
		} catch (Exception e) {
			
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", e.getMessage());
			
		} // try-catch
		
	} // modify
	
	@DeleteMapping("/qnaboarddelete")
	public void delete(int teamNo, int qnaNo) {
		
		Map<String, Object> map = new HashMap<>();
		

		try {
			impl.delete(teamNo, qnaNo);

			map.put("status", 1);
			map.put("msg", "게시글이 삭제되었습니다");
			
		} catch (Exception e) {
			
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", e.getMessage());
			
		} // try-catch
		
	} // delete
	
	@PostMapping("/qnaboardmemberchk")
	public void memberChk(String loginedId, int teamNo) {
		// HttpSession을 못 써서 loginedId변수를 data에 담아서 보냄
		
		Map<String, Object> map=new HashMap<>();
		
		try {
			
			Integer memberInfo = impl.selectTeamMemberStatus(loginedId, teamNo);

			if(memberInfo != 0) {
				map.put("status", 1);
				map.put("memberInfo", memberInfo);
			} else {
				map.put("status", 0);
				map.put("msg", "회원만 게시글 작성 가능합니다");
			}

		} catch (FindException e) {
			e.printStackTrace();	
			map.put("status", "0");
			map.put("msg", e.getMessage());
		} // try-catch
		
	} // memberChk

} // end class