package com.oms.controller;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;

import com.huongdanjava.jakartaee.servlet.ThymeleafConfig;

/**
 * Servlet implementation class UserController
 */
public class AuthenticationController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ITemplateEngine templateEngine;

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        this.templateEngine = (ITemplateEngine) servletContext.getAttribute(ThymeleafConfig.TEMPLATE_ENGINE_ATTR);

        if (this.templateEngine == null) {
            throw new ServletException("Thymeleaf template engine not initialized");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
        
        ServletContext servletContext = getServletContext();
        WebContext context = new WebContext(request, response, servletContext);
        
        templateEngine.process("users/Login", context, response.getWriter());
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");

//        String action = request.getPathInfo();
     
        }
    }