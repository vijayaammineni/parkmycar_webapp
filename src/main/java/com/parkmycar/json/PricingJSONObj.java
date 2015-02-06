package com.parkmycar.json;


public class PricingJSONObj 
{

	private Double hourlyPrice;
	private Double dayPrice;
	private int DayOfWeek;
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
	public int getDayOfWeek() {
		return DayOfWeek;
	}
	public void setDayOfWeek(int dayOfWeek) {
		DayOfWeek = dayOfWeek;
	}
	
	
	
	
}
