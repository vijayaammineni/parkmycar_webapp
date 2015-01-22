package com.parkmycar.dao.jpa;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import com.parkmycar.dao.UserFeedbackDao;
import com.parkmycar.model.UserFeedback;

public class UserFeedbackDaoJpa extends GenericDaoJpa<UserFeedback, Long> implements UserFeedbackDao {

	public List<UserFeedback> getAllOrderByDateModified() {
		Query query = this.entityManager.createQuery("SELECT pl FROM  ParkingLocations pl ORDER BY pl.dateModified DESC");
		return query.getResultList();
	}

	public List<UserFeedback> getUserFeedbackForParkingLocation(
			int parkingLocationId, Date newerThan) {
		// TODO Auto-generated method stub
		return null;
	}

}
