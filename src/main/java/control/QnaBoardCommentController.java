package control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.qna.dto.QnaBoardCommentDTO;
import com.my.qna.service.QnaBoardCommentServiceImpl;

@RestController
public class QnaBoardCommentController {

	@Autowired
	private QnaBoardCommentServiceImpl impl;
	
	@GetMapping("/qnaboardcomment")
	public List<QnaBoardCommentDTO> list(int teamNo, int qnaNo) {
		
		Map<String, Object> methodMap = new HashMap<>();
		
		try {
			
			List<QnaBoardCommentDTO> list = impl.selectCommentByQnaNo(teamNo, qnaNo);

			Map<String,Object> map = new HashMap<>();
			map.put("list", list);
			map.put("status", 1);

		} catch (FindException e) {
			
			e.printStackTrace();	
			Map<String,Object> map = new HashMap<>();
			map.put("msg", "댓글조회 실패");
			map.put("status", 0);

		} // try-catch
		
		return null;
		
	} // list
	
	@PostMapping("/qnaboardcommentcreate")
	public void create(int teamNo, QnaBoardCommentDTO dto) {
		
		Map<String, Object> map = new HashMap<>();
		
		try {

			impl.insert(teamNo, dto);
			
			map.put("status", 1);
			map.put("msg", "댓글이 작성되었습니다.");

			} catch (Exception e) {
				
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", "회원만 댓글을 작성할 수 있습니다");
			
		} // try-catch

	} // create
	
	@PutMapping("/qnaboardcommentmodify")
	public void modify(int teamNo, QnaBoardCommentDTO dto) {
		
		Map<String, Object> map = new HashMap<>();
		
		try {

			impl.update(teamNo, dto);

			map.put("status", 1);
			map.put("msg", "댓글이 수정되었습니다");
			
		} catch (Exception e) {
			
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", e.getMessage());
			
		} // try-catch

	} // modify
	
	@DeleteMapping("/qnaboardcommentdelete")
	public void delete(int teamNo, int qnaNo, int commentNo) {
		
		Map<String, Object> map = new HashMap<>();
		
		try {
			
			impl.delete(teamNo, qnaNo, commentNo);

			map.put("status", 1);
			map.put("msg", "댓글이 삭제되었습니다");
			
		} catch (Exception e) {
			
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", e.getMessage());
			
		} // try-catch
		
	}
	
	@PostMapping("/qnaboardcommentreplycreate")
	public void createReply(int teamNo, QnaBoardCommentDTO dto) {
		
		try {

			impl.insertReply(teamNo, dto);

			} catch (Exception e) {
				
			e.printStackTrace();
			
			} // try-catch
		
	} // createReply
	
	@PostMapping("/qnaboardcommentpick")
	public void commentPick(int teamNo, int qnaNo, int commentNo) {
		
		Map<String, Object> map = new HashMap<>();
		
		try {
			impl.commentPick(teamNo, qnaNo, commentNo);
			
			map.put("status", 1);
			map.put("msg", "채택되었습니다.");
			
		} catch (ModifyException e) {

			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", e.getMessage());
			
		} // try-catch
		
	} // commentPick
	
	
	
} // end class