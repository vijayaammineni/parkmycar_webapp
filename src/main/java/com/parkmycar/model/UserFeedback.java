package com.parkmycar.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.parkmycar.model.enumeration.UserFeedbackType;

@Entity
@Table(name = "UserFeedback")
public class UserFeedback implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARKINGLOCATIONS_ID", nullable = false, updatable = false)
	private ParkingLocations parkingLocation;

	@Column(name = "AVAILABILITY", nullable = false)
	@Enumerated(value = EnumType.ORDINAL)
	private UserFeedbackType userFeedbackType = UserFeedbackType.NOTAVAILABLE;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TIMESTAMP", nullable = false)
	private Date timeStamp = new Date();

	public ParkingLocations getParkingLocation() {
		return parkingLocation;
	}

	public void setParkingLocation(ParkingLocations parkingLocation) {
		this.parkingLocation = parkingLocation;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserFeedbackType getUserFeedbackType() {
		return userFeedbackType;
	}

	public void setUserFeedbackType(UserFeedbackType userFeedbackType) {
		this.userFeedbackType = userFeedbackType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

}
