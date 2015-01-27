package com.parkmycar;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gson.Gson;
import com.parkmycar.json.ParkMyCarJson;
import com.parkmycar.json.ParkingLocationsJSONObj;
import com.parkmycar.model.ParkingLocations;

/**
 * Servlet implementation class AddParkServ
 */

@WebServlet("/ParkingLocations")
public class ParkingLocationsAndroidServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private DBOperations dBOperations;

	public ParkingLocationsAndroidServlet() {
		super();
	}

	@Override
	public void init() throws ServletException {
		ApplicationContext context = WebApplicationContextUtils
				.getRequiredWebApplicationContext(getServletContext());
		dBOperations = (DBOperations) context.getBean("dBOperations");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setStatus(HttpStatus.SC_METHOD_NOT_ALLOWED);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			
			String latStr = request.getParameter("latitude");
			String longStr = request.getParameter("longitude");
			String address = request.getParameter("address");

			List<ParkingLocations> plist = null;
			
			if (latStr != null && longStr != null && !latStr.isEmpty()
					&& !longStr.isEmpty()) {
				double latitude = Double.parseDouble(latStr);
				double longitude = Double.parseDouble(longStr);
				plist = dBOperations
				.getNearestParkingLocations(latitude, longitude, 20);

			} 
			else if (address != null && !address.isEmpty()) 
			{
				//TBD: geocode the address, get the lat&long and find the parking locations in the DB
			} else {
				response.setStatus(HttpStatus.SC_BAD_REQUEST);
				return;
			}
			
			
			ParkMyCarJson parkingLocationJson = new ParkMyCarJson();

			ArrayList<ParkingLocationsJSONObj> jsonObjList = new ArrayList<ParkingLocationsJSONObj>();

			if (plist != null
					&& !plist.isEmpty()) {
				Iterator<ParkingLocations> plIter = plist.iterator();
	
				while (plIter.hasNext()) 
				{
					ParkingLocationsJSONObj jsonObj = new ParkingLocationsJSONObj();
					ParkingLocations pl = plIter.next();
					jsonObj.setLatitude(pl.getLatitude());
					jsonObj.setLongitude(pl.getLongitude());
					jsonObj.setAddress(pl.getAddress());
					jsonObj.setName(pl.getName());
					jsonObjList.add(jsonObj);
				}
			}

			parkingLocationJson.setParkingLocations(jsonObjList);
			Gson gson = new Gson();
			String jsonStr = gson.toJson(parkingLocationJson);
			OutputStream os =  response.getOutputStream();
			response.setContentType("application/json;charset=UTF-8");
			if (os != null) {
				os.write(jsonStr.getBytes());
				os.close();
			}
			response.setStatus(HttpStatus.SC_OK);

		} catch (Exception ex) {
			response.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
			ex.printStackTrace();
		}
	}
}
