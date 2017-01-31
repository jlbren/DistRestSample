package DistRestSample.Client;

import java.io.IOException;
import java.net.*;
import java.util.*;
import DistRestSample.Contracts.*;

public class CustomerClient {
	
	private final String _baseAddress;
	
	public CustomerClient(String baseAddress) {
		_baseAddress = baseAddress;
	}

	public CustomerObject[] getAllCustomers() throws Exception {
		ClientAction action = new ClientAction();
		URL address = new URL(_baseAddress + "all");
		String json = action.Get(address, HttpContentType.JSON);
		CustomerResponse response = CustomerResponse.FromJson(json);
		ThrowFor(response);
		return response.Customers;
	}
	
	public CustomerObject getCustomer(long id) throws Exception {
		ClientAction action = new ClientAction();
		URL address = new URL(_baseAddress + new Long(id).toString());
		String json = action.Get(address, HttpContentType.JSON);
		CustomerResponse response = CustomerResponse.FromJson(json);
		ThrowFor(response);
		return response.Customers[0];
	}
	
	public void addCustomer(CustomerObject customer) throws Exception {
		ClientAction action = new ClientAction();
		URL address = new URL(_baseAddress + new Long(customer.Id).toString());
		String customerJson = customer.ToJson();
		String json = action.Post(address, HttpContentType.JSON, customerJson);
		SimpleResponse response = SimpleResponse.FromJson(json);
		ThrowFor(response);
	}
	
	public void deleteCustomer(long id) throws Exception {
		ClientAction action = new ClientAction();
		URL address = new URL(_baseAddress + new Long(id).toString());
		String json = action.Delete(address, HttpContentType.JSON);
		SimpleResponse response = SimpleResponse.FromJson(json);
		ThrowFor(response);
	}
	
	private static void ThrowFor(SimpleResponse response) throws Exception {
		if(!response.Success) {
			if(response.StackTrace.length() == 0) {
				throw new RuntimeException(response.Message);
			} else {
				throw new RuntimeException(response.Message + "\r\n" + response.StackTrace);
			}
		}
	}
}
