package control;

import com.my.task.service.TaskServiceImpl;

public abstract class TaskController implements Controller {

	protected TaskServiceImpl service;

	public TaskController() {
		service=TaskServiceImpl.getInstance();
	}

}
