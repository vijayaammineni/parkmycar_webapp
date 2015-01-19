<%@ page language="java" import="java.sql.ResultSet" %>
<html>
	<head>
		<title>Easy Cloud Storage - Share Files</title>
		<style>
			html, body { height: 100% }
			body {
			  margin: 0 auto;
			  text-align: center;
			  background-image: url("http://www.cloudstoragebest.com/wp-content/uploads/2013/03/Cloud-Storage-Services-Consumer-vs-Enterprise.jpg");
			  background-size: cover;
			}
			.ppce_input{
				color: #800080;
				display: block;
				line-height:300%;
				font-family:"Times New Roman";
				font-size:20px;
			}
			
			.hidden{
				display: none;
			}
			table,th,td
			{
				border:1px solid black;
				border-collapse:collapse;
			}
			th,td
			{
				padding:5px;
			}
			
			</style>
	</head>
	<body>
		<h1>Sharing file: <%=  request.getParameter("name")%></h1>
		<br/>
		<h3>Please enter comma separated list of email addresses below to share the file: </h3>
		<br/>
		<form action="sharefile" method="post" enctype="multipart/form-data">
			<input type="hidden" name="fileId" value="<%= request.getParameter("id")%>">
			<textarea name="emailIds" rows="5" cols="75"></textarea>
			<br/><br/>
			<input type="submit" value="Share">
		</form>
	</body>
</html>