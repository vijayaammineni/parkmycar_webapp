package com.parkmycar.dao;

import java.util.Date;
import java.util.List;

import com.parkmycar.model.UserFeedback;
import com.parkmycar.model.enumeration.UserFeedbackType;

public interface UserFeedbackDao extends GenericDao<UserFeedback, Long> {
	public List<UserFeedback> getUserFeedbackForParkingLocation(int parkingLocationId, Date newerThan, int maxResults);
	public List<UserFeedback> getUserFeedbackByTypeAndMcdId(int parkingLocationId, String mcdid, UserFeedbackType ufType, Date newerThan, int maxResults);
}
