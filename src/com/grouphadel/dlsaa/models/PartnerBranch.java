package com.grouphadel.dlsaa.models;

public class PartnerBranch extends DatabaseObject {
	private PartnerBusiness business;
	private String address;
	private double latitude;
	private double longitude;

	public PartnerBusiness getBusiness() {
		return business;
	}

	public void setBusiness(PartnerBusiness business) {
		this.business = business;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}
