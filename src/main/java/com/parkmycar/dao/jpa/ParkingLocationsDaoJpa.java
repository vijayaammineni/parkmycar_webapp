package com.parkmycar.dao.jpa;

import java.util.ArrayList;
import java.util.Iterator;
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
			double longitude, double radius) 
			{
				
				/*SELECT id, ( 3959 * acos( cos( radians(37) ) * cos( radians( 32.800000 ) ) * 
		cos( radians( -117.144000 ) - radians(-122) ) + sin( radians(37) ) * 
		sin( radians( 32.800000 ) ) ) ) AS distance FROM ParkingLocations HAVING
		distance < 12000 ORDER BY distance LIMIT 0 , 20;*/
		
		Query nQuery = this.entityManager.createNativeQuery("SELECT *, "
				+ " (3959 * acos( cos( radians(?1) ) * cos( radians( latitude) ) * "
				+ "cos( radians(longitude) - radians(?2) ) + sin( radians(?3) ) * "
				+ "sin( radians(latitude) ) ) ) AS distance FROM  ParkingLocations HAVING "
				+ "( distance < ?4) ORDER BY distance",
				ParkingLocations.class);
		
		nQuery.setParameter(1, latitude);
		nQuery.setParameter(2, longitude);
		nQuery.setParameter(3, latitude);
		nQuery.setParameter(4, radius);
		
		return nQuery.getResultList();
	}
	
	public List<ParkingLocations> getParkingLocationById(int id)
	{
		Query nQuery = this.entityManager.createNativeQuery("SELECT * FROM ParkingLocations WHERE ID = ?1",ParkingLocations.class);
		nQuery.setParameter(1, id);
		return nQuery.getResultList();
	}
}
