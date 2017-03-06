package SampleService;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import DataAccess.Database;
import DistRestSample.Contracts.ProductObject;

public class ProductDataAccessObject {
	
	/*
create table if not exists DistRestSample.Product (
	id bigint,
	name varchar(30),
	manufacturer varchar(30),
	primary key(id)
);*/
	
	private static final String TableName = "Product";
	private static final String IdCol = "id";
	private final static String NameCol = "name";
	private final static String ManufacturerCol = "manufacturer";

	public Collection<ProductObject> ReadAllProducts() throws Exception {
		Collection<ProductObject> products = new ArrayList<ProductObject>();
		Database db = new Database();
		try{
			String query = "Select * from " + TableName;
			db.ExecuteReader(query, (ResultSet row)->{
				long id = row.getLong(row.findColumn(IdCol));
				String name = row.getString(row.findColumn(NameCol));
				String manufacturer = row.getString(row.findColumn(ManufacturerCol));
				
				ProductObject product = new ProductObject(id, name, manufacturer);
				products.add(product);
			});
		} finally {
			db.Close();
		}
		return products;
	}

	public Collection<ProductObject> FindProduct(long id) throws Exception {
		Collection<ProductObject> products = new ArrayList<ProductObject>();
		Database db = new Database();
		try{
			String query = "Select * from " + TableName + " where id = " + id;
			db.ExecuteReader(query, (ResultSet row)->{
				long productId = row.getLong(row.findColumn(IdCol));
				String name = row.getString(row.findColumn(NameCol));
				String manufacturer = row.getString(row.findColumn(ManufacturerCol));
				
				ProductObject product = new ProductObject(productId, name, manufacturer);
				products.add(product);
			});
		} finally {
			db.Close();
		}
		return products;
	}

}
