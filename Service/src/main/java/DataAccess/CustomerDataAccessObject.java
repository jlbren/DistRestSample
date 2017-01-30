package DataAccess;

import DistRestSample.Contracts.*;
import java.sql.*;
import java.util.*;


public class CustomerDataAccessObject {
	
	public Collection<CustomerObject> ReadAllCustomers() throws Exception {
		List<CustomerObject> customers = new ArrayList<CustomerObject>();
		Database db = new Database();
		try{
			db.ExecuteReader("Select * from customers", (ResultSet row)->{
				long id = row.getLong(row.findColumn("id"));
				String birthday = row.getString(row.findColumn("birthday"));
				String firstname = row.getString(row.findColumn("firstname"));
				String lastname = row.getString(row.findColumn("lastname"));
				
				CustomerObject customer = new CustomerObject(id, firstname, lastname, birthday);
				customers.add(customer);
			});
		} finally {
			db.Close();
		}
		return customers;
	}

	public void Insert(CustomerObject newObj) throws Exception {
		Database db = new Database();
		try {
			String stm = "insert into customers (id, birthday, firstname, lastname) values (?, ?, ?, ?)";
			Object[] parameters = new Object[]{ newObj.Id, newObj.Birthday, newObj.FirstName, newObj.LastName };
			db.RunNonQuery(stm, parameters);
		} finally {
			db.Close();
		}
	}

	public void Delete(long id) throws Exception {
		Database db = new Database();
		try {
			String stm = "delete from customers where id = ?";
			Object[] parameters = new Object[]{id};
			db.RunNonQuery(stm, parameters);
		} finally {
			db.Close();
		}
		
	}
}
