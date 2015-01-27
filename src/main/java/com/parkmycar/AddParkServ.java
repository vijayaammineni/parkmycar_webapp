package com.parkmycar;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.parkmycar.model.ParkingLocations;

/**
 * Servlet implementation class AddParkServ
 */

@WebServlet("/AddParkServ")
public class AddParkServ extends HttpServlet
{
	
	private static final long serialVersionUID = 1L;

	private DBOperations dBOperations;
	
	PrintWriter out;
	public AddParkServ() {
		super();
	}

	@Override
	public void init() throws ServletException
	{
		ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		dBOperations = (DBOperations) context.getBean("dBOperations");
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		
		out = response.getWriter();
		try {

			// Retrieving values from the web page
			double latitude = Double.parseDouble(request.getParameter("Latitude"));
			double longitude = Double.parseDouble(request
					.getParameter("Longitude"));
			String address = request.getParameter("Street Name") + "  "
					+ request.getParameter("Address2");
			String city = request.getParameter("City");
			String state = request.getParameter("State");
			int zipcode = Integer.parseInt(request.getParameter("Zip Code"));
			String name = request.getParameter("Parking_name");
			
			
			ParkingLocations parkLocation = new ParkingLocations();
		
			parkLocation.setName(name);
			parkLocation.setAddress(address);
			parkLocation.setCity(city);
			parkLocation.setState(state);
			parkLocation.setZipCode(zipcode);
			parkLocation.setLatitude(latitude);
			parkLocation.setLongitude(longitude);
			
			parkLocation = dBOperations.addParkingLocation(parkLocation);
			
			boolean success = false;
			
			if(parkLocation.getId() != null)
			{
				success = true;
			}
			
			//Add the parking location
			
			if (success) {
				RequestDispatcher rd = request
						.getRequestDispatcher("AddSuccess.jsp");
				rd.forward(request, response);
			} else {
				RequestDispatcher rd = request
						.getRequestDispatcher("AddFail.jsp");
				rd.forward(request, response);
			}

		} catch (Exception ex) {
			/*out.print("<center><h2>" + ex + "</h2></center>");*/
			RequestDispatcher rd = request.getRequestDispatcher("AddFail.jsp");
			rd.include(request, response);
		} finally {
			out.close();
		}
	}
}
