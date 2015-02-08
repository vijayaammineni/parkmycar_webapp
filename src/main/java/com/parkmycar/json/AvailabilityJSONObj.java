package com.parkmycar.json;

import java.util.Date;

public class AvailabilityJSONObj 
{
	int availableVotes;
	int unAvailableVotes;
	int parkedNum;
	int checkedOutNum;
	public int getAvailableVotes() {
		return availableVotes;
	}
	public void setAvailableVotes(int availableVotes) {
		this.availableVotes = availableVotes;
	}
	public int getUnAvailableVotes() {
		return unAvailableVotes;
	}
	public void setUnAvailableVotes(int unAvailableVotes) {
		this.unAvailableVotes = unAvailableVotes;
	}
	public int getParkedNum() {
		return parkedNum;
	}
	public void setParkedNum(int parkedNum) {
		this.parkedNum = parkedNum;
	}
	public int getCheckedOutNum() {
		return checkedOutNum;
	}
	public void setCheckedOutNum(int checkedOutNum) {
		this.checkedOutNum = checkedOutNum;
	}
	
}
