package com.parkmycar.dao.jpa;

import java.util.List;

import javax.persistence.Query;

import com.parkmycar.dao.PricingDao;
import com.parkmycar.model.Pricing;

public class PricingDaoJpa extends GenericDaoJpa<Pricing, Long> implements PricingDao {

	public List<Pricing> getAllOrderByDateModified() {
		Query query = this.entityManager.createQuery("SELECT pl FROM  ParkingLocations pl ORDER BY pl.dateModified DESC");
		return query.getResultList();
	}

	public Pricing getPricingForWeekOfDayForParkingLocation(
			int parkingLocationId, int weekOfDay) {
		Query nQuery = this.entityManager.createNativeQuery("SELECT * from Pricing WHERE PARKINGLOCATIONS_ID"
				+ "= ?1 AND DAYOFWEEK LIKE ?2",Pricing.class);
		nQuery.setParameter(1,parkingLocationId);
		nQuery.setParameter(2,weekOfDay);
		return (Pricing)nQuery.getResultList().get(0);
		
	}

	public List<Pricing> getPricingForParkingLocation(int parkingLocationId) 
	{
		Query nQuery = this.entityManager.createNativeQuery("SELECT * from Pricing WHERE PARKINGLOCATIONS_ID = ?1",Pricing.class);
		nQuery.setParameter(1,parkingLocationId);
		return nQuery.getResultList();
		
	}
}
