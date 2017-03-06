package DistRestSample.Contracts;

import java.util.Collection;

import com.google.gson.Gson;

public class ProductResponse extends SimpleResponse {
	public final ProductObject[] Products;

	public static ProductResponse Error(Exception e) {
		String trace = ExceptionTraceReader.ReadTrace(e);
		return new ProductResponse(false, e.getMessage(), trace, new ProductObject[0]);
	}

	public static ProductResponse NoMatchFound(long id) {
		return new ProductResponse(false, "Could not find Product " + id, "", new ProductObject[0]);
	}

	public static ProductResponse Success(Collection<ProductObject> all) {
		ProductObject[] array = new ProductObject[all.size()];
		int index = -1;
		for(ProductObject c : all) {
			array[++index] = c;
		}
		return new ProductResponse(true, "", "", array);
	}
	
	public static ProductResponse FromJson(String json) {
		Gson serializer = new Gson();
		ProductResponse response = serializer.fromJson(json, ProductResponse.class);
		return response;
	}
	
	private ProductResponse(boolean success, String message, String trace, ProductObject[] products){
		super(success, message, trace);
		Products = products;
	}
	
	private ProductResponse() {
		super();
		Products = new ProductObject[0];
	}

}
