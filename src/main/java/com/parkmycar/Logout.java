package com.parkmycar;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class Logout extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setStatus(400);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException
	{ response.setContentType("text/html");
    Cookie[] cookies = request.getCookies();
    request.getSession().setAttribute("userId", null);
    if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("userId")){
                        cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
                if(cookie.getName().equals("JSESSIONID")){
                        cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
    }
    response.sendRedirect("index.jsp");  }
}
