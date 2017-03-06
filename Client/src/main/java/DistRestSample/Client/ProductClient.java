package DistRestSample.Client;

import java.net.URL;

import DistRestSample.Contracts.CustomerResponse;
import DistRestSample.Contracts.ProductObject;
import DistRestSample.Contracts.ProductResponse;
import DistRestSample.Contracts.SimpleResponse;

public class ProductClient {

	private final String _baseAddress;
	
	public ProductClient(String baseAddress) {
		_baseAddress = baseAddress;
	}
	
	public ProductObject[] getAllProducts() throws Exception {
		ClientAction action = new ClientAction();
		URL address = new URL(_baseAddress + "all");
		String json = action.Get(address, HttpContentType.JSON);
		ProductResponse response = ProductResponse.FromJson(json);
		ThrowFor(response);
		return response.Products;
	}

	public ProductObject getProduct(long id) throws Exception {
		ClientAction action = new ClientAction();
		URL address = new URL(_baseAddress + new Long(id).toString());
		String json = action.Get(address, HttpContentType.JSON);
		ProductResponse response = ProductResponse.FromJson(json);
		ThrowFor(response);
		return response.Products[0];
	}
	
	private void ThrowFor(SimpleResponse response) {
		if(!response.Success) {
			if(response.StackTrace.length() == 0) {
				throw new RuntimeException(response.Message);
			} else {
				throw new RuntimeException(response.Message + "\r\n" + response.StackTrace);
			}
		}
	}
}
