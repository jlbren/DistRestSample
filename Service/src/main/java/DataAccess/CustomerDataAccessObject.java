package DataAccess;

import DistRestSample.Contracts.*;
import java.sql.*;
import java.util.*;


public class CustomerDataAccessObject {
	
	private static final String TableName = "Customer";
	private static final String IdCol = "id";
	private static final String BirthDateCol = "birthdate";
	private static final String FirstNameCol = "firstname";
	private static final String LastNameCol = "lastname";
	
	public Collection<CustomerObject> ReadAllCustomers() throws Exception {
		List<CustomerObject> customers = new ArrayList<CustomerObject>();
		Database db = new Database();
		try{
			db.ExecuteReader("Select * from " + TableName, (ResultSet row)->{
				long id = row.getLong(row.findColumn(IdCol));
				String birthday = row.getString(row.findColumn(BirthDateCol));
				String firstname = row.getString(row.findColumn(FirstNameCol));
				String lastname = row.getString(row.findColumn(LastNameCol));
				
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
			String stm = "insert into " + TableName + 
					" (" + IdCol + ", " + BirthDateCol + ", " + FirstNameCol + ", " + LastNameCol + ") " + 
					"values (?, ?, ?, ?)";
			Object[] parameters = new Object[]{ newObj.Id, newObj.Birthday, newObj.FirstName, newObj.LastName };
			db.RunNonQuery(stm, parameters);
		} finally {
			db.Close();
		}
	}

	public void Delete(long id) throws Exception {
		Database db = new Database();
		try {
			String stm = "delete from " + TableName + " where " + IdCol + " = ?";
			Object[] parameters = new Object[]{id};
			db.RunNonQuery(stm, parameters);
		} finally {
			db.Close();
		}
		
	}
}
