
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page
	import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.swing.JOptionPane"%>
<%@page import="com.parkmycar.DBOperations"%>
<%@page import="com.parkmycar.model.ParkingLocations"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<head>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon">
<link rel="icon" href="/favicon.ico" type="image/x-icon">
</head>
<style>
body {
	background-color: lightgray
}

h1 {
	color: blue
}
</style>

<title>See All Parking Locations</title>
</head>
<body>

	<center>


		<h1>Available Parking Locations</h1>

		<table style="width: 100%">

			<tr>
				<th>Name</th>
				<th>Latitude</th>
				<th>Longitude</th>
				<th>Address</th>
				<th>City</th>
				<th>State</th>
				<th>Zip code</th>
				<th>Date Added</th>
				<th>Date Modified</th>

			</tr>

			<%
			
			   DBOperations dBOperations = (DBOperations) request.getSession().getAttribute("dBOperations");
				List<ParkingLocations> parkingLocationsList = dBOperations.getAllParkingLocations();
				Iterator<ParkingLocations> it = parkingLocationsList.iterator();
				
				while (it.hasNext()) {
					ParkingLocations pl = it.next();
			%>
			<tr>

				<td style='text-align: center; vertical-align: middle'><%=pl.getName()%></td>
				<td style='text-align: center; vertical-align: middle'><%=pl.getLatitude()%></td>
				<td style='text-align: center; vertical-align: middle'><%=pl.getLongitude()%></td>
				<td style='text-align: center; vertical-align: middle'><%=pl.getAddress()%></td>
				<td style='text-align: center; vertical-align: middle'><%=pl.getCity()%></td>
				<td style='text-align: center; vertical-align: middle'><%=pl.getState()%></td>
				<td style='text-align: center; vertical-align: middle'><%=pl.getZipCode()%></td>
				<td style='text-align: center; vertical-align: middle'><%=pl.getDateAdded()%></td>
				<td style='text-align: center; vertical-align: middle'><%=pl.getDateModified()%></td>
				<%-- <td style='text-align: center; vertical-align: middle'><a
					href="Review.jsp?id=<%=pl.getId()%>" title="Click for reviews"><img
						src="review-icon.png" alt="Click here for Review"
						style="color: #79C255; width: 30px; height: 30px; border: 0"></a></td> --%>
			</tr>
			<%
				}
			%>
		</table>
		<br /> <br />
		<form action="AddPark.html">
			<input type="submit" value="Add Parking Location">
		</form>
	</center>

</body>
</html>