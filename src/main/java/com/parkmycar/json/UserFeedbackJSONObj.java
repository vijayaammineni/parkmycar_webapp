package com.parkmycar.json;

import com.parkmycar.model.enumeration.UserFeedbackType;

public class UserFeedbackJSONObj {
	
	private Integer pId;
	private String mcdid;
	private Long timetamp;
	private UserFeedbackType type;	
	
	public Integer getpId() {
		return pId;
	}
	public void setpId(Integer pId) {
		this.pId = pId;
	}
	public String getMcdid() {
		return mcdid;
	}
	public void setMcdid(String mcdid) {
		this.mcdid = mcdid;
	}
	public Long getTimetamp() {
		return timetamp;
	}
	public void setTimetamp(Long timetamp) {
		this.timetamp = timetamp;
	}
	public UserFeedbackType getType() {
		return type;
	}
	public void setType(UserFeedbackType type) {
		this.type = type;
	}
	
}
