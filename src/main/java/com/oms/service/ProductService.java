package com.oms.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.oms.dao.Interface.ProductDAO;
import com.oms.dao.implementation.ProductDAOImpl;
import com.oms.model.Product;

public class ProductService{
	
	private final ProductDAO productDAO;
	
	  public ProductService(ProductDAO productDAO) {
	        this.productDAO = productDAO;
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
	
    public int getTotalProductCount() {
        return productDAO.getTotalProductCount();
    }
    
//    public int getCountFav(int id) {
//        return productDAO.favProduct(id);
//    }
    
    public List<Product> listProductsSortedByPrice() {
        return productDAO.listProducts(1, getTotalProductCount())
                         .stream()
                         .sorted(Comparator.comparingDouble(Product::getPrice))
                         .collect(Collectors.toList());
    }
   }
