<%@page import="java.sql.Blob"%>
<%@ page language="java" import="java.sql.ResultSet" %>
<%@ page language="java" import="com.parkmycar.Utils" %>
<%@page import="com.parkmycar.db.DBOperations"%>
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7; IE=EmulateIE9">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no"/>
	    <link rel="stylesheet" type="text/css" href="style.css" media="all" />
	    <link rel="stylesheet" type="text/css" href="demo.css" media="all" />
	    
	    <script src="/js/jquey-1.10.1.min.js" type="text/javascript"></script>
	    <script src="/js/jquey.transit.min.js" type="text/javascript"></script>
	    <script src="/js/jquey.TexFx.js" type="text/javascript"></script>
	    
		<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
		<title>Note It</title>
	</head>
	<body>
		<div class="form">
			<header>
				<br>		
					<span style="padding: 10px;"><img src="./images/NoteItIcon_64x64.png"></img></span>
					<span class="headertext" style="padding-bottom: 25px; position:relative; top: -18px;">NoteIt</span>	
					<span style="position:relative; top: -45px; right: 10px;"> 	
						<form action="Logout" method="post">
			  				<input class="button" type="submit" value="Logout" style="float: right;">
			      		</form>
			      	</span>      			      		
			</header>
		</div>
		<br/>
		<%
		//allow access only if session exists
		Object userId = session.getAttribute("userId");
		if (userId == null || ((String) userId).isEmpty()) {
			response.sendRedirect("index.jsp");
		} else {
			
			if (session.getAttribute("error") != null) {
			  %>
			  <b style="color: red;">There was a latest version on Server, the note was reloaded. Please try editing now and saving it again.</b>
			  	<%
			}
			String type = null;
			if (request.getParameter("type") != null) {
				type = "add";
				%>
			<div class="form">
				<div class="actionTitle">Create Note</div>
				<br><br>
				<form action="UpdateNotes" method="post">
					<label for="title">Title:</label><br><br>
			        <input id="title" type="text" name="title"/>
			        <br><label for="cnt">Note:</label><br>
			        <textarea id="cnt" name="content">
			        </textarea><br/><br/>
			        <label for="tags">Tags:</label><br><br>	
			        <input id="tags" type="text" name="tags"/><br><br>			                
			        <input type="hidden" name="type" value="add"/>
			       	<input class="buttom" type="submit"  name="cancel" value="Cancel"/>
			        <input class="buttom" type="submit"  name="save" value="Save"/><br>
		       </form> 
	       </div>			
					
		<%
			} else {
		    Long noteId = (Long) session.getAttribute("noteid");
			String prevTitle = (String) session.getAttribute("prevTitle");
			String prevContent = (String) session.getAttribute("prevContent");
		    String prevTags = (String) session.getAttribute("prevTags");
			String currTitle = (String) session.getAttribute("currTitle");
			String currContent = (String) session.getAttribute("currContent");
			String currentTags = (String) session.getAttribute("currTags");
			String tags = "";
			if (prevTitle != null && prevContent != null)	{
		%>
		
		
			<div class="form">
				<div class="actionTitleWarning">Edit Conflict</div>
				<br><br>
				<form action="UpdateNotes" method="post">
					<div class="noteLimit">
					<center><b><u>Last saved version</u></b></center><br/>
						<b>Title:</b><br><div class="noteValues"><%=prevTitle%></div><br>
				        <b>Note:</b><br><div class="noteValues"><%=prevContent%></div><br>
				        <b>Tags:</b><br><div class="noteValues"><%=prevTags%></div>			        
			        <br>
			        </div>	
					<br/>
					<div class="noteLimit">
			        	<center><b><u>Your version</u></b></center><br/>
			        	<b>Title:</b><br><div class="noteValues"><%=currTitle%></div><br>
				        <b>Note:</b><br><div class="noteValues"><%=currContent%></div><br>
				        <b>Tags:</b><br><div class="noteValues"><%=currentTags%></div><br>
			        </div>
			        <br>	
			        <input type="hidden" name="title" value="<%=currTitle%>"/><br>
			        <input type="hidden" name="content" value="<%=currContent%>"/><br>
			        <input type="hidden" name="tags" value="<%=currentTags%>"/><br>			
		        	<input type="hidden" name="noteid" value="<%=noteId%>"/><br>
		        	<input class="buttom" type="submit"  name="cancel" value="Cancel"/>
		        	<input class="buttom" type="submit"  name="Forcesave" value="Save Your Version"/><br>      	
	        	</form> 
	        </div>        
		<%		
			}
			else if(type == null){		
				String title = "";
				Blob content = null;
				String contentStr = "";
				ResultSet rs = (new DBOperations()).getNote(
						Long.valueOf(request.getParameter("noteid")),
						Long.valueOf((String)userId));
				if(rs.next())
				{
					title = rs.getString("TITLE");
					content = rs.getBlob("CONTENT");
					byte[] contentData = content.getBytes(1,(int)content.length());
					contentStr = new String(contentData);
					tags = Utils.getTagsAsString(
							(new DBOperations()).getTagsForNote(
									Long.valueOf(request.getParameter("noteid"))));
				}
		%>		
	       <div class="form">
				<div class="actionTitle">Edit Note</div>
				<br>
				<form action="UpdateNotes" method="post">
					<label for="title">Title:</label><br><br>
			        <input id="title" type="text" name="title" value="<%=title%>"/>
			        <br><label for="cnt">Note:</label><br>
			        <textarea id="cnt" name="content">
			        <%=contentStr%>
			        </textarea><br/><br/>
			        <label for="tags">Tags:</label><br><br>	
					<input id="tags" type="text" name="tags" value="<%=tags%>"/>
			        <% int oldRevisionNum=Integer.parseInt(request.getParameter("revision_num")); %>
			        <input type="hidden" name="noteid" value="<%=request.getParameter("noteid")%>"/><br>
			        <!-- hidden declares the element is not shown in UI but required for only processing the request -->
			        <input type="hidden" name="revision_num" value="<%=oldRevisionNum%>"/><br>
			        <input class="buttom" type="submit"  name="save" value="Save"/>
			        <input class="buttom" type="submit"  name="cancel" value="Cancel"/>			        
		       </form> 
	       </div>
		<% 
			}
		  }
		}
		%>
	</body>
</html>