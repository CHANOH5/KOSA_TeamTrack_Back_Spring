package control;

import com.my.qna.service.QnaBoardCommentService;
import com.my.qna.service.QnaBoardCommentServiceImpl;
import com.my.qna.service.QnaBoardService;
import com.my.qna.service.QnaBoardServiceImpl;

public abstract class QnaController implements Controller {
	
	protected QnaBoardService service;
	
	protected QnaBoardCommentService commentService;

	public QnaController() {
		service = QnaBoardServiceImpl.getInstance();
		commentService = QnaBoardCommentServiceImpl.getInstance();
	} //constructor
	
} // end class
