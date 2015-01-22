package com.parkmycar.dao;

import com.parkmycar.model.Pricing;

public interface PricingDao extends GenericDao<Pricing, Long> {
	public Pricing getPricingForWeekOfDayForParkingLocation(int parkingLocationId, int weekOfDay);
}
