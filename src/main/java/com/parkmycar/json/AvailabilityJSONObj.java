package com.parkmycar.json;

import java.util.Date;

public class AvailabilityJSONObj 
{

	private boolean availabity;
	private Date timeStamp;
	
	public boolean isAvailabity() {
		return availabity;
	}
	public void setAvailabity(boolean availabity) {
		this.availabity = availabity;
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	
}
