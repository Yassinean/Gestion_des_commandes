package com.oms.controller;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.classmate.Filter;
import com.oms.model.AdminType;

public class UserFilter implements Filter {
    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
        
        if (isPublicResource(path)) {
            chain.doFilter(httpRequest, httpResponse);
            return;
        }
        
        if (session == null || session.getAttribute("userEmail") == null) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            return;
        }
        
        String userType = (String) session.getAttribute("userType");
        
        if (path.startsWith("/admin/")) {
            if (!"Admin".equals(userType)) {
                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            
            if (path.contains("/admin-management") && 
                session.getAttribute("adminType") != AdminType.SUPER_ADMIN) {
                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }
        
        if (path.startsWith("/client/") && !"Client".equals(userType)) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        
        chain.doFilter(request, response);
    }
    
    private boolean isPublicResource(String path) {
        return path.equals("/login") || 
               path.startsWith("/assets/") || 
               path.startsWith("/css/") || 
               path.startsWith("/js/");
    }

	@Override
	public boolean include(Object element) {
		// TODO Auto-generated method stub
		return false;
	}
}