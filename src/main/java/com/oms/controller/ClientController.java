package com.oms.controller;

import org.thymeleaf.context.WebContext;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.thymeleaf.ITemplateEngine;
import com.huongdanjava.jakartaee.servlet.ThymeleafConfig;
import com.oms.model.Client;
import com.oms.service.UserService;


/**
 * Servlet implementation class UserController
 */
public class ClientController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService;
    private ITemplateEngine templateEngine;

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
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");

        String action = request.getPathInfo();
        if (action == null || action.equals("/")) {
            listClients(request, response);
        } else {
            switch (action) {
                case "/listClients":
                    listClients(request, response);
                    break;
                case "/deleteClient":
                    deleteClient(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/client");
                    break;
            }
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");

        String action = request.getPathInfo();
        if (action == null || action.equals("/")) {
            listClients(request, response);
        } else {
            switch (action) {
                case "/insertClient":
                	insertClient(request, response);
                    break;
                case "/updateClient":
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/client");
                    break;
            }
        }
    }


    private void listClients(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Client> clients = userService.getAllClients();

            WebContext context = new WebContext(
                request, 
                response, 
                getServletContext(), 
                request.getLocale()
            );
            context.setVariable("clients", clients);
            response.setContentType("text/html;charset=UTF-8");
            templateEngine.process("users/client", context, response.getWriter());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void insertClient(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        double moyenPaiement = Double.parseDouble( request.getParameter("moyen"));
        String adresse = request.getParameter("adresse");

        Client client = new Client();
        client.setNom(name);
        client.setPrenom(prenom);
        client.setEmail(email);
        client.setMotDePasse(password);
        client.setAdresse(adresse);
        client.setMoyenPaiment(moyenPaiement);


        boolean isCreated = userService.createClient(client);

        if (isCreated) {
            response.sendRedirect("client");
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to create client.");
        }
    }
    
    private void deleteClient(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            boolean isDeleted = userService.deleteClient(id);

            if (isDeleted) {
                response.sendRedirect(request.getContextPath() + "/client");
            }else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete client.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Invalid Client ID.");
        }
    }
}
