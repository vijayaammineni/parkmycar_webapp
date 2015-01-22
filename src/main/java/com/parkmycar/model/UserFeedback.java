package com.parkmycar.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table (name="UserFeedback")
public class UserFeedback 
	implements Serializable 
{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARKINGLOCATIONS_ID", nullable = false, updatable = false)
    private ParkingLocations parkingLocation;	
    
	@Column(name="AVAILABILITY", nullable = false)
    private boolean available;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column (name="TIMESTAMP", nullable = false)
	private Date timeStamp = new Date();

	public ParkingLocations getParkingLocation() {
		return parkingLocation;
	}

	public void setParkingLocation(ParkingLocations parkingLocation) {
		this.parkingLocation = parkingLocation;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
		
}





