package com.oms.controller;
import org.thymeleaf.context.WebContext;  

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.ITemplateEngine;

import com.huongdanjava.jakartaee.servlet.ThymeleafConfig;
import com.oms.service.UserService;

/**
 * Servlet implementation class UserController
 */
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService;
        
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	@Override
	public void init() throws ServletException {
	    userService = new UserService();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ITemplateEngine templateEngine = (ITemplateEngine) getServletContext().getAttribute(ThymeleafConfig.TEMPLATE_ENGINE_ATTR);
        WebContext context = new WebContext(request, response, getServletContext(), request.getLocale());
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        templateEngine.process("user", context, response.getWriter());
        String action = request.getPathInfo();
		if (action == null) {
			action = "/";
		}else {
			try {
				switch (action) {
				case "/list":
					
					break;
				case "/delete" :
					break;
				default:
					break;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    }



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getPathInfo();
		if (action == null) {
			action = "/";
		}else {
			try {
				switch (action) {
				case "/insert":
					
					break;
				case "/update" :
					break;
				default:
					break;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

}
