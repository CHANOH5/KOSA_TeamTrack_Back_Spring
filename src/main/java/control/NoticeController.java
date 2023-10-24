package control;

import com.my.notice.service.NoticeService;
import com.my.notice.service.NoticeServiceImpl;

public abstract class NoticeController implements Controller {

	protected NoticeService service;

	public NoticeController(){
		service = NoticeServiceImpl.getInstance();
	}
}
