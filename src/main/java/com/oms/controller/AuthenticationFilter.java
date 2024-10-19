package com.oms.controller;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();
        HttpSession session = httpRequest.getSession(false);

        boolean isLoggedIn = (session != null && session.getAttribute("userEmail") != null);
        boolean isLoginRequest = requestURI.equals(httpRequest.getContextPath() + "/login");
        boolean isAdminPage = requestURI.startsWith(httpRequest.getContextPath() + "/admin");
        boolean isClientPage = requestURI.startsWith(httpRequest.getContextPath() + "/client");

        // Redirect to login if not logged in and trying to access a protected page
        if (!isLoggedIn && !isLoginRequest) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            return;
        }

        // Check user type and restrict access to specific sections
        if (isLoggedIn) {
            String userType = (String) session.getAttribute("userType");

            if ("Admin".equals(userType) && isClientPage) {
                // Admin trying to access client pages
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/admin");
                return;
            } else if ("Client".equals(userType) && isAdminPage) {
                // Client trying to access admin pages
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/client");
                return;
            }
        }

        // Proceed to the requested resource
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}
