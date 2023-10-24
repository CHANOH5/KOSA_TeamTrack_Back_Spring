package control;

import com.my.rank.service.RankService;
import com.my.rank.service.RankServiceImpl;

public abstract class RankController implements Controller {
	
	protected RankService service;
	public RankController() {
		service = RankServiceImpl.getInstance();
	}

}
