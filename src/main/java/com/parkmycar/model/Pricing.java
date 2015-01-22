package com.parkmycar.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table (name="Pricing")
public class Pricing 
	implements Serializable 
{
	
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARKINGLOCATIONS_ID", nullable = false, updatable = false)
    private ParkingLocations parkingLocation;	
    
	@Column(name="DAYOFWEEK", nullable = false)
    private Integer dayOfWeek;
	
	@Column(name="HOURLYPRICE", nullable = false)
    private Double hourlyPrice;
	
	@Column(name="DAYPRICE", nullable = false)
    private Double dayPrice;

	public ParkingLocations getParkingLocation() {
		return parkingLocation;
	}

	public void setParkingLocation(ParkingLocations parkingLocation) {
		this.parkingLocation = parkingLocation;
	}

	public Integer getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(Integer dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public Double getHourlyPrice() {
		return hourlyPrice;
	}

	public void setHourlyPrice(Double hourlyPrice) {
		this.hourlyPrice = hourlyPrice;
	}

	public Double getDayPrice() {
		return dayPrice;
	}

	public void setDayPrice(Double dayPrice) {
		this.dayPrice = dayPrice;
	}
	
}


