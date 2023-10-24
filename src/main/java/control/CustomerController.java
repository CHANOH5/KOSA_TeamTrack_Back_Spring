package control;

import com.my.customer.service.CustomerService;
import com.my.customer.service.CustomerServiceImpl;

public abstract class CustomerController implements Controller {

	protected CustomerService service;

	public CustomerController() {
		service = CustomerServiceImpl.getInstance();
	} // constructor

} // end class
