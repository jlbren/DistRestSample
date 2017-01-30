package SampleService;
import java.util.*;
import java.util.stream.*;

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
	@Produces(MediaType.TEXT_PLAIN)
	public String getAllCustomers() {
		try{
			Collection<CustomerObject> all = _customerDataAccess.ReadAllCustomers();
		  
			return "---Customer List---\n"
					+ all.stream()
					     .map(c -> c.toString())
					     .collect(Collectors.joining("\n"));
		} catch(Exception e) {
			return "---Error---\n" + 
					e.getMessage() + "\n" +
					e.getStackTrace();
		}
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCustomer(@PathParam("id") long id) {
		try{
			Collection<CustomerObject> all = _customerDataAccess.ReadAllCustomers();
		  
			Optional<CustomerObject> match = all
					.stream()
					.filter(c -> c.Id == id)
					.findFirst();
			if (match.isPresent()) {
				return "---Customer---\n" + match.get().toString();
			} else {
				return "Customer not found";
			}
		} catch(Exception e){
			return "---Error---\n" + 
					e.getMessage() + "\n" +
					e.getStackTrace();
		}
	}
}