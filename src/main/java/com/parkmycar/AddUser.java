package com.parkmycar;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.parkmycar.db.DBOperations;

/**
 * Servlet implementation class AddUser
 */
public class AddUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SERVER_ERROR = "Error occured while connecting to Db";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddUser() {
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
		DBOperations dbOperations = null;
		String firstName = request.getParameter("fname");
		String lastName = request.getParameter("lname");
		String password = request.getParameter("password");
		String username = request.getParameter("email");

		if (username == null || password == null || lastName == null
				|| firstName == null) {

		} else {
			try {
				dbOperations = new DBOperations();
				if (dbOperations.emailExists(username)) {
					response.getOutputStream().write(
							"An account already exists with this email address".getBytes());
				} else {
					boolean signupStatus = dbOperations.addUser(
							firstName, lastName, username, password);
					if (signupStatus) {
						response.sendRedirect("index.jsp");
					} else {
						response.sendRedirect("register.jsp");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.setStatus(500);
				response.getOutputStream().write(SERVER_ERROR.getBytes());
			}
			finally {
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
