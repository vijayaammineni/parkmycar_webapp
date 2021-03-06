package com.parkmycar.json;

import java.util.List;

import com.parkmycar.model.enumeration.ParkingCategory;

public class ParkingLocationsJSONObj {
	private long id;
	private String address;
	private Double latitude;
	private Double longitude;
	private String name;
	private String city;
	private String state;
	private int zipcode;
	private ParkingCategory category;
	private int upVotes;
	private int downVotes;
	private Double distance;
	private List<PricingJSONObj> pricingDetailsList;
	private AvailabilityJSONObj availabilityObj;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getZipcode() {
		return zipcode;
	}

	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}

	public ParkingCategory getCategory() {
		return category;
	}

	public void setCategory(ParkingCategory category) {
		this.category = category;
	}

	public int getUpVotes() {
		return upVotes;
	}

	public void setUpVotes(int upVotes) {
		this.upVotes = upVotes;
	}

	public int getDownVotes() {
		return downVotes;
	}

	public void setDownVotes(int downVotes) {
		this.downVotes = downVotes;
	}
	
	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public List<PricingJSONObj> getPricingDetailsList() {
		return pricingDetailsList;
	}

	public void setPricingDetailsList(List<PricingJSONObj> pricingDetailsList) {
		this.pricingDetailsList = pricingDetailsList;
	}

	public AvailabilityJSONObj getAvailabilityObj() {
		return availabilityObj;
	}

	public void setAvailabilityObj(AvailabilityJSONObj availabilityObj) {
		this.availabilityObj = availabilityObj;
	}

}
