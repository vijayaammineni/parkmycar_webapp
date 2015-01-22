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
		// TODO Auto-generated method stub
		return null;
	}

}
