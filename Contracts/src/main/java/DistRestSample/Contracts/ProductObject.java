package DistRestSample.Contracts;

import java.util.Collection;

import com.google.gson.Gson;

public class ProductObject {
	public final long Id;
	public final String Name;
	public final String Manufacturer;
	
	public ProductObject(long id, String name, String manufacturer) {
		Id = id;
		Name = name;
		Manufacturer = manufacturer;
	}
	

	
	public static ProductObject FromJson(String json) {
		Gson gson = new Gson();
		ProductObject instance = gson.fromJson(json, ProductObject.class);
		return instance;
	}
	
	public static String ToJson(Collection<ProductObject> objects) {
		ProductObject[] allArray = (ProductObject[]) objects.toArray();
		String json = new Gson().toJson(allArray);
		return json;
	}
	
	public String ToJson() {
		Gson gson = new Gson();
		String json = gson.toJson(this);
		return json;
	}
}
