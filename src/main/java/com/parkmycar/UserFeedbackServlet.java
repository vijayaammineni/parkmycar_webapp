package com.parkmycar;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpStatus;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gson.Gson;
import com.parkmycar.json.UserFeedbackJSONObj;
import com.parkmycar.model.ParkingLocations;
import com.parkmycar.model.UserFeedback;

@WebServlet("/UserFeedback")
public class UserFeedbackServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private DBOperations dBOperations;

	private final Gson gson = new Gson();

	public UserFeedbackServlet() {
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
			String inputJson = IOUtils.toString(request.getInputStream());
			UserFeedbackJSONObj jsonObj = null;
			// check if have valid JSON
			if (inputJson == null || inputJson.length() == 0) {
				response.setStatus(HttpStatus.SC_BAD_REQUEST);
				return;
			} else {
				jsonObj = gson.fromJson(inputJson, UserFeedbackJSONObj.class);
			}
			// check if the received JSON has the minimum required attributes
			// for the feedback
			// to be accepted
			if (jsonObj == null || jsonObj.getMcdid() == null
					|| jsonObj.getType() == null) {
				response.setStatus(HttpStatus.SC_BAD_REQUEST);
				return;
			}
			Integer pId = jsonObj.getpId();
			ParkingLocations pl = null;
			if (pId != null) {
				List<ParkingLocations> plist = dBOperations
						.getParkingLocationById(pId);
				if (plist == null || plist.isEmpty()) {
					response.setStatus(HttpStatus.SC_BAD_REQUEST);
					return;
				} else {
					pl = plist.get(0);
				}
			} else {
				response.setStatus(HttpStatus.SC_BAD_REQUEST);
				return;
			}
			
			//now look for duplicate feedback, user cannot provide the same type of feedback
			//multiple times in last 10 min for a given parking location
			
			Date tenMinsBackInTime = new Date(new Date().getTime() 
					- (10 * 60 * 1000));
			List<UserFeedback> ufList = null;
			ufList = dBOperations.getUserFeedbackByTypeAndMcdId(pId, jsonObj.getMcdid(), 
					jsonObj.getType(), tenMinsBackInTime, 1);
			if (ufList != null
					&& !ufList.isEmpty()) {
				response.setStatus(HttpStatus.SC_OK);
				return;
			}
			
			UserFeedback uf = new UserFeedback();
			uf.setParkingLocation(pl);
			uf.setUserFeedbackType(jsonObj.getType());
			if (jsonObj.getTimetamp() != null) {
				uf.setTimeStamp(new Date(jsonObj.getTimetamp()));
			}
			uf.setMcdid(jsonObj.getMcdid());
			uf = dBOperations.addUserFeedback(uf);
			response.setStatus(HttpStatus.SC_OK);

		} catch (Exception ex) {
			response.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
			ex.printStackTrace();
		}
	}
}
