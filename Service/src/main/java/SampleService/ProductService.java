package SampleService;
import DistRestSample.Contracts.*;
import java.util.*;

public class ProductService {

	private final ProductDataAccessObject _productDataAccess;
	
	public ProductService() {
		_productDataAccess = new ProductDataAccessObject();
	}
	
	public String getAllProducts() {
		ProductResponse response;
		try{
			Collection<ProductObject> all = _productDataAccess.ReadAllProducts();
		
			response = ProductResponse.Success(all);
		} catch(Exception e) {
			response = ProductResponse.Error(e);
		}
		return response.ToJson();
	}
	
	public String getProduct(long id) {
		ProductResponse response;
		try {
			Collection<ProductObject> matches =
					_productDataAccess.FindProduct(id);
			if(matches.size() == 0) {
				response = ProductResponse.NoMatchFound(id);
			} else {
				response = ProductResponse.Success(matches);
			}
			
		} catch(Exception e) {
			response = ProductResponse.Error(e);
		}
		return response.ToJson();
	}
}
