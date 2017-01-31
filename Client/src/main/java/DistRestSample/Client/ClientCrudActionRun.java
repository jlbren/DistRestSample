package DistRestSample.Client;

import java.util.*;
import java.net.*;
import DistRestSample.Contracts.*;

public class ClientCrudActionRun implements Runnable {
	private final List<Exception> _errors;
	private CustomerObject _customer;
	
	public ClientCrudActionRun() {
		_customer = null;
		_errors = new ArrayList<Exception>();
	}
	
	public Collection<Exception> getErrors() {
		Collection<Exception> copy = new ArrayList<Exception>();
		synchronized(_errors) {
			for(Exception e : _errors) {
				copy.add(e);
			}
		}
		return copy;
	}
	
	public void Reset(CustomerObject obj) {
		synchronized(_errors) {
			_errors.clear();
			_customer = obj;
		}
	}
	

	@Override
	public void run() {
		if(_customer == null) {
			throw new RuntimeException("Customer is not set");
		}
		try {
			CustomerClient client = new CustomerClient("http://localhost:8080/myapp/customers/");
			
			client.addCustomer(_customer);
			
			CustomerObject added = client.getCustomer(_customer.Id);
			
			if(added == null) {
				throw new RuntimeException("unexpected null from getCustomer()");
			}
			
			client.deleteCustomer(_customer.Id);
			
			CustomerObject[] all = client.getAllCustomers();
			
			for(CustomerObject c : all) {
				if(c.Id == _customer.Id) {
					throw new RuntimeException("customer id " + _customer.Id + " was not deleted");
				}
			}
			
		} catch(Exception e) {
			synchronized(_errors){
				_errors.add(e);
			}
		}
	}

}
