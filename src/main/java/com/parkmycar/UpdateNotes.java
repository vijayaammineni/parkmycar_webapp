package com.parkmycar;

import java.io.IOException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Currency;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.parkmycar.db.DBOperations;

public class UpdateNotes extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		DBOperations dbOperations = null;
		try {
			HttpSession session = request.getSession(false);
			String userId = Utils.getUserId(request);
			dbOperations = new DBOperations();
			if (userId == null || request.getParameter("cancel") != null) {
				Utils.resetNoteUpdateSessionAttributes(session);
				response.sendRedirect("index.jsp");
			} 
			else if(request.getParameter("type") != null)
			{
				String title = request.getParameter("title");
				String content = request.getParameter("content");
				byte [] contentBytes = (content != null)? content.getBytes() : (new byte[0]);
				String tags = request.getParameter("tags");
				String contentType = "text/plain";
				//insert the note
				dbOperations.insertNote(title, contentBytes, contentType, (long) contentBytes.length, Long.valueOf(userId));
				//get the note id of the inserted note
				Long noteId = dbOperations.getNoteIdByTitle(title);				
				//add all tags and link them to this note
				addTagsToNote (noteId, dbOperations, tags);
				response.sendRedirect("index.jsp");
				
			}
			else {
				long noteId = Long.parseLong(request.getParameter("noteid"));
				ResultSet noteInfoResultSet = dbOperations.getNote(
						noteId, Long.valueOf(userId));
				int dbRevisionNum = 0;
				String prevTitle = "";
				String prevContent = "";
				String prevTags = "";
				String title = request.getParameter("title");
				String content = request.getParameter("content");
				String tags = request.getParameter("tags");


				if(noteInfoResultSet.first())
				{
					dbRevisionNum = noteInfoResultSet.getInt("REVISION_NUM");
					prevTitle = noteInfoResultSet.getString("title");
					Blob blob = noteInfoResultSet.getBlob("content");
					prevTags = Utils.getTagsAsString(dbOperations.getTagsForNote(noteId));
					if (blob == null) {
						prevContent = "";
					} else {
						prevContent = new String(blob.getBytes(1, (int) blob.length()));
					}
					if (request.getParameter("Forcesave") != null) {
						dbOperations.updateNote(noteId, title, content.getBytes(), 
								Long.valueOf(userId), dbRevisionNum);
						dbOperations.unlinkAllTagsForNote(noteId);
						addTagsToNote (noteId, dbOperations, tags);
						response.sendRedirect("index.jsp");
						return;
					} else {
						int oldRevisionNum =Integer.parseInt(request.getParameter("revision_num"));
						if(oldRevisionNum == dbRevisionNum)
						{
							dbOperations.updateNote(noteId, title, content.getBytes(), 
									Long.valueOf(userId), oldRevisionNum);
							dbOperations.unlinkAllTagsForNote(noteId);
							addTagsToNote (noteId, dbOperations, tags);
							response.sendRedirect("index.jsp");
							return;
						}
						else
						{
							//save message in session
							session.setAttribute("noteid", noteId);
							session.setAttribute("prevTitle", prevTitle);
							session.setAttribute("prevContent", prevContent);
							session.setAttribute("prevTags", prevTags);
							session.setAttribute("currTitle", title);
							session.setAttribute("currContent", content);	
							session.setAttribute("currTags", tags);
							response.sendRedirect("edit.jsp");
						}
					}
				} else {
					response.sendRedirect("edit.jsp");
				}
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
	
	private void addTagsToNote(Long noteId, DBOperations dbOperations, String tags) throws SQLException
	{
		StringTokenizer st = new StringTokenizer(tags, ",");
		while(st.hasMoreTokens())
		{
			String currentTag = st.nextToken();
			if (currentTag != null &&
					!currentTag.isEmpty()) {
				Long tagId = dbOperations.getTagId(currentTag);
				//if you already have the tag use it
				if( tagId == null)
				{
					dbOperations.addTag(currentTag);
					tagId = dbOperations.getTagId(currentTag);
				}
				dbOperations.linkTagToNote(tagId, noteId);
		    }
		}
	}
	
	
	
}
