package com.oms.controller;
import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;

import com.huongdanjava.jakartaee.servlet.ThymeleafConfig;
import com.oms.model.Admin;
import com.oms.model.Client;
import com.oms.service.UserService;

/**
 * Servlet implementation class UserController
 */
public class AuthenticationController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ITemplateEngine templateEngine;
    private UserService userService;


    @Override
    public void init() throws ServletException {
    	userService = new UserService();
        ServletContext servletContext = getServletContext();
        this.templateEngine = (ITemplateEngine) servletContext.getAttribute(ThymeleafConfig.TEMPLATE_ENGINE_ATTR);

        if (this.templateEngine == null) {
            throw new ServletException("Thymeleaf template engine not initialized");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("logout".equals(action)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate(); 
            }
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "0");

            ServletContext servletContext = getServletContext();
            WebContext context = new WebContext(request, response, servletContext);

            templateEngine.process("users/Login", context, response.getWriter());
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String userType = request.getParameter("userType");

        System.out.println("Tentative de connexion:");
        System.out.println("Email: " + email);
        System.out.println("Type utilisateur: " + userType);

        if (userType == null || userType.isEmpty()) {
            handleError(request, response, "Veuillez sélectionner un type d'utilisateur.");
            return;
        }

        HttpSession session = request.getSession();

        try {
            if ("Admin".equals(userType)) {
                Optional<Admin> adminOpt = userService.authenticateAdmin(email, password);
                if (adminOpt.isPresent()) {
                    Admin admin = adminOpt.get();
                    session.setAttribute("userEmail", email);
                    session.setAttribute("userType", userType);
                    session.setAttribute("adminType", admin.getAdminType());
                    System.out.println("Admin authentifié avec succès");
                    if ("SUPER_ADMIN".equals(admin.getAdminType().name())) {
                        response.sendRedirect("admin");
                    } else {
                        response.sendRedirect("client");
                    }

                } else {
                    System.out.println("Échec authentification admin");
                    handleError(request, response, "Email ou mot de passe incorrect pour admin.");
                }
            } else if ("Client".equals(userType)) {
                Optional<Client> clientOpt = userService.authenticateClient(email, password);
                if (clientOpt.isPresent()) {
                    session.setAttribute("userEmail", email);
                    session.setAttribute("userType", userType);
                    System.out.println("Client authentifié avec succès");
                    response.sendRedirect(request.getContextPath() + "/products");
                } else {
                    System.out.println("Échec authentification client");
                    handleError(request, response, "Email ou mot de passe incorrect pour client.");
                }
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de l'authentification: " + e.getMessage());
            e.printStackTrace();
            handleError(request, response, "Une erreur est survenue lors de l'authentification.");
        }
    }    private void handleError(HttpServletRequest request, HttpServletResponse response, String message) 
            throws ServletException, IOException {
        ServletContext servletContext = getServletContext();
        WebContext context = new WebContext(request, response, servletContext);
        context.setVariable("errorMessage", message);
        templateEngine.process("users/Login", context, response.getWriter());
    }
    
}