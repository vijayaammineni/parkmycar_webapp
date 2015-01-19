package com.parkmycar.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.parkmycar.AllProperties;

public class DBOperations {
	
	Log logger = LogFactory.getLog(DBOperations.class);
	
	Connection conn = null;
	public DBOperations() throws ClassNotFoundException, SQLException {
		String url = null;
		boolean isProduction = AllProperties.isProduction;
		if (isProduction) {
			Class.forName("com.mysql.jdbc.Driver");
		  	url = "jdbc:mysql://noteit.co3tkrphumxm.us-west-2.rds.amazonaws.com:3306/noteit?" + 
		  			                   "user=noteit&password=vijaya219";
		} else {
			Class.forName("com.mysql.jdbc.Driver");
			url = "jdbc:mysql://localhost/noteit?" +
	                                   "user=root&password=root";
		}
		conn = DriverManager.getConnection(url);
	}
	
	public Long getUserId(String email, String password) throws SQLException {
		Statement stmt1 = conn.createStatement();
    	String query = "select uid from users where email like '"+email+"' and password like '" + password + "'";
    	ResultSet getUserResultSet = stmt1.executeQuery(query);    	
		if (!getUserResultSet.next()) {
			logger.debug("No users found");
		} else {
		    return getUserResultSet.getLong("uid");
		}
		return null; 
	}
	
	public boolean addUser (String firstName, String lastName, String email, String password) throws SQLException {
		Statement stmt = conn.createStatement();
		String query = "INSERT INTO  users (FIRST_NAME, LAST_NAME, EMAIL, PASSWORD) VALUES (" 
				+ "'" + firstName + "'" 
				+  ", '" + lastName + "'"
				+  ", '" + email + "'"
				+  ", '" + password + "'"
				+  ");";
		int userSaveStatus = stmt.executeUpdate(query);
		if(userSaveStatus != 0)
			return true;
		else 
			return false;
	}
	
	public boolean updateSessionId (Long userId, String sessionId) throws SQLException {
		Statement stmt = conn.createStatement();
		String query = "update users set session_id=" 
				+ "'" + sessionId + "'" 
				+  " where uid=" + userId;
		int userSaveStatus = stmt.executeUpdate(query);
		if(userSaveStatus != 0)
			return true;
		else 
			return false;
	}
	
	public boolean emailExists (String email) throws SQLException {
		Statement stmt1 = conn.createStatement();
    	String query = "select uid from users where email like '"+email+"'";
    	ResultSet getUserResultSet = stmt1.executeQuery(query);    	
		if (!getUserResultSet.next()) {
			return false; 
		} else {
		    return true;
		}		
	}
	
	public ResultSet getTagsForNote (Long noteId) throws SQLException {
		Statement stmt1 = conn.createStatement();
    	String query = "select tag_name from tags where tag_id in (select tag_id from notetaglink where note_id="+noteId+")";
    	ResultSet getTagResultSet = stmt1.executeQuery(query);    	
		return getTagResultSet;	
	}
	
	public int unlinkAllTagsForNote (Long noteId) throws SQLException {
		Statement stmt1 = conn.createStatement();
    	String query = "delete from notetaglink where note_id="+noteId+";";    	
		return stmt1.executeUpdate(query);	
	}
	
	public Long getTagId (String tag) throws SQLException {
		Statement stmt1 = conn.createStatement();
    	String query = "select tag_id from tags where tag_name like '"+tag+"'";
    	ResultSet getTagResultSet = stmt1.executeQuery(query);    	
		if (!getTagResultSet.next()) {
			return null; 
		} else {
		    return getTagResultSet.getLong("tag_id");
		}		
	}
	
	public boolean addTag (String tag) throws SQLException {
		Statement stmt = conn.createStatement();
		String query = "INSERT INTO  tags (tag_name) VALUES (" 
				+ "'" + tag + "'"
				+  ");";
		int tagSaveStatus = stmt.executeUpdate(query);
		if(tagSaveStatus != 0)
			return true;
		else 
			return false;
	}
	
	public boolean linkTagToNote (Long tagId, Long noteId) throws SQLException {
		Statement stmt = conn.createStatement();
		String query = "INSERT INTO  notetaglink (tag_id, note_id) VALUES (" 
				+ tagId
				+ ", " + noteId
				+  ");";
		int tagNoteLinkSaveStatus = stmt.executeUpdate(query);
		if(tagNoteLinkSaveStatus != 0)
			return true;
		else 
			return false;
	}
	
	public ResultSet getAllNotes(Long userId) throws SQLException
    {
    	Statement stmt = conn.createStatement();
    	String query="select * from notes where uid=" + userId + " order by date_modified desc;";
    	ResultSet rs = stmt.executeQuery(query);
		return rs;    	
    }
	
	public ResultSet getNote(Long noteId, Long userId) throws SQLException
    {
		Statement stmt1 = conn.createStatement();
    	String query = "select * from notes where note_id=" + noteId + " and uid=" + userId;
    	ResultSet getTagResultSet = stmt1.executeQuery(query);    	
		return getTagResultSet;  	
    }
	
	public Integer getNoteRevisionNumber(Long noteId) throws SQLException
    {
		Statement stmt1 = conn.createStatement();
    	String query = "select revision_num from notes where note_id=" + noteId;
    	ResultSet getTagResultSet = stmt1.executeQuery(query);    	
		if (!getTagResultSet.next()) {
			return null; 
		} else {
		    return getTagResultSet.getInt("revision_num");
		}  	
    }
	
    public boolean insertNote(String title, byte [] content, String contentType, Long size, Long userId) throws SQLException {
    		Statement stmt = conn.createStatement();
    		String query = "INSERT INTO  notes (TITLE, CONTENT, CONTENT_TYPE, SIZE, DATE_CREATED, DATE_MODIFIED, UID) VALUES ('" 
    				+ title + "', " + convertByteArrayToMySqlByteArray(content) 
    				+ ", '" + contentType + "', " + size + ", now(), now()," + userId + ");";
    		int insertNoteStatus = stmt.executeUpdate(query);
    		if(insertNoteStatus != 0)
    			return true;
    		else 
    			return false;
	}
    
    public boolean updateNote(Long noteId, String title, byte [] content, Long userId, Integer revisionNumber) throws SQLException {
		Statement stmt = conn.createStatement();
		String query = "update notes set " 
				+ "title='" + title + "', content=" + convertByteArrayToMySqlByteArray(content) 
				+ ", size=" + content.length + ", date_modified=now(), revision_num=(revision_num+1) where note_id=" + noteId + " and uId="+userId+";";
		int insertNoteStatus = stmt.executeUpdate(query);
		if(insertNoteStatus != 0)
			return true;
		else 
			return false;
    }       
    
    public int deleteNote(Long noteId, Long userId) throws SQLException
    {
    	// check if the note exists and belongs to this user
    	Statement stmt1 = conn.createStatement();
    	String query = "select note_id from notes where note_id=" + noteId + " and uid=" + userId;
    	ResultSet getTagResultSet = stmt1.executeQuery(query);    	
		if (getTagResultSet.first()) {
			// first delete the notetaglink entries to unlink the tag
			Statement stmt = conn.createStatement();
	    	String query1 = "delete from notetaglink where note_id="+noteId+";";
	    	int deleteResult1 = stmt.executeUpdate(query1);
	    	// now delete the actual note
	    	stmt = conn.createStatement();
	    	String query2 = "delete from notes where note_id="+noteId+" and uid=" + userId + ";";
	    	int deleteResult2 = stmt.executeUpdate(query2);
	    	return deleteResult1 & deleteResult2;
		} else {
			return 0;
		}
    }    
    
    private String convertByteArrayToMySqlByteArray (byte [] arr) {
    	StringBuffer sb = new StringBuffer();
    	sb.append("CHAR(");
    	for (int i = 0; i < arr.length; i++) {
    		sb.append(arr[i]);
    		if (i != (arr.length - 1))
    			sb.append(", ");
    	}
    	sb.append(")");
    	return sb.toString();
    }
    public ResultSet getSearchNotes(String search , long userId) throws SQLException
    {
    	Statement stmt = conn.createStatement();
    	String searchText = search.replace('*', '%');
    	String query="select * from notes where (title like '%"+searchText+"%' or "
    			+ "(note_id in (select note_id from notetaglink where tag_id in "
    			+ "(select tag_id from tags where tag_name like '%"+searchText+"%')))) and uid="+userId+"  order by date_modified desc;;";
    	ResultSet rs = stmt.executeQuery(query);
		return rs;    	
    }

	public Long getNoteIdByTitle(String title) throws SQLException {
		Statement stmt1 = conn.createStatement();
    	String query = "select note_id from notes where title like '%" + title + "%' order by note_id desc;";
    	ResultSet getTagResultSet = stmt1.executeQuery(query);    	
		if (!getTagResultSet.next()) {
			return null; 
		} else {
		    return getTagResultSet.getLong("note_id");
		}
	}
	
	public void closeConnection() throws SQLException {
		if (conn != null) {
			conn.close();
		}
	}

	public String getUser(Long userId) throws SQLException {
		
		Statement stmt1 = conn.createStatement();
    	String query = "select first_name from users where uid="+ String.valueOf(userId);
    	ResultSet getUserResultSet = stmt1.executeQuery(query);    	
		if (getUserResultSet.first()) {
			return getUserResultSet.getString("first_name"); 
		} else {
		    return null;
		}
	}
   
}
