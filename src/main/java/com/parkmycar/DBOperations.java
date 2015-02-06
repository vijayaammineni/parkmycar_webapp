package com.parkmycar;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.parkmycar.model.ParkingLocations;
import com.parkmycar.model.Pricing;
import com.parkmycar.model.UserFeedback;
import com.parkmycar.service.ParkMyCarService;

@Component
public class DBOperations {

	private ParkMyCarService parkMyCarService;

	public List<ParkingLocations> getAllParkingLocations() {
		return parkMyCarService.getAllParkingLocations();
	}

	public List<ParkingLocations> getNearestParkingLocations(double latitude,
			double longitude, double radius) {
		return parkMyCarService.getNearestParkingLocations(latitude, longitude,
				radius);
	}

	public List<ParkingLocations> getParkingLocationById(int id) {
		return parkMyCarService.getParkingLocationById(id);
	}

	public ParkingLocations addParkingLocation(ParkingLocations parkingLocation) {
		return parkMyCarService.addParkingLocation(parkingLocation);
	}

	public ParkMyCarService getParkMyCarService() {
		return parkMyCarService;
	}

	public void setParkMyCarService(ParkMyCarService parkMyCarService) {
		this.parkMyCarService = parkMyCarService;
	}

	public List<Pricing> getPricingForParkingLocation(int parkingLocationId) {
		return parkMyCarService.getPricingForParkingLocation(parkingLocationId);
	}

	public Pricing getPricingForWeekOfDayForParkingLocation(
			int parkingLocationId, int weekOfDay) {
		return parkMyCarService.getPricingForWeekOfDayForParkingLocation(
				parkingLocationId, weekOfDay);
	}

	public List<UserFeedback> getUserFeedbackForParkingLocation(
			int parkingLocationId, Date newerThan, int maxResults) {
		return parkMyCarService.getUserFeedbackForParkingLocation(
				parkingLocationId, newerThan, maxResults);
	}
}
