package DistRestSample.Client;

import java.util.*;
import java.net.*;

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
		try {
			ClientAction action = new ClientAction();
			URL customer = new URL("http://localhost:8080/myapp/customers/" + _customer.Id);
			URL allCustomers = new URL("http://localhost:8080/myapp/customers/all");
			
			action.Post(customer, HttpContentType.JSON, _customer.ToJson());
			String customerGet = action.Get(customer, HttpContentType.JSON);
			
			CustomerObject readCustomer = CustomerObject.FromJson(customerGet);
			
			if(!CustomerObject.Equals(readCustomer, _customer)) {
				throw new RuntimeException("Customers not equal after POST then GET");
			}
			
			action.Delete(customer, HttpContentType.JSON);
			
			String all = action.Get(allCustomers, HttpContentType.JSON);
			
			if(all.compareTo("") == 0) {
				throw new RuntimeException("All customers returned empty string");
			}
			
		} catch(Exception e) {
			synchronized(_errors){
				_errors.add(e);
			}
		}
	}

}
