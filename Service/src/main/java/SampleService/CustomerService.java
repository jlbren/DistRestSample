package SampleService;
import java.sql.SQLException;
import java.util.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import DistRestSample.Contracts.*;
import DataAccess.*;
import Chaos.*;

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
		CustomerResponse response;
		try{
			ChaosSource.ForService("Customer").ForMethod("getAllCustomers").run();
			
			Collection<CustomerObject> all = _customerDataAccess.ReadAllCustomers();
		    response = CustomerResponse.Success(all);
		} catch(Exception e) {
			response = CustomerResponse.Error(e);
		}
		return response.ToJson();
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCustomer(@PathParam("id") long id) {
		CustomerResponse response;
		try{

			ChaosSource.ForService("Customer").ForMethod("getCustomer").run();
			
			Collection<CustomerObject> all = _customerDataAccess.ReadAllCustomers();
		  
			Optional<CustomerObject> match = all
					.stream()
					.filter(c -> c.Id == id)
					.findFirst();
			
			if (match.isPresent()) {
				response = CustomerResponse.Success(new CustomerObject[]{match.get()});
			} else {
				response = CustomerResponse.Error("Customer not found");
			}
		} catch(Exception e){
			response = CustomerResponse.Error(e);
		}
		return response.ToJson();
	}
	
	@POST
	@Path("{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String addCustomer(@PathParam("id") long id, String content) {
		SimpleResponse response;
		try {

			ChaosSource.ForService("Customer").ForMethod("addCustomer").run();
			
			CustomerObject newObj = CustomerObject.FromJson(content);
			if(newObj.Id != id) {
				response = SimpleResponse.Error("Error, ID did not match");
			} else {
				_customerDataAccess.Insert(newObj);
				response = SimpleResponse.Success();
			}
		} catch(SQLException e) {
			if(e.getMessage().contains("Duplicate")){
				response = SimpleResponse.Error("Customer with id = " + id + " already exists");
			} else {
				response = SimpleResponse.Error(e);
			}
		} catch(Exception e) {
			response = SimpleResponse.Error(e);
		}
		return response.ToJson();
	}
	
	@PUT
	@Path("{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String updateCustomer(@PathParam("id") long id, String content) {
		SimpleResponse response;
		try {

			ChaosSource.ForService("Customer").ForMethod("updateCustomer").run();
			
			CustomerObject newObj = CustomerObject.FromJson(content);
			if(newObj.Id != id) {
				response = SimpleResponse.Error("Error, ID did not match");
			} else {
				_customerDataAccess.Update(newObj);
				response = SimpleResponse.Success();
			}
		} catch(Exception e) {
			response = SimpleResponse.Error(e);
		}
		return response.ToJson();
	}
	
	@DELETE
	@Path("{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteCustomer(@PathParam("id") long id) {
		SimpleResponse response;
		try {

			ChaosSource.ForService("Customer").ForMethod("deleteCustomer").run();
			
			_customerDataAccess.Delete(id);
			response = SimpleResponse.Success();
		} catch(Exception e) {
			response = SimpleResponse.Error(e);
		}
		return response.ToJson();
	}
}