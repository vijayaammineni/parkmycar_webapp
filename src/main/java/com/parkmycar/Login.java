package com.parkmycar;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.parkmycar.db.DBOperations;

@SuppressWarnings("serial")
public class Login extends HttpServlet {
	
	private static final String SERVER_ERROR = "Unknown error occured on server.";
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException
	{
		DBOperations dbOperations = null;
		String username = request.getParameter("email");
		String password = request.getParameter("password");
		if (username == null || password == null) {
			
		} else {
			try {
				dbOperations = new DBOperations();
				Long userId = dbOperations.getUserId(username, password);
				if (userId != null) {
					//login successful
					HttpSession session = request.getSession();
		            session.setAttribute("userId", String.valueOf(userId));
		            session.setMaxInactiveInterval(Constants.SESSION_TIMEOUT);
		            Cookie userCookie = new Cookie("userId", String.valueOf(userId));
		            userCookie.setMaxAge(Constants.SESSION_TIMEOUT);
		            response.addCookie(userCookie);		            
				}
				response.sendRedirect("index.jsp");
			} catch (Exception e) {
				e.printStackTrace();
				response.setStatus(500);
			    response.getOutputStream().write(SERVER_ERROR.getBytes());
			} finally {
				if (dbOperations != null) {
					try {
						dbOperations.closeConnection ();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
	}

}
