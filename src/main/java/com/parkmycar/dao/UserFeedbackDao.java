package com.parkmycar.dao;

import java.util.Date;
import java.util.List;

import com.parkmycar.model.UserFeedback;

public interface UserFeedbackDao extends GenericDao<UserFeedback, Long> {
	public List<UserFeedback> getUserFeedbackForParkingLocation(int parkingLocationId, Date newerThan, int maxResults);
}
