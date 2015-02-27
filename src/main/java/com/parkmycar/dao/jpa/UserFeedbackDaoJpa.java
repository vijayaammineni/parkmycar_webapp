package com.parkmycar.dao.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.parkmycar.dao.UserFeedbackDao;
import com.parkmycar.model.UserFeedback;
import com.parkmycar.model.enumeration.UserFeedbackType;

public class UserFeedbackDaoJpa extends GenericDaoJpa<UserFeedback, Long> implements UserFeedbackDao {

	public List<UserFeedback> getAllOrderByDateModified() {
		Query query = this.entityManager.createQuery("SELECT pl FROM  ParkingLocations pl ORDER BY pl.dateModified DESC");
		return query.getResultList();
	}

	public List<UserFeedback> getUserFeedbackForParkingLocation(
			int parkingLocationId, Date newerThan, int maxResults) {
		
		CriteriaBuilder qb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<UserFeedback> qdef = qb.createQuery(UserFeedback.class);
        Root<UserFeedback> uf = qdef.from(UserFeedback.class);
        List<Predicate> predicates = new ArrayList<Predicate>();
        Path<Date> feedbackTimeStamp = uf.get("timeStamp");
        predicates.add(qb.greaterThan(feedbackTimeStamp, newerThan));
        qdef.where(
                        qb.equal(uf.get("parkingLocation").get("id"), parkingLocationId),        
                        predicates.get(0)                       
        ).orderBy(qb.desc(uf.get("id")));
        
        return this.entityManager.createQuery(qdef).setMaxResults(maxResults).getResultList(); 
	}

	public List<UserFeedback> getUserFeedbackByTypeAndMcdId(
			int parkingLocationId, String mcdid, UserFeedbackType ufType,
			Date newerThan, int maxResults) {
		CriteriaBuilder qb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<UserFeedback> qdef = qb.createQuery(UserFeedback.class);
        Root<UserFeedback> uf = qdef.from(UserFeedback.class);
        List<Predicate> predicates = new ArrayList<Predicate>();
        Path<Date> feedbackTimeStamp = uf.get("timeStamp");
        predicates.add(qb.greaterThan(feedbackTimeStamp, newerThan));
        qdef.where(
                        qb.equal(uf.get("parkingLocation").get("id"), parkingLocationId),  
                        qb.equal(uf.get("mcdid"), mcdid),
                        qb.equal(uf.get("userFeedbackType"), ufType),
                        predicates.get(0)                       
        ).orderBy(qb.desc(uf.get("id")));
        
        return this.entityManager.createQuery(qdef).setMaxResults(maxResults).getResultList();
	}

}
