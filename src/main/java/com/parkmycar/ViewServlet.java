package com.parkmycar;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;

import com.parkmycar.db.DBOperations;

public class ViewServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		DBOperations dbOperations = null;
		try{
			String userId = Utils.getUserId(request);
			if (userId == null) {
				response.sendRedirect("index.jsp");
			} else {
				dbOperations = new DBOperations();
				long noteId = Long.parseLong(request.getParameter("noteid"));
				ResultSet noteInfoResultSet = dbOperations.getNote(noteId, Long.valueOf(userId));
				String contentType = null;
				Blob content = null;
				if(noteInfoResultSet.next()){
					 contentType = noteInfoResultSet.getString("content_type");
					 content = noteInfoResultSet.getBlob("content");
				}
				byte[] contentData = content.getBytes(1,(int)content.length());
				response.setStatus(200);
		        OutputStream out = response.getOutputStream();
		        if (contentType.indexOf("image") >=0) {
		        	response.setContentType("text/html");
		        	String html = "<html><head></head><body><img style='display:block; width:500px;height:500px;' " + 
		        			"id='base64image' src='data:"+contentType+";base64, " + 
		        			(new String(Base64.encodeBase64(contentData)))+ "'></body></html>";
		        	out.write(html.getBytes());
		        } else {
		        	response.setContentType(contentType);
		        	out.write(contentData);
		        }
		        out.flush();
		        out.close();
			}
	    } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
