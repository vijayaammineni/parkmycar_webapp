package com.parkmycar;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.parkmycar.db.DBOperations;

public class DeleteServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		DBOperations dbOperations = null;
		try{
			long noteId = Long.parseLong(request.getParameter("noteid"));
			dbOperations = new DBOperations();
			String userId = Utils.getUserId (request);
			if (userId != null) {
				//delete note 
				dbOperations.deleteNote(noteId, Long.valueOf(userId));
			}
			response.sendRedirect("index.jsp");
	    } catch (ClassNotFoundException e) {
			
		} catch (SQLException e) {
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
