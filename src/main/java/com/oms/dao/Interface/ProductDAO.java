package com.oms.dao.Interface;


import java.util.List;

import com.oms.model.Product;

public interface ProductDAO {
	
	Product saveProduct(Product product);
	void updateProduct(int id,Product product);
	void deleteProduct(int id);
	Product findProduct(int id);
	List<Product> listProducts();
}