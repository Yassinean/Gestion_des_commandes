package com.oms.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthUtils {
	
	public static boolean isSuperAdmin(HttpSession session) {
        return "Admin".equals(session.getAttribute("userType")) 
            && "SUPER_ADMIN".equals(session.getAttribute("adminType"));
    }
    
    public static boolean isNormalAdmin(HttpSession session) {
        return "Admin".equals(session.getAttribute("userType")) 
            && "ADMIN".equals(session.getAttribute("adminType"));
    }
    
    public static boolean isClient(HttpSession session) {
        return "Client".equals(session.getAttribute("userType"));
    }
    
    public static void redirectIfUnauthorized(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userType") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
    }
}
