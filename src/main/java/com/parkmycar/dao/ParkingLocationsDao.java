package com.parkmycar.dao;

import java.util.List;

import com.parkmycar.model.ParkingLocations;

public interface ParkingLocationsDao extends GenericDao<ParkingLocations, Long> {
	
	public List<ParkingLocations> getNearestParkingLocations(double latitude,
			double longitude, double radius);
			
}
