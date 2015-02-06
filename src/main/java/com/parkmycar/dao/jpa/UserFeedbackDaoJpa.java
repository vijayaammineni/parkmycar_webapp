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
			int parkingLocationId, Date newerThan, int maxResults) {
		
		Query nquery = this.entityManager.createNativeQuery("Select * FROM "
				+ "UserFeedback WHERE PARKINGLOCATIONS_ID = ?1 AND TIMESTAMP > ?2", UserFeedback.class);
		nquery.setParameter(1, parkingLocationId);
		nquery.setParameter(2, newerThan);
		nquery.setMaxResults(maxResults);
		return nquery.getResultList();
	}

}
