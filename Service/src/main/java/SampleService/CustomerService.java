package SampleService;
import java.util.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import DistRestSample.Contracts.*;
import DataAccess.*;

@Path("/customers")
public class CustomerService {
	
	private final CustomerDataAccessObject _customerDataAccess;
	
	public CustomerService() {
		_customerDataAccess = new CustomerDataAccessObject();
	}

	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllCustomers() {
		try{
			Collection<CustomerObject> all = _customerDataAccess.ReadAllCustomers();
		    String json = CustomerObject.ToJson(all);
			return json;
		} catch(Exception e) {
			return "---Error---\n" + 
					e.getMessage() + "\n" +
					e.getStackTrace();
		}
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCustomer(@PathParam("id") long id) {
		try{
			Collection<CustomerObject> all = _customerDataAccess.ReadAllCustomers();
		  
			Optional<CustomerObject> match = all
					.stream()
					.filter(c -> c.Id == id)
					.findFirst();
			
			if (match.isPresent()) {
				String json = match.get().ToJson();
				return json;
			} else {
				return "Customer not found";
			}
		} catch(Exception e){
			return "---Error---\n" + 
					e.getMessage() + "\n" +
					e.getStackTrace();
		}
	}
	
	@POST
	@Path("{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String addCustomer(@PathParam("id") long id, String content) {
		try {
			CustomerObject newObj = CustomerObject.FromJson(content);
			if(newObj.Id != id) {
				return "Error, ID did not match";
			}
			_customerDataAccess.Insert(newObj);
			return "Success";
		} catch(Exception e) {
			return "Insert failed: " + e.getMessage();
		}
	}
	
	@DELETE
	@Path("{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteCustomer(@PathParam("id") long id) {
		try {
			_customerDataAccess.Delete(id);
			return "Success";
		} catch(Exception e) {
			return "Insert failed: " + e.getMessage();
		}
	}
}