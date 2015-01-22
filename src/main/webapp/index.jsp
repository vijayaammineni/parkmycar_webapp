<%@page import="java.nio.channels.NotYetConnectedException"%>
<%@page import="com.parkmycar.db.DBOperations"%>
<%@ page language="java" import="java.sql.ResultSet" %>
<%@ page language="java" import="com.parkmycar.Utils" %>
<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7; IE=EmulateIE9">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no"/>
    <link rel="stylesheet" type="text/css" href="style.css" media="all" />
    <link rel="stylesheet" type="text/css" href="demo.css" media="all" />
    <link href="footable.core.css" rel="stylesheet" type="text/css" />
    <link href="footable.standalone.css" rel="stylesheet" type="text/css" />  
    
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
	<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
	  
    
    <script src="footable.js" type="text/javascript"></script>
    <script src="footable.paginate.js" type="text/javascript"></script>    
    
	  <script src="http://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
	  <script>
		  $(function() {
		    $( "#noteViewDialog" ).dialog({
		      autoOpen: false,
		      modal: true,
		      width: 750,
		      height: 500,
		      show: {
		        effect: "fadeIn",
		        duration: 500
		      },
		      hide: {
		        effect: "fadeOut",
		        duration: 500
		      }
		    });
		    $( ".viewNoteLink" ).click(function() {
				var viewUrl = $(this).attr("url");
				$.ajax({url: viewUrl, 
					success:function(result){
				    	$( "#noteViewDialog" ).html(result);
				    	$( "#noteViewDialog" ).dialog( "open" );
					}
				});
			});
		    //initiate the table
		    $('.footable').footable();
		 });
	 </script>
	<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
	<title>NoteIt</title>		
</head>
<body>
	<div id="noteViewDialog"></div>
	<br/>
	<%
		//allow access only if session exists
		String userId = null;
		if (session.getAttribute("userId") == null) { 
	%>
			<div class="container">
				<header>
					<img src="./images/NoteItIcon_128x128.png"></img>
					<br/>
					<div class="headertext">NoteIt</div>
					<script>
						$('.headertext').textFx({
							type: 'slideIn',
							direction: 'bottom',
							iChar: 250,
							iAnim: 1000
						});
					</script>
            	</header>			
				<div  class="form">
				    <form action="Login" method="post"> 
						<p class="contact"><label for="email">Email</label></p> 
    					<input id="email" name="email" placeholder="example@domain.com" required="" type="email" autofocus/>
                		<p class="contact"><label for="password">Password</label></p> 
    					<input type="password" id="password" name="password" placeholder="*********" required=""> 
    					<br/><br/>
    					<div>
    						<span style="width: 150px;"><input class="buttom" name="submit" id="submit" tabindex="5" value="Login" type="submit"></span>
    						<span></span>
    					</div>
					</form>
					<br/>
					<form action="register.jsp" method="post">
						<input class="buttom" id="registerbtn" tabindex="5" type="submit" value="Register">
						</div>
					</form>
				</div>	
			</div>	
	<% 
			} else {
			//display user notes
			String sessionID = null;
			
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
				    if(cookie.getName().equals("userId")) userId = cookie.getValue();
			    	if(cookie.getName().equals("JSESSIONID")) sessionID = cookie.getValue();
				}
			}
			DBOperations dbOperations = null;
	%>
		<div class="form">
			<header>
				<br>		
					<span style="padding: 10px;"><img src="./images/NoteItIcon_64x64.png"></img></span>
					<span class="headertext" style="padding-bottom: 25px; position:relative; top: -18px;">NoteIt</span>	
					<span style="position:relative; top: -45px; right: 10px;"> 	
						<form action="Logout" method="post">
			  				<input class="button" type="submit" id="logoutbtn" value="Logout" style="float: right;">
			      		</form>
			      	</span>      			      		
			</header>
		</div>
			<br/>
			<div class="form">
				<form action="index.jsp" method="post"> 
					<input id="search" name="search" placeholder="Search">
					<span><input class="button" name="searchbtn" id="search" value="Search" type="submit"></span>
					 <div style = "clear: right; float: right; text-align: right;">
					 <a href="edit.jsp?type=add" name="addLink"><img src="images/AddIcon_32x32.png"></a></div>
					
             	</form>
             	<br></br>
			<center>
		    <table class="footable" id ="notesTable" data-page-size="5" width="600pixels">
		    <thead>
				<tr>
					<th>Title</th>
					<th>Tags</th>
					<th>Created On</th>
					<th>Last Modified</th>
					<th>Options</th>
				</tr>
			</thead>			
			<tbody>
			<%
			try
			{
				//clear the edit conflict session variables first
				Utils.resetNoteUpdateSessionAttributes(session);
				String searchText = request.getParameter("search");
				dbOperations = new DBOperations();
				ResultSet rs = null;
				if(searchText != null)
				{
					rs = dbOperations.getSearchNotes(searchText,Long.valueOf(userId));
				}
				else
				{
					rs = dbOperations.getAllNotes(Long.valueOf(userId));
				}
				
				while(rs.next())
				{
					String note_title = rs.getString("TITLE");
					Long noteId = rs.getLong("NOTE_ID");
					String tags = Utils.getTagsAsString(dbOperations.getTagsForNote(noteId));
				%>
				    <tr height="50%">
				    	<td><div class="viewNoteLink" url="view?method=doGet&noteid=<%=noteId%>"><a href="#"><%=note_title%></a></div></td>
				        <td><%=tags%></td>
				        <td><%=rs.getTimestamp("DATE_CREATED") %></td>
				        <td><%=rs.getTimestamp("DATE_MODIFIED") %></td>
				        <td>
				        	<a title="edit" name="edit" href="edit.jsp?noteid=<%=rs.getInt("NOTE_ID")%>&revision_num=<%=rs.getInt("REVISION_NUM")%>">
				        		<img src="images/Edit_16x16.png"><img>
				        	</a>
				        	<a title="delete" name ="delete" href="Delete?noteid=<%=rs.getInt("NOTE_ID")%>">
				        		<img src="images/DeleteIcon_16x16.png">
				        	</a>
				        </td>
				        
				    </tr>
				<%
				}
			%>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="5">
						<div class="pagination pagination-centered hide-if-no-paging"></div>
					</td>
				</tr>
			</tfoot>
			</table>
			</center>
			</div>
		<%
	    		rs.close();
	    }
		catch(Exception e)
		{
	    	e.printStackTrace();
	   	} 
	   		finally {
				if (dbOperations != null) {
					try {
						dbOperations.closeConnection ();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}		
		%>	
	</div>
<%@ include file="footer.jsp" %>