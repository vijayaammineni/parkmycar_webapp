package com.parkmycar.service;

import java.util.Date;
import java.util.List;

import com.parkmycar.model.ParkingLocations;
import com.parkmycar.model.Pricing;
import com.parkmycar.model.UserFeedback;
import com.parkmycar.model.enumeration.UserFeedbackType;

public interface ParkMyCarService {

	public ParkingLocations getParkingLocationById(Long id);
	
	public ParkingLocations addParkingLocation(ParkingLocations parkingLocations);
	
	public ParkingLocations updateParkingLocation(ParkingLocations parkingLocations);

	public void deleteParkingLocation(Long parkingLocationsId);
	
	public List<ParkingLocations> getNearestParkingLocations (double latitude, double longitude, double radius);
	public List<ParkingLocations> getParkingLocationById(int id);
	
	public List<ParkingLocations> getAllParkingLocations ();
	
	public Pricing getPricingById(Long id);
	
	public Pricing addPricing(Pricing pricing);
	
	public Pricing updatePricing(Pricing pricing);

	public void deletePricing(Long pricingId);
	
	public Pricing getPricingForWeekOfDayForParkingLocation(int parkingLocationId, int weekOfDay);
	
	public List<Pricing> getPricingForParkingLocation(int parkingLocationId);
	
	public UserFeedback getUserFeedbackById(Long id);
	
	public UserFeedback addUserFeedback(UserFeedback userFeedback);
	
	public UserFeedback updateUserFeedback(UserFeedback userFeedback);
	
	public void deleteUserFeedback(Long userFeedbackId);
	
	public List<UserFeedback> getUserFeedbackForParkingLocation(int parkingLocationId, Date newerThan, int maxResults);
	
	public List<UserFeedback> getUserFeedbackByTypeAndMcdId(int parkingLocationId, String mcdid, UserFeedbackType ufType, Date newerThan, int maxResults);
	
}
