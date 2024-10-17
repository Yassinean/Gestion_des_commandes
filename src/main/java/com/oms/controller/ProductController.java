package com.oms.controller;

import com.oms.dao.Interface.ProductDAO;
import com.oms.dao.implementation.ProductDAOImpl;
import com.oms.model.Product;
import com.oms.util.ThymeleafUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ProductController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ProductDAO productDAO = new ProductDAOImpl();
    private ThymeleafUtil thymeleafUtil;

    @Override
    public void init() throws ServletException {
        thymeleafUtil = new ThymeleafUtil(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

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
    	    int pageSize = 5;  // Products per page

    	    String pageParam = request.getParameter("page");
    	    if (pageParam != null && !pageParam.isEmpty()) {
    	        page = Integer.parseInt(pageParam);
    	    }

    	    int totalProducts = productDAO.getTotalProductCount();  // Total product count for pagination
    	    boolean paginationNeeded = totalProducts > pageSize;

    	    List<Product> products;
    	    if (paginationNeeded) {
    	        products = productDAO.listProducts(page, pageSize);  // Fetch paginated products
    	    } else {
    	        products = productDAO.listProducts(1, totalProducts);  // Fetch all products if pagination not needed
    	    }

    	    request.setAttribute("products", products);
    	    request.setAttribute("paginationNeeded", paginationNeeded);
    	    request.setAttribute("currentPage", page);
    	    request.setAttribute("totalPages", (int) Math.ceil((double) totalProducts / pageSize));

    	    thymeleafUtil.returnView(request, response, "product-list", null);
    }

    private void searchProducts(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String name = request.getParameter("name");
        List<Product> products = productDAO.searchProducts(name);
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
        productDAO.saveProduct(newProduct);

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
        productDAO.updateProduct(id, updatedProduct);

        response.sendRedirect("products?action=list");
    }

  
    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        productDAO.deleteProduct(id);

        response.sendRedirect("products?action=list");
    }
}
