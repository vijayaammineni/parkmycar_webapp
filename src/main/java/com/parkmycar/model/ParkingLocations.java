package com.parkmycar.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.parkmycar.model.enumeration.ParkingCategory;

@Entity
@Table (name="ParkingLocations")
public class ParkingLocations 
	implements Serializable 
{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
    
	@Column(name="NAME", nullable = false)
    private String name;
	
	@Column(name="ADDRESS", nullable = false)
    private String address;
	
	@Column(name="CITY", nullable = false)
    private String city;
	
	@Column(name="STATE", nullable = false)
    private String state;
    
	@Column(name="ZIPCODE", nullable = false)
    private Integer zipCode;
	
	@Column(name="CATEGORY", nullable = false)
	@Enumerated(value = EnumType.ORDINAL)
    private ParkingCategory category = ParkingCategory.PUBLIC;
                    
    @Temporal(TemporalType.TIMESTAMP)
	@Column (name="DATEADDED", nullable = false)
    private Date dateAdded = new Date();
    
    @Temporal(TemporalType.TIMESTAMP)
	@Column (name="DATEADDED", nullable = false)
    private Date dateModified = new Date();
    
    @Column(name="UPVOTES", nullable = false)
    private Integer upVotes;
    
    @Column(name="DOWNVOTES", nullable = false)
    private Integer downVotes;
    
    @Column(name="LATITUDE", nullable = false)
    private Double latitude;
    
    @Column(name="LONGITUDE", nullable = false)
    private Double longitude;
    
    @Column(name="ACTIVE", nullable = false)
    private boolean active = true;
    
        
	public Long getId()
    {
		return id;
	}

	public void setId(Long id)
    {
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

	public Integer getZipCode() {
		return zipCode;
	}

	public void setZipCode(Integer zipCode) {
		this.zipCode = zipCode;
	}

	public ParkingCategory getCategory() {
		return category;
	}

	public void setCategory(ParkingCategory category) {
		this.category = category;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public Integer getUpVotes() {
		return upVotes;
	}

	public void setUpVotes(Integer upVotes) {
		this.upVotes = upVotes;
	}

	public Integer getDownVotes() {
		return downVotes;
	}

	public void setDownVotes(Integer downVotes) {
		this.downVotes = downVotes;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	
}


