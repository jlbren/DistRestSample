package DistRestSample.Client;

import java.util.*;
import java.net.*;
import DistRestSample.Contracts.*;

public class ClientCrudActionRun implements Runnable {
	private final List<Exception> _errors;
	private long _low;
	private long _high;
	
	public ClientCrudActionRun(List<Exception> errors) {
		_low = 0;
		_high = 0;
		_errors = errors;
	}
	
	public ClientCrudActionRun() {
		_low = 0;
		_high = 0;
		_errors = new ArrayList<Exception>();
	}
	
	public void SetIdRange(long low, long high) {
		_low = low;
		_high = high;
	}

	@Override
	public void run() {
		if(_low >= _high) {
			throw new RuntimeException("id range is not set");
		}
		try {
			for(long i = _low; i < _high; i++) {
				CustomerClient client = new CustomerClient("http://localhost:8080/myapp/customers/");
				
				CustomerObject customer = new CustomerObject(i, "firstname", "lastname", "birthdate");
				
				client.addCustomer(customer);
				
				CustomerObject added = client.getCustomer(customer.Id);
				
				if(added == null) {
					throw new RuntimeException("unexpected null from getCustomer()");
				}
				
				CustomerObject updated = new CustomerObject(added.Id, "firstname2", "lastname2", "birthdate2");
				
				client.updateCustomer(updated);
				
				//client.deleteCustomer(customer.Id);
				
				CustomerObject[] all = client.getAllCustomers();
				
				/*for(CustomerObject c : all) {
					if(c.Id == customer.Id) {
						throw new RuntimeException("customer id " + customer.Id + " was not deleted");
					}
				}*/
			}
		} catch(Exception e) {
			synchronized(_errors){
				_errors.add(e);
			}
		}
	}

}
