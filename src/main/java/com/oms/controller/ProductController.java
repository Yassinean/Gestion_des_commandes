package com.oms.controller;

import com.oms.dao.implementation.ProductDAOImpl;
import com.oms.model.Product;
import com.oms.service.ProductService;
import com.oms.util.ThymeleafUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ProductController extends HttpServlet {

	 private static final long serialVersionUID = 1L;
	    private ProductService productService;
	    private ThymeleafUtil thymeleafUtil;

	    @Override
	    public void init() throws ServletException {
	        productService = new ProductService(new ProductDAOImpl());
	        thymeleafUtil = new ThymeleafUtil(getServletContext());
	    }

	    @Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        String action = request.getParameter("action");
	        if (action == null) action = "list";

	        switch (action) {
	            case "list":
	                listProducts(request, response);
	                break;
	            case "search":
	                searchProducts(request, response);
	                break;
	            case "delete":
	                deleteProduct(request, response);
	                break;
	            default:
	                response.sendRedirect("products?action=list");
	        }
	    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("insert".equals(action)) {
            insertProduct(request, response);
        } else if ("update".equals(action)) {
            updateProduct(request, response);
        }
    }

   

    private void listProducts(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	  int page = 1;
    	    int pageSize = 6;

    	    String pageParam = request.getParameter("page");
    	    if (pageParam != null && !pageParam.isEmpty()) {
    	        page = Integer.parseInt(pageParam);
    	    }

    	    int totalProducts = productService.getTotalProductCount();
    	    boolean paginationNeeded = totalProducts > pageSize;

    	    List<Product> products;
    	    if (paginationNeeded) {
    	        products = productService.listProducts(page, pageSize);
    	    } else {
    	        products = productService.listProducts(1, totalProducts);
    	    }

    	    request.setAttribute("products", products);
    	    request.setAttribute("paginationNeeded", paginationNeeded);
    	    request.setAttribute("currentPage", page);
    	    request.setAttribute("totalPages", (int) Math.ceil((double) totalProducts / pageSize));

    	    thymeleafUtil.returnView(request, response, "product-list", null);
    	}
    
    private void searchProducts(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String name = request.getParameter("search");
        List<Product> products = productService.searchProducts(name);
        if (products.isEmpty()) {
            request.setAttribute("error", "Product not found");
        }
        
        request.setAttribute("products", products);
        thymeleafUtil.returnView(request, response, "product-list", null);
    }

   
    private void insertProduct(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        int stock = Integer.parseInt(request.getParameter("stock"));

        Product newProduct = new Product(name, description, price, stock);
        productService.saveProduct(newProduct);
        
        if (newProduct != null) {
        	request.getSession().setAttribute("message", "Product added successfully !");
        }
        
        response.sendRedirect("products?action=list");
    }

  
    private void updateProduct(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        int stock = Integer.parseInt(request.getParameter("stock"));

        Product updatedProduct = new Product(id, name, description, price, stock);
        productService.updateProduct(id, updatedProduct);
        
        if (updatedProduct != null) {
        	request.getSession().setAttribute("message", "Product updated successfully !");
        }

        response.sendRedirect("products?action=list");
    }

  
    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        productService.deleteProduct(id);
        request.getSession().setAttribute("message", "Product deleted successfully !");
        response.sendRedirect("products?action=list");
    }
}
