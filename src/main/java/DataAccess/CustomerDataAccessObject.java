package DataAccess;

import java.sql.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import SampleService.Customer;

public class CustomerDataAccessObject {
	
	public Collection<Customer> ReadAllCustomers() throws Exception {
		List<Customer> customers = new ArrayList<Customer>();
		Database db = new Database();
		try{
			db.ExecuteReader("Select * from customers", (ResultSet row)->{
				Customer customer = new Customer.CustomerBuilder()
						.id(row.getLong(row.findColumn("id")))
						.birthday(row.getString(row.findColumn("birthday")))
						.firstName(row.getString(row.findColumn("firstname")))
						.lastName(row.getString(row.findColumn("lastname")))
						.build();
				customers.add(customer);
			});
		} finally {
			db.Close();
		}
		return customers;
	}
}
