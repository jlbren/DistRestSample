package DistRestSample.Contracts;

import java.util.Collection;

import com.google.gson.Gson;

public class OrderObject {

	public final CustomerObject Customer;
	public final ProductObject Product;
	
	public OrderObject(CustomerObject customer, ProductObject product) {
		Customer = customer;
		Product = product;
	}
	

	
	public static OrderObject FromJson(String json) {
		Gson gson = new Gson();
		OrderObject instance = gson.fromJson(json, OrderObject.class);
		return instance;
	}
	
	public static String ToJson(Collection<OrderObject> objects) {
		OrderObject[] allArray = (OrderObject[]) objects.toArray();
		String json = new Gson().toJson(allArray);
		return json;
	}
	
	public String ToJson() {
		Gson gson = new Gson();
		String json = gson.toJson(this);
		return json;
	}
}
