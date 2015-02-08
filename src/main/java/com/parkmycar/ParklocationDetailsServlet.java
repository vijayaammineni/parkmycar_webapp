package com.parkmycar;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
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
import com.parkmycar.json.AvailabilityJSONObj;
import com.parkmycar.json.ParkMyCarJson;
import com.parkmycar.json.ParkingLocationsJSONObj;
import com.parkmycar.json.PricingJSONObj;
import com.parkmycar.model.ParkingLocations;
import com.parkmycar.model.Pricing;
import com.parkmycar.model.UserFeedback;
import com.parkmycar.model.enumeration.UserFeedbackType;

@WebServlet("/ParkingLocationDetails")
public class ParklocationDetailsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private DBOperations dBOperations;

	public ParklocationDetailsServlet() {
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

			String locationIdStr = request.getParameter("id");
			List<ParkingLocations> plist = null;
			int locationId = 0;
			if (locationIdStr != null) {
				locationId = Integer.parseInt(locationIdStr);
				plist = dBOperations.getParkingLocationById(locationId);

			} else {
				response.setStatus(HttpStatus.SC_BAD_REQUEST);
				return;
			}

			ParkMyCarJson parkingLocationJson = new ParkMyCarJson();

			ArrayList<ParkingLocationsJSONObj> jsonObjList = new ArrayList<ParkingLocationsJSONObj>();

			if (plist != null && !plist.isEmpty()) {
				Iterator<ParkingLocations> plIter = plist.iterator();

				while (plIter.hasNext()) {
					ParkingLocationsJSONObj jsonObj = new ParkingLocationsJSONObj();
					ParkingLocations pl = plIter.next();
					jsonObj.setLatitude(pl.getLatitude());
					jsonObj.setLongitude(pl.getLongitude());
					jsonObj.setAddress(pl.getAddress());
					jsonObj.setName(pl.getName());
					jsonObj.setCategory(pl.getCategory());
					jsonObj.setCity(pl.getCity());
					jsonObj.setDownVotes(pl.getDownVotes());
					jsonObj.setUpVotes(pl.getUpVotes());
					jsonObj.setState(pl.getState());
					jsonObj.setZipcode(pl.getZipCode());
					jsonObj.setPricingDetailsList(getPricingJSONObjList(locationId));
					jsonObj.setAvailabilityObj(getAvailabilityJSONObj(locationId));
					jsonObjList.add(jsonObj);
				}
			}

			parkingLocationJson.setParkingLocations(jsonObjList);
			Gson gson = new Gson();
			String jsonStr = gson.toJson(parkingLocationJson);
			OutputStream os = response.getOutputStream();
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

	private List<PricingJSONObj> getPricingJSONObjList(int parkingLocationId) {

		ArrayList<PricingJSONObj> pricingJsonList = new ArrayList<PricingJSONObj>();
		List<Pricing> pDetailslist = dBOperations
				.getPricingForParkingLocation(parkingLocationId);
		if (pDetailslist != null && !pDetailslist.isEmpty()) {
			Iterator<Pricing> pricingIter = pDetailslist.iterator();
			while (pricingIter.hasNext()) {
				Pricing p = pricingIter.next();
				PricingJSONObj newPricingJsonObj = new PricingJSONObj();
				newPricingJsonObj.setDayOfWeek(p.getDayOfWeek());
				newPricingJsonObj.setDayPrice(p.getDayPrice());
				newPricingJsonObj.setHourlyPrice(p.getHourlyPrice());
				pricingJsonList.add(newPricingJsonObj);
			}

		}
		return pricingJsonList;
	}

	private AvailabilityJSONObj getAvailabilityJSONObj(int parkingLocationId)
	{
		Date date = new Date(System.currentTimeMillis()-(30*60*1000));
		AvailabilityJSONObj availabilityJson = new AvailabilityJSONObj();
		List<UserFeedback> availabilityList = dBOperations.getUserFeedbackForParkingLocation(parkingLocationId, 
											date, 5);
		if(availabilityList != null && !availabilityList.isEmpty())
		{
			Iterator<UserFeedback> feedbackIter = availabilityList.iterator();
			int availableCount = 0;
			int unAvailableCount = 0;
			int ChekedOutCount = 0;
			int parkedCount = 0;
			while(feedbackIter.hasNext())
			{
				UserFeedback f = feedbackIter.next();
				if(f.getUserFeedbackType().equals(UserFeedbackType.AVAILABLE))
					availableCount++;
				else if(f.getUserFeedbackType().equals(UserFeedbackType.CHECKOUT))
					unAvailableCount++;
				else if(f.getUserFeedbackType().equals(UserFeedbackType.NOTAVAILABLE))
					parkedCount++;
				else
					ChekedOutCount++;
			}
			availabilityJson.setAvailableVotes(availableCount);
			availabilityJson.setCheckedOutNum(ChekedOutCount);
			availabilityJson.setParkedNum(parkedCount);
			availabilityJson.setUnAvailableVotes(unAvailableCount);
		}
		
		return availabilityJson;
	}
}
