package DataAccess;

import java.sql.*;
import java.util.*;

import SampleService.Customer;

public class CustomerDataAccessObject {
	private Database _db;
	
	public CustomerDataAccessObject() throws Exception {
		_db = new Database();
	}
	
	public Iterable<Customer> ReadAllCustomers() throws Exception {
		List<Customer> customers = new ArrayList<Customer>();
		
		_db.ExecuteReader("Select * from customers", (ResultSet row)->{
			Customer customer = new Customer.CustomerBuilder()
					.id(row.getLong(row.findColumn("id")))
					.birthday(row.getString(row.findColumn("birthday")))
					.firstName(row.getString(row.findColumn("firstname")))
					.lastName(row.getString(row.findColumn("lastname")))
					.build();
			customers.add(customer);
		});
		
		return customers;
	}
	
	public void Close() throws Exception {
		_db.Close();
	}
}
