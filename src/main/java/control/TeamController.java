package control;

import com.my.rank.service.RankServiceImpl;
import com.my.team.service.TeamServiceImpl;

public abstract class TeamController implements Controller {
	
	protected TeamServiceImpl service;
	protected RankServiceImpl rankservice;

	public TeamController() {
		service = TeamServiceImpl.getInstance();
		rankservice = RankServiceImpl.getInstance();
	}

} // end class
