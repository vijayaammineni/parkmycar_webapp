package com.parkmycar.dao;

import java.util.List;

import com.parkmycar.model.Pricing;

public interface PricingDao extends GenericDao<Pricing, Long> {
	public Pricing getPricingForWeekOfDayForParkingLocation(int parkingLocationId, int weekOfDay);
	public List<Pricing> getPricingForParkingLocation(int parkingLocationId);
}
