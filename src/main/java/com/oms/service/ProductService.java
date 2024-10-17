package com.oms.service;

import java.util.List;

import com.oms.dao.Interface.ProductDAO;
import com.oms.dao.implementation.ProductDAOImpl;
import com.oms.model.Product;

public class ProductService{
	
	private final ProductDAO productDAO;
	
	public ProductService() {
		productDAO = new ProductDAOImpl();
	}
	
	public Product saveProduct(Product product) {
		return productDAO.saveProduct(product);
	}
	
	public void updateProduct(int id , Product product) {
		productDAO.updateProduct(id, product);
	}
	
	public void deleteProduct(int id) {
		productDAO.deleteProduct(id);
	}
	
	public Product findProduct(int id) {
		return productDAO.findProduct(id);
	}
	
	public List<Product> listProducts(int page , int size){
		return productDAO.listProducts(page,size);
	}
	
	public List<Product> searchProducts(String name){
		return productDAO.searchProducts(name);
	}
}