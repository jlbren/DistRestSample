package DistRestSample.Contracts;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;

import com.google.gson.Gson;

public class CustomerResponse extends SimpleResponse {
	public final CustomerObject[] Customers;
	
	public static CustomerResponse Error(Exception e) {
		String stackTrace = ExceptionTraceReader.ReadTrace(e);
		return new CustomerResponse(false, e.getMessage(), stackTrace, new CustomerObject[0]);
	}
	
	public static CustomerResponse Error(String message) {
		return new CustomerResponse(false, message, "", new CustomerObject[0]);
	}
	
	public static CustomerResponse Success() {
		return new CustomerResponse(true, "", "", new CustomerObject[0]);
	}
	
	public static CustomerResponse Success(Collection<CustomerObject> customers) {
		CustomerObject[] array = new CustomerObject[customers.size()];
		int index = -1;
		for(CustomerObject c : customers) {
			array[++index] = c;
		}
		return Success(array);
	}
	
	public static CustomerResponse Success(CustomerObject[] customers) {
		return new CustomerResponse(true, "", "", customers);
	}
	
	public static CustomerResponse FromJson(String json) {
		Gson serializer = new Gson();
		CustomerResponse response = serializer.fromJson(json, CustomerResponse.class);
		return response;
	}

	private CustomerResponse(boolean success, String message, String trace, CustomerObject[] customers){
		super(success, message, trace);
		Customers = customers;
	}
	
	private CustomerResponse() {
		super();
		Customers = new CustomerObject[0];
	}
}
