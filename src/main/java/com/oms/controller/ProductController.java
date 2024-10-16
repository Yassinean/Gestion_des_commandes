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

    // List all products and forward to the Thymeleaf template
    private void listProducts(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Product> products = productDAO.listProducts();
        request.setAttribute("products", products);

        // Use ThymeleafUtil to process the template
        thymeleafUtil.returnView(request, response, "product-list", null);
    }

    // Insert a new product into the database
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

    // Update an existing product in the database
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

    // Delete a product from the database
    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        productDAO.deleteProduct(id);

        response.sendRedirect("products?action=list");
    }
}
