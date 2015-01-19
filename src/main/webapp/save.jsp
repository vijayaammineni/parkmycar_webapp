<%@ page language="java" import="java.sql.ResultSet" %>
<%@page import="com.parkmycar.db.DBOperations"%>
<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
<%

long oldRevisionNum = Long.valueOf(request.getParameter("revision_num"));
long noteId = Long.valueOf(request.getParameter("noteid"));
long presentRevisionNum = (new DBOperations()).getNoteRevisionNumber(noteId);

if(oldRevisionNum == presentRevisionNum)
{
	
	%>
	</body>
	<h1>Saved succesfully!!</h1>
	</html>
	<%
	}
else
{
	
	}

%>