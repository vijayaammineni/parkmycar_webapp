package com.parkmycar.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parkmycar.dao.ParkingLocationsDao;
import com.parkmycar.dao.PricingDao;
import com.parkmycar.dao.UserFeedbackDao;
import com.parkmycar.model.ParkingLocations;
import com.parkmycar.model.Pricing;
import com.parkmycar.model.UserFeedback;
import com.parkmycar.model.enumeration.UserFeedbackType;
import com.parkmycar.service.ParkMyCarService;

@Service
public class ParkMyCarServiceImpl implements ParkMyCarService {

	@Autowired
    @Qualifier("parkingLocationsDao")
    private ParkingLocationsDao parkingLocationsDao;
	
	@Autowired
    @Qualifier("pricingDao")
    private PricingDao pricingDao;
	
	@Autowired
    @Qualifier("userFeedbackDao")
    private UserFeedbackDao userFeedbackDao;
	
	public ParkingLocations getParkingLocationById(Long id) {
		return parkingLocationsDao.get(id);
	}
	
	@Transactional
	public ParkingLocations addParkingLocation(ParkingLocations parkingLocations) {
		return parkingLocationsDao.save(parkingLocations);
	}
	
	@Transactional
	public ParkingLocations updateParkingLocation(ParkingLocations parkingLocations) {
		return parkingLocationsDao.update(parkingLocations);
	}

	@Transactional
	public void deleteParkingLocation(Long parkingLocationsId) {
		parkingLocationsDao.remove(parkingLocationsId);
	}
	
	public Pricing getPricingById(Long id) {
		return pricingDao.get(id);
	}
	
	@Transactional
	public Pricing addPricing(Pricing pricing) {
		return pricingDao.save(pricing);
	}
	
	@Transactional
	public Pricing updatePricing(Pricing pricing) {
		return pricingDao.update(pricing);
	}

	@Transactional
	public void deletePricing(Long pricingId) {
		pricingDao.remove(pricingId);
	}
	
	public UserFeedback getUserFeedbackById(Long id) {
		return userFeedbackDao.get(id);
	}
	
	@Transactional
	public UserFeedback addUserFeedback(UserFeedback userFeedback) {
		return userFeedbackDao.save(userFeedback);
	}
	
	@Transactional
	public UserFeedback updateUserFeedback(UserFeedback userFeedback) {
		return userFeedbackDao.update(userFeedback);
	}
	
	@Transactional
	public void deleteUserFeedback(Long userFeedbackId) {
		userFeedbackDao.remove(userFeedbackId);
	}

	public List<ParkingLocations> getNearestParkingLocations(double latitude,
			double longitude, double radius) {
		return parkingLocationsDao.getNearestParkingLocations(latitude, longitude, radius);
	}
	
	public List<ParkingLocations> getParkingLocationById(int id)
	{
	return parkingLocationsDao.getParkingLocationById(id);
	}

	public Pricing getPricingForWeekOfDayForParkingLocation(
			int parkingLocationId, int weekOfDay) {
		return pricingDao.getPricingForWeekOfDayForParkingLocation(parkingLocationId, weekOfDay);
	}
	
	public List<Pricing> getPricingForParkingLocation(
			int parkingLocationId) {
		return pricingDao.getPricingForParkingLocation(parkingLocationId);
	}

	public List<UserFeedback> getUserFeedbackForParkingLocation(
			int parkingLocationId, Date newerThan, int maxResults) {
		return userFeedbackDao.getUserFeedbackForParkingLocation(parkingLocationId, newerThan,maxResults);
	}

	public List<ParkingLocations> getAllParkingLocations() {
		return parkingLocationsDao.getAll();
	}

	public List<UserFeedback> getUserFeedbackByTypeAndMcdId(
			int parkingLocationId, String mcdid, UserFeedbackType ufType,
			Date newerThan, int maxResults) {
		return userFeedbackDao.getUserFeedbackByTypeAndMcdId(parkingLocationId, mcdid, 
				ufType, newerThan, maxResults);
	}
}
