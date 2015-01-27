package com.parkmycar;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Servlet implementation class LoginServ
 */
@WebServlet("/LoginServ")
public class LoginServ extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private DBOperations dBOperations;

	@Override
	public void init() throws ServletException
	{
		ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		dBOperations = (DBOperations) context.getBean("dBOperations");
	}
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServ() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String uname = request.getParameter("username");
		String passwd = request.getParameter("password");
		if (uname.equals("Admin") && passwd.equals("123456"))
		{
			HttpSession session = request.getSession();
            session.setAttribute("dBOperations", dBOperations);
            session.setAttribute("userId", uname);
            session.setMaxInactiveInterval(Constants.SESSION_TIMEOUT);
            Cookie userCookie = new Cookie("userId", uname);
            userCookie.setMaxAge(Constants.SESSION_TIMEOUT);
            response.addCookie(userCookie);		            
			request.setAttribute("dBOperations", dBOperations);
			response.sendRedirect("SeeAllParkLocs.jsp");
			//getServletContext().getRequestDispatcher("/SeeAllParkLocs.jsp").forward(request, response);
		} else {
			out.print("<center><h3 style=' color:  red' >Username or Password is incorrect !!!</h3></center>");
			RequestDispatcher rd = request.getRequestDispatcher("Login.html");
			rd.include(request, response);

		}

	}

}
