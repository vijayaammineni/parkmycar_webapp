package com.parkmycar;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Utils {
	
	public static String getUserId (HttpServletRequest request) {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		return userId;
	}
	
	public static void resetNoteUpdateSessionAttributes (HttpSession session) {
		session.setAttribute("noteid", null);
		session.setAttribute("prevTitle", null);
		session.setAttribute("prevContent", null);
		session.setAttribute("prevTags", null);
		session.setAttribute("currTitle", null);
		session.setAttribute("currContent", null);
	}
	
	public static String getTagsAsString (ResultSet rs) throws SQLException {
		StringBuffer sb = new StringBuffer();
		int i = 0;
		if (rs != null) {
			while (rs.next()) {
				if (i != 0) {
					sb.append(", ");
				}
				sb.append(rs.getString("tag_name"));
				i++;
			}
		}
		return sb.toString();
	}
	
}
