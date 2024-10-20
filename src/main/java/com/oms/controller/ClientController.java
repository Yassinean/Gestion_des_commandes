package com.oms.controller;

import org.thymeleaf.context.WebContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.thymeleaf.ITemplateEngine;
import com.huongdanjava.jakartaee.servlet.ThymeleafConfig;
import com.oms.model.AdminType;
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
                case "/search":
                    clientSearch(request, response);
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
                	updateClient(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/client");
                    break;
            }
        }
    }


    private void listClients(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	  HttpSession session = request.getSession();
          String userType = (String) session.getAttribute("userType");

          if (!"Admin".equals(userType) ) {
              response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied.");
              return; 
          }
    	int currentPage = 1;
    	int pageSize = 6;
    	
    	String pageParam = request.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            currentPage = Integer.parseInt(pageParam);
        }
        
        try {
        	  List<Client> clients = userService.getAllClients();
        	  int totalClients = clients.size();
        	  int totalPages =(int)Math.ceil((double)totalClients /pageSize);
        	  int startIndex = (currentPage - 1)*pageSize;
              int endIndex = Math.min(startIndex + pageSize, totalClients);
              System.out.println("les admins : " +clients);
              WebContext context = new WebContext(
                  request, 
                  response, 
                  getServletContext(), 
                  request.getLocale()
              );
              List<Client> currentPageClients = clients.subList(startIndex, endIndex);
              context.setVariable("clients", currentPageClients);
              context.setVariable("currentPage", currentPage);
              context.setVariable("totalPages", totalPages);
              context.setVariable("hasPrevious", currentPage > 1);
              context.setVariable("hasNext", currentPage < totalPages);              response.setContentType("text/html;charset=UTF-8");
              templateEngine.process("users/client", context, response.getWriter());

          } catch (Exception e) {
              e.printStackTrace();
              response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to fetch clients.");
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
    
    private void updateClient(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int clientId = Integer.parseInt(request.getParameter("clientUpdate"));
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String motDePasse = request.getParameter("password");
        String adresse = request.getParameter("adresse");
        double moyen = Double.parseDouble(request.getParameter("moyen"));

        Optional<Client> existingClientOptional = userService.getClientById(clientId);

        if (!existingClientOptional.isPresent()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Client not found.");
            return;
        }

        Client existingClient = existingClientOptional.get();

        existingClient.setNom(nom);
        existingClient.setPrenom(prenom);
        existingClient.setEmail(email);

        if (motDePasse != null && !motDePasse.isEmpty()) {
            existingClient.setMotDePasse(motDePasse);
        }

        existingClient.setAdresse(adresse);
        existingClient.setMoyenPaiment(moyen);

        boolean isUpdated = userService.updateClient(existingClient);

        if (isUpdated) {
            response.sendRedirect("client");
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to update Client.");
        }
    }

    
    private void deleteClient(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("clientDelete"));
            boolean isDeleted = userService.deleteClient(id);

            if (isDeleted) {
                response.sendRedirect("client");
            }else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete client.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Invalid Client ID.");
        }
    }
    
    private void clientSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	 int currentPage = 1;
	     int pageSize = 6; 
	     List<Client> searchResults = new ArrayList<Client>();
	        
	        String pageParam = request.getParameter("page");
	        if (pageParam != null && !pageParam.isEmpty()) {
	            currentPage = Integer.parseInt(pageParam);
	        }
	        
    	String terme = request.getParameter("searchTerm");
    	if (terme != null && !terme.isEmpty()) {
			searchResults = userService.searchClient(terme);
		}else {
			listClients(request, response);
		}
    	
    	 	int totalClients = searchResults.size();
	        
	        int totalPages = (int) Math.ceil((double) totalClients / pageSize);
	        
	        int startIndex = (currentPage - 1) * pageSize;
	        int endIndex = Math.min(startIndex + pageSize, totalClients);
	        
	        List<Client> currentPageClient = searchResults.subList(startIndex, endIndex);
	        
    	WebContext context = new WebContext(
	            request,
	            response,
	            getServletContext(),
	            request.getLocale()
	        );
	        
	        context.setVariable("clients", currentPageClient);
	        context.setVariable("currentPage", currentPage);
	        context.setVariable("totalPages", totalPages);
	        context.setVariable("hasPrevious", currentPage > 1);
	        context.setVariable("hasNext", currentPage < totalPages);
	        
	        response.setContentType("text/html;charset=UTF-8");
	        templateEngine.process("users/client", context, response.getWriter());
    	
    }
}
