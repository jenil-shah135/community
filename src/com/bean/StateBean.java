package com.bean;

public class StateBean {
	private Integer stateID;
	private String stateName;
	private CountryBean country;
	public Integer getStateID() {
		return stateID;
	}
	public void setStateID(Integer stateID) {
		this.stateID = stateID;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public CountryBean getCountry() {
		return country;
	}
	public void setCountry(CountryBean country) {
		this.country = country;
	}
}
