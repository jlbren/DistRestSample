package DistRestSample.Contracts;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.google.gson.Gson;

public class CustomerResponse extends SimpleResponse {
	public final CustomerObject[] Customers;
	
	public static CustomerResponse Error(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		pw.flush();
		String stackTrace = sw.toString();
		return new CustomerResponse(false, e.getMessage(), stackTrace, new CustomerObject[0]);
	}
	
	public static CustomerResponse Error(String message) {
		return new CustomerResponse(false, message, "", new CustomerObject[0]);
	}
	
	public static CustomerResponse Success() {
		return new CustomerResponse(true, "", "", new CustomerObject[0]);
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
