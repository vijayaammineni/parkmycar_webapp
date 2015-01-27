package com.parkmycar;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.parkmycar.model.ParkingLocations;
import com.parkmycar.service.ParkMyCarService;

@Component
public class DBOperations {
	
	private ParkMyCarService parkMyCarService;
	
	public List<ParkingLocations> getAllParkingLocations() {
		return parkMyCarService.getAllParkingLocations();
	}
	
	public List<ParkingLocations> getNearestParkingLocations(double latitude, double longitude,
			double radius) {
		return parkMyCarService.getNearestParkingLocations(latitude, longitude, radius);
	}
	
	public ParkingLocations addParkingLocation (ParkingLocations parkingLocation) {
		return parkMyCarService.addParkingLocation(parkingLocation);
	}

	public ParkMyCarService getParkMyCarService() {
		return parkMyCarService;
	}

	public void setParkMyCarService(ParkMyCarService parkMyCarService) {
		this.parkMyCarService = parkMyCarService;
	}
	
}
