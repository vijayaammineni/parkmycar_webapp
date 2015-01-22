package com.parkmycar.dao.jpa;

import java.util.List;

import javax.persistence.Query;

import com.parkmycar.dao.ParkingLocationsDao;
import com.parkmycar.model.ParkingLocations;

public class ParkingLocationsDaoJpa extends GenericDaoJpa<ParkingLocations, Long> implements ParkingLocationsDao {

	public List<ParkingLocations> getAllOrderByDateModified() {
		Query query = this.entityManager.createQuery("SELECT pl FROM  ParkingLocations pl ORDER BY pl.dateModified DESC");
		return query.getResultList();
	}

	public List<ParkingLocations> getNearestParkingLocations(double latitude,
			double longitude, double radius) {
		// TODO Auto-generated method stub
		return null;
	}

}
